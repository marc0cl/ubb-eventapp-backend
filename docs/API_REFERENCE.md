# API Reference

This document describes the available REST endpoints. All examples assume the server is running locally on port 8080.

## Authentication

### Register
- **POST** `/auth/register`
- **Request body**:
```json
{
  "email": "user@example.com",
  "password": "secret",
  "username": "user",
  "firstName": "John",
  "lastName": "Doe",
  "isExternal": false
}
```
- **Curl example**:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"secret","username":"user","firstName":"John","lastName":"Doe","isExternal":false}'
```
- **Response**:
```json
{
  "accessToken": "<jwt>",
  "refreshToken": "<jwt>"
}
```

### Login
- **POST** `/auth/login`
- **Request body**:
```json
{
  "email": "user@example.com",
  "password": "secret"
}
```
- **Curl example**:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"user@example.com","password":"secret"}'
```
- **Response**: same as `POST /auth/register`.

### Refresh Token
- **POST** `/auth/refresh`
- **Headers**: `Authorization: Bearer <refreshToken>`
- **Curl example**:
```bash
curl -X POST http://localhost:8080/auth/refresh \
  -H 'Authorization: Bearer <refreshToken>'
```
- **Response**: same as login.

## Users

### Update Profile
- **PUT** `/users`
- **Request body** (User):
```json
{
  "id": 1,
  "correoUbb": "user@ubb.cl",
  "nombres": "Juan",
  "apellidos": "Pérez",
  "username": "juan",
  "campus": {"id": 1}
}
```
- **Curl example**:
```bash
curl -X PUT http://localhost:8080/users \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"correoUbb":"user@ubb.cl","nombres":"Juan","apellidos":"Pérez","username":"juan","campus":{"id":1}}'
```
- **Response**: updated `User` object.

### Get User
- **GET** `/users/{id}`
- **Curl**:
```bash
curl http://localhost:8080/users/1
```
- **Response**: `User`.

### Profile Summary
- **GET** `/users/{id}/summary`
- **Curl**:
```bash
curl http://localhost:8080/users/1/summary
```
- **Response**:
```json
{
  "username": "juan",
  "friendsCount": 5,
  "trophies": [],
  "events": {
    "eventsAttended": 10,
    "eventsCreated": 3,
    "eventsToAttend": 2
  }
}
```

### Profile Events
- **GET** `/users/{id}/events`
- **Curl**:
```bash
curl http://localhost:8080/users/1/events
```
- **Response**: `ProfileEvents` object (see above).

### Calendar
- **GET** `/users/{id}/calendar`
- **Curl**:
```bash
curl http://localhost:8080/users/1/calendar
```
- **Response**:
```json
[
  {"date":"2024-01-01T10:00:00","eventIds":[1,2]}
]
```

### Events To Attend
- **GET** `/users/{id}/to-attend`
- **Curl**:
```bash
curl http://localhost:8080/users/1/to-attend
```
- **Response**:
```json
{"eventIds": [1, 3, 5]}
```

### User Recommendations
- **GET** `/users/{id}/recommendations`
- **Curl**:
```bash
curl http://localhost:8080/users/1/recommendations
```
- **Response**:
```json
[ {"id": 3, "username": "ana"} ]
```

## Events

### Create Event
- **POST** `/events`
- **Request body**:
```json
{
  "titulo": "Charla",
  "descripcion": "Descripción",
  "fechaInicio": "2024-01-01T10:00:00",
  "fechaFin": "2024-01-01T12:00:00",
  "lugar": "Auditorio",
  "aforoMax": 100
}
```
- **Curl example**:
```bash
curl -X POST http://localhost:8080/events \
  -H 'Content-Type: application/json' \
  -d '{"titulo":"Charla","descripcion":"Descripción","fechaInicio":"2024-01-01T10:00:00","fechaFin":"2024-01-01T12:00:00","lugar":"Auditorio","aforoMax":100}'
```
- **Response**: created `Event` object.

