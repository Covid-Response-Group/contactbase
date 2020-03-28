ContactBase
===========

### Open API for Device Based Contact Tracing


- Device Registration

```
curl localhost:3000/api/v1/devices -XPOST -d '{"deviceId": "aa:bb:cc:dd", "user": {"mobile": "9999999999"}}' -H "Content-Type: application/json"
{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"}
```


- Push Device Contacts

```
curl localhost:3000/api/v1/contacts -XPOST -d '[{"deviceId": "cc:dd:ee:ff", "spatialTemporalStamps": [{"geohash": "gbsuv7z", "dateStamp": "20200327"}, {"geohash": "gbsuv7z", "dateStamp": "20200328"}]}]' -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"
```

- List All Device Contacts

```
curl "localhost:3000/api/v1/contacts?dateStamp=20200327&geohash=gbsuv7z" -XGET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"
```

- Mark device of the infected owner

```
curl localhost:3000/api/v1/device/infected -XPOST -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"
```

- Unmark device as infected

```
curl localhost:3000/api/v1/device/infected -XDELETE -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"
```

- Get if device owner is infected or not

```
curl localhost:3000/api/v1/device/infected -XGET -H "Content-Type: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJkZXZpY2VJZFwiOlwiYWE6YmI6Y2M6ZGRcIixcInVzZXJcIjp7XCJtb2JpbGVcIjpcIjk5OTk5OTk5OTlcIn0sXCJyZWdpc3RyYXRpb25UaW1lU3RhbXBcIjpudWxsfSJ9.h1kz-TnAQU6LbuXr5V72Fp_CwMMPu6eIn9jeDBCyDaA"
true
```