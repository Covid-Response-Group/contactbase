package org.covid19.contactbase.service;

import org.covid19.contactbase.model.Contact;
import org.covid19.contactbase.model.SpatialTemporalStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public ContactService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void store(String deviceId, List<Contact> contacts) {
        Set<ZSetOperations.TypedTuple<String>> contactDeviceIds = new HashSet<>();

        for (Contact contact : contacts) {
            List<SpatialTemporalStamp> spatialTemporalStamps = contact.getSpatialTemporalStamps();

            String[] spatialTemporalStampSerializedList = new String[spatialTemporalStamps.size()];

            int stampIdx = 0;

            for (SpatialTemporalStamp spatialTemporalStamp : spatialTemporalStamps) {
                spatialTemporalStampSerializedList[stampIdx] = spatialTemporalStamp.toString();
                stampIdx++;
            }

            String contactDeviceId = contact.getDeviceId();

            stringRedisTemplate.opsForZSet().add(getContactsKey(contactDeviceId), deviceId, 1.0);

            stringRedisTemplate.opsForSet().add(getContactMetaKey(deviceId, contactDeviceId),
                    spatialTemporalStampSerializedList);

            contactDeviceIds.add(new DefaultTypedTuple<>(contactDeviceId, 1.0));
        }

        stringRedisTemplate.opsForZSet().add(getContactsKey(deviceId), contactDeviceIds);

    }

    public List<Contact> list(String deviceId, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        int start = pageNumber * pageSize;
        int end = start + pageNumber;

        Set<String> contactDeviceIds = stringRedisTemplate
                .opsForZSet()
                .range(getContactsKey(deviceId), start, end);

        List<Contact> contacts = new ArrayList<>();

        for (String contactDeviceId : contactDeviceIds) {
            List<SpatialTemporalStamp> spatialTemporalStamps = stringRedisTemplate
                    .opsForSet()
                    .members(getContactMetaKey(deviceId, contactDeviceId))
                    .stream()
                    .map(SpatialTemporalStamp::fromString)
                    .collect(Collectors.toList());

            contacts.add(new Contact(contactDeviceId, spatialTemporalStamps));
        }

        return contacts;
    }

    private String getContactsKey(String deviceId) {
        return "CONTACTS/" + deviceId;
    }

    private String getContactMetaKey(String sourceDeviceId, String targetDeviceId) {
        Integer i = sourceDeviceId.compareTo(targetDeviceId);

        if (i < 0) {
            return "CONTACT/" + sourceDeviceId + "/" + targetDeviceId + "/";
        } else {
            return "CONTACT/" + targetDeviceId + "/" + sourceDeviceId + "/";
        }
    }
}