### Update Event
- **PUT** `/events`
- **Request body**: same as create plus `id`.
- **Curl**:
```bash
curl -X PUT http://localhost:8080/events \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"titulo":"Charla","descripcion":"Descripción","fechaInicio":"2024-01-01T10:00:00","fechaFin":"2024-01-01T12:00:00","lugar":"Auditorio","aforoMax":100}'
```
- **Response**: updated `Event`.

### Approve Event
- **POST** `/events/{id}/approve`
- **Curl**:
```bash
curl -X POST http://localhost:8080/events/1/approve
```
- **Response**: approved `Event`.

### Delete Event
- **DELETE** `/events/{id}`
- **Curl**:
```bash
curl -X DELETE http://localhost:8080/events/1
```
- **Response**: `200 OK` with empty body.

### Find Event By Id
- **GET** `/events/{id}`
- **Curl**:
```bash
curl http://localhost:8080/events/1
```
- **Response**: `Event`.

### Events By Creator
- **GET** `/events/creator/{userId}`

Example:
```bash
curl http://localhost:8080/events/creator/1
```
- **Response**:
```json
[
  {"id":1,"titulo":"Charla","fechaInicio":"2024-01-01T10:00:00"}
]
```

### Events By Group
- **GET** `/events/group/{groupId}`

Example:
```bash
curl http://localhost:8080/events/group/2
```
- **Response**:
```json
[
  {"id":3,"titulo":"Reunión","fechaInicio":"2024-02-10T09:00:00"}
]
```

### Upcoming Events
- **GET** `/events/upcoming?after=2025-01-01T00:00:00`

Example:
```bash
curl "http://localhost:8080/events/upcoming?after=2025-01-01T00:00:00"
```
- **Response**:
```json
[
  {"id":5,"titulo":"Feria","fechaInicio":"2025-01-15T11:00:00"}
]
```

### Public Events
- **GET** `/events/public`

Example:
```bash
curl http://localhost:8080/events/public
```
- **Response**:
```json
[
  {"id":1,"titulo":"Charla"}
]
```

### Pending Events
- **GET** `/events/pending`

Example:
```bash
curl http://localhost:8080/events/pending
```
- **Response**:
```json
[
  {"id":2,"titulo":"Pendiente"}
]
```

### Events By Ids
- **GET** `/events?ids=1,2,3`

Example:
```bash
curl "http://localhost:8080/events?ids=1,2,3"
```
- **Response**:
```json
[
  {"id":1,"titulo":"Charla"},
  {"id":2,"titulo":"Taller"},
  {"id":3,"titulo":"Reunión"}
]
```

Each of the above returns a list of `Event` objects.

## Registrations

### Register For Event
- **POST** `/registrations`
- **Request body**:
```json
{
  "id": {"eventId": 1, "userId": 2}
}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/registrations \
  -H 'Content-Type: application/json' \
  -d '{"id":{"eventId":1,"userId":2}}'
```
- **Response**: `Registration` object.

### Cancel Registration
- **POST** `/registrations/cancel`
- **Request body**:
```json
{"eventId": 1, "userId": 2}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/registrations/cancel \
  -H 'Content-Type: application/json' \
  -d '{"eventId":1,"userId":2}'
```
- **Response**: updated `Registration`.

### Get Registration
- **GET** `/registrations/{eventId}/{userId}`
Example:
```bash
curl http://localhost:8080/registrations/1/2
```
- **Response**:
```json
{
  "id": {"eventId": 1, "userId": 2},
  "estado": "INSCRITO"
}
```

## Friendships

### Request Friendship
- **POST** `/friendships`
- **Request body**:
```json
{"id": {"user1Id": 1, "user2Id": 2}}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/friendships \
  -H 'Content-Type: application/json' \
  -d '{"id":{"user1Id":1,"user2Id":2}}'
```
- **Response**: `Friendship`.

### Accept Friendship
- **POST** `/friendships/accept`
- **Request body**:
```json
{"user1Id": 1, "user2Id": 2}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/friendships/accept \
  -H 'Content-Type: application/json' \
  -d '{"user1Id":1,"user2Id":2}'
```
- **Response**: updated `Friendship`.

### Reject Friendship
- **POST** `/friendships/reject`
- **Request body**:
```json
{"user1Id": 1, "user2Id": 2}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/friendships/reject \
  -H 'Content-Type: application/json' \
  -d '{"user1Id":1,"user2Id":2}'
```
- **Response**: updated `Friendship`.

