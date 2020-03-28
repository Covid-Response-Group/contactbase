package org.covid19.contactbase.service;

import org.covid19.contactbase.model.Contact;
import org.covid19.contactbase.model.SpatialTemporalStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public ContactService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void store(String deviceId, List<Contact> contacts) {
        Map<String, Set<ZSetOperations.TypedTuple<String>>> contactsToFlush = new HashMap<>();
        Map<String, Set<String>> spatialTemporalStampsToFlush = new HashMap<>();

        for (Contact contact : contacts) {
            String contactDeviceId = contact.getDeviceId();

            for (SpatialTemporalStamp spatialTemporalStamp : contact.getSpatialTemporalStamps()) {
                // Contacts Storage

                String key = getContactsKey(deviceId, spatialTemporalStamp);

                if (!contactsToFlush.containsKey(key)) {
                    contactsToFlush.put(key, new HashSet<>());
                }

                contactsToFlush.get(key).add(new DefaultTypedTuple<>(contactDeviceId, 1.0));

                key = getContactsKey(contactDeviceId, spatialTemporalStamp);

                if (!contactsToFlush.containsKey(key)) {
                    contactsToFlush.put(key, new HashSet<>());
                }

                contactsToFlush.get(key).add(new DefaultTypedTuple<>(deviceId, 1.0));

                // Spatial Temporal Stamps Storage

                key = getSpatialTemporalStampsKey(deviceId);

                if (!spatialTemporalStampsToFlush.containsKey(key)) {
                    spatialTemporalStampsToFlush.put(key, new HashSet<>());
                }

                spatialTemporalStampsToFlush.get(key).add(spatialTemporalStamp.toString());

                key = getSpatialTemporalStampsKey(contactDeviceId);

                if (!spatialTemporalStampsToFlush.containsKey(key)) {
                    spatialTemporalStampsToFlush.put(key, new HashSet<>());
                }

                spatialTemporalStampsToFlush.get(key).add(spatialTemporalStamp.toString());
            }
        }

        for (Map.Entry<String, Set<ZSetOperations.TypedTuple<String>>> entry : contactsToFlush.entrySet()) {
            stringRedisTemplate.opsForZSet().add(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Set<String>> entry : spatialTemporalStampsToFlush.entrySet()) {
            String[] values = new String[entry.getValue().size()];
            entry.getValue().toArray(values);
            stringRedisTemplate.opsForSet().add(entry.getKey(), values);
        }
    }

    public List<Contact> list(String deviceId, String geohash, String dateStamp) {
        SpatialTemporalStamp spatialTemporalStamp = new SpatialTemporalStamp(geohash, dateStamp);

        String key = getContactsKey(deviceId, spatialTemporalStamp);
        Set<String> contactDeviceIds = stringRedisTemplate.opsForZSet().range(key, 0, -1);

        return contactDeviceIds
                .stream()
                .map(contactDeviceId -> new Contact(contactDeviceId, Collections.singletonList(spatialTemporalStamp)))
                .collect(Collectors.toList());
    }

    private String getContactsKey(String deviceId, SpatialTemporalStamp spatialTemporalStamp) {
        return "CONTACTS/" + deviceId + "/" + spatialTemporalStamp.getGeohash() + "/" + spatialTemporalStamp.getDateStamp();
    }

    private String getSpatialTemporalStampsKey(String deviceId) {
        return "SPACETIME/" + deviceId;
    }
}
