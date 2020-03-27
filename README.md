ContactBase
===========

### Open API for Device Based Contact Tracing


- Device Registration

```
curl localhost:3000/api/v1/devices -XPOST -d '{"deviceId": "aa:bb:cc:dd", "user": {"mobile": "9999999999"}}' -H "Content-Type: application/json"
{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"}
```


- Push Device Contacts

```
curl localhost:3000/api/v1/contacts -XPOST -d '[{"deviceId": "cc:dd:ee:ff", "spatialTemporalStamps": [{"geohash": "gbsuv7z", "hourStamp": "2020032712", "distance": 12}, {"geohash": "gbsuv7z", "hourStamp": "2020032713", "distance": 25}]}]' -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"
```

Each SpatialTemporalStamp has a format of L7Geohash:DistanceInMeters:HourStamp
