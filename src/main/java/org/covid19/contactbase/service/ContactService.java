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
        Map<String, Set<ZSetOperations.TypedTuple<String>>> spatialTemporalStampsToFlush = new HashMap<>();

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

                spatialTemporalStampsToFlush.get(key)
                        .add(new DefaultTypedTuple<>(spatialTemporalStamp.toString(), 1.0));

                key = getSpatialTemporalStampsKey(contactDeviceId);

                if (!spatialTemporalStampsToFlush.containsKey(key)) {
                    spatialTemporalStampsToFlush.put(key, new HashSet<>());
                }

                spatialTemporalStampsToFlush.get(key)
                        .add(new DefaultTypedTuple<>(spatialTemporalStamp.toString(), 1.0));
            }
        }

        for (Map.Entry<String, Set<ZSetOperations.TypedTuple<String>>> entry : contactsToFlush.entrySet()) {
            stringRedisTemplate.opsForZSet().add(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Set<ZSetOperations.TypedTuple<String>>> entry : spatialTemporalStampsToFlush.entrySet()) {
            stringRedisTemplate.opsForZSet().add(entry.getKey(), entry.getValue());
        }
    }

    public List<Contact> query(String deviceId, List<String> geoHashes, String fromDateStamp, String toDateStamp) {
        Set<SpatialTemporalStamp> filteredSpatialTemporalStamps = filterQuerySpatialTemporalStamps(deviceId,
                geoHashes, fromDateStamp, toDateStamp);

        List<Contact> allContacts = new ArrayList<>();

        for (SpatialTemporalStamp spatialTemporalStamp : filteredSpatialTemporalStamps) {
            List<Contact> contacts = query(deviceId, spatialTemporalStamp.getGeohash(), spatialTemporalStamp.getDateStamp());
            allContacts.addAll(contacts);
        }

        Map<String, List<SpatialTemporalStamp>> deviceSpatialTemporalMap = new HashMap<>();

        for (Contact contact : allContacts) {
            if (deviceSpatialTemporalMap.containsKey(contact.getDeviceId())) {
                deviceSpatialTemporalMap.get(contact.getDeviceId()).addAll(contact.getSpatialTemporalStamps());
            } else {
                deviceSpatialTemporalMap.put(contact.getDeviceId(),
                        new ArrayList<>(contact.getSpatialTemporalStamps()));
            }
        }

        return deviceSpatialTemporalMap
                .entrySet()
                .stream()
                .map(entry -> new Contact(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<Contact> query(String deviceId, String geohash, String dateStamp) {
        SpatialTemporalStamp spatialTemporalStamp = new SpatialTemporalStamp(geohash, dateStamp);

        String key = getContactsKey(deviceId, spatialTemporalStamp);
        Set<String> contactDeviceIds = stringRedisTemplate.opsForZSet().range(key, 0, -1);

        return contactDeviceIds
                .stream()
                .map(contactDeviceId -> new Contact(contactDeviceId, Collections.singletonList(spatialTemporalStamp)))
                .collect(Collectors.toList());
    }

    private Set<SpatialTemporalStamp> filterQuerySpatialTemporalStamps(String deviceId, List<String> geoHashes, String fromDateStamp, String toDateStamp) {
        String spatialTemporalStampKey = getSpatialTemporalStampsKey(deviceId);

        Map<String, Boolean> geoHashMap = new HashMap<>();

        for (String geohash : geoHashes) {
            geoHashMap.put(geohash, true);
        }

        int currentStart = 0;
        int pageSize = 50;

        Set<SpatialTemporalStamp> filteredSpatialTemporalStamps = new HashSet<>();

        while (true) {
            Set<String> spatialTemporalStamps = stringRedisTemplate
                    .opsForZSet()
                    .reverseRange(spatialTemporalStampKey, currentStart, currentStart + pageSize);

            boolean dateRangeSatisfied = false;

            for (String spatialTemporalStampString : spatialTemporalStamps) {
                SpatialTemporalStamp spatialTemporalStamp = SpatialTemporalStamp
                        .fromString(spatialTemporalStampString);

                if (spatialTemporalStamp == null) {
                    continue;
                }

                String dateStamp = spatialTemporalStamp.getDateStamp();
                String geohash = spatialTemporalStamp.getGeohash();

                Integer dateStampInt = Integer.parseInt(dateStamp);
                Integer fromDateStampInt = Integer.parseInt(fromDateStamp);

                if (dateStampInt >= fromDateStampInt
                        && dateStampInt <= Integer.parseInt(toDateStamp)
                        && geoHashMap.containsKey(geohash)) {
                    filteredSpatialTemporalStamps.add(spatialTemporalStamp);
                }

                if (dateStampInt < fromDateStampInt) {
                    dateRangeSatisfied = true;
                    break;
                }
            }

            if (dateRangeSatisfied) {
                break;
            }

            if (spatialTemporalStamps.size() != pageSize) {
                break;
            }

            currentStart += pageSize;
        }

        return filteredSpatialTemporalStamps;
    }

    private String getContactsKey(String deviceId, SpatialTemporalStamp spatialTemporalStamp) {
        return "CONTACTS/" + deviceId + "/" + spatialTemporalStamp.getGeohash() + "/" + spatialTemporalStamp.getDateStamp();
    }

    private String getSpatialTemporalStampsKey(String deviceId) {
        return "SPACETIME/" + deviceId;
    }
}