### Pending Friendships
- **GET** `/friendships/pending/{userId}`
- **Curl**:
```bash
curl http://localhost:8080/friendships/pending/1
```
- **Response**: list of `Friendship`.

### Get Friendship
- **GET** `/friendships/{user1}/{user2}`
Example:
```bash
curl http://localhost:8080/friendships/1/2
```
- **Response**:
```json
{
  "id": {"user1Id": 1, "user2Id": 2},
  "estado": "PENDIENTE"
}
```

### List Friends
- **GET** `/friendships/friends/{userId}`
- **Curl**:
```bash
curl http://localhost:8080/friendships/friends/1
```
- **Response**: list of `User`.

### Delete Friendship
- **DELETE** `/friendships/{user1}/{user2}`
- **Curl**:
```bash
curl -X DELETE http://localhost:8080/friendships/1/2
```
- **Response**: `200 OK` with empty body.

## Groups

### Create Group
- **POST** `/groups`
- **Request body**:
```json
{
  "nombre": "Grupo de prueba",
  "descripcion": "Descripción",
  "imagenUrl": "http://example.com/logo.png"
}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/groups \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Grupo de prueba","descripcion":"Descripción","imagenUrl":"http://example.com/logo.png"}'
```

### Update Group
- **PUT** `/groups`
- **Request body**: same as create plus `id`.
```bash
curl -X PUT http://localhost:8080/groups \
  -H 'Content-Type: application/json' \
  -d '{"id":1,"nombre":"Grupo","descripcion":"Desc","imagenUrl":null}'
```
- **Response**:
```json
{"id":1,"nombre":"Grupo"}
```

### Get Group By Id
- **GET** `/groups/{id}`
```bash
curl http://localhost:8080/groups/1
```
- **Response**:
```json
{"id":1,"nombre":"Grupo"}
```

### Groups By Representative
- **GET** `/groups/representative/{userId}`
```bash
curl http://localhost:8080/groups/representative/1
```
- **Response**:
```json
[{"id":1,"nombre":"Grupo"}]
```

### Groups By Tags
- **GET** `/groups/tags?ids=1,2`
```bash
curl "http://localhost:8080/groups/tags?ids=1,2"
```
- **Response**:
```json
[{"id":1,"nombre":"Grupo"}]
```

### Search Groups
- **GET** `/groups/search?name=query`
```bash
curl "http://localhost:8080/groups/search?name=test"
```
- **Response**:
```json
[{"id":1,"nombre":"Grupo"}]
```

All these return `Group` or list of `Group` objects.

## Support Tickets

### Open Ticket
- **POST** `/tickets`
- **Request body**:
```json
{
  "descripcion": "Necesito ayuda",
  "reporter": {"id": 1}
}
```
- **Curl**:
```bash
curl -X POST http://localhost:8080/tickets \
  -H 'Content-Type: application/json' \
  -d '{"descripcion":"Necesito ayuda","reporter":{"id":1}}'
```
- **Response**: created `SupportTicket`.

### Close Ticket
- **POST** `/tickets/{id}/close`
```bash
curl -X POST http://localhost:8080/tickets/7/close
```
- **Response**:
```json
{"id":7,"estado":"CERRADO"}
```

### Get Ticket
- **GET** `/tickets/{id}`
```bash
curl http://localhost:8080/tickets/7
```
- **Response**:
```json
{"id":7,"descripcion":"Necesito ayuda","estado":"ABIERTO"}
```

## Trophies

### Get Trophy
- **GET** `/trophies/{id}`
```bash
curl http://localhost:8080/trophies/1
```
- **Response**:
```json
{"id":1,"nombre":"Participante"}
```

### Get Many Trophies
- **GET** `/trophies?ids=1,2`
```bash
curl "http://localhost:8080/trophies?ids=1,2"
```
- **Response**:
```json
[
  {"id":1,"nombre":"Participante"},
  {"id":2,"nombre":"Organizador"}
]
```

---

Objects not shown completely may include additional fields returned by the API. This reference should help front-end developers map the main structures.

