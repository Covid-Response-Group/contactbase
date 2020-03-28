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

- List All Device Contacts

```
curl "localhost:3000/api/v1/contacts?page=0&size=100" -XGET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"
```

- Mark device of the infected owner

```
curl localhost:3000/api/v1/device/infected -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"
```

- Unmark device as infected

```
curl localhost:3000/api/v1/device/infected -XDELETE -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"
```

- Get if device owner is infected or not

```
curl localhost:3000/api/v1/device/infected -XGET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.5gMsujUERueh7r-BCaEUV2zYlGglimGkpZc0ap9v4DM"
true
```