# API Endpoints

All endpoints are prefixed with the base URL of the running server, for example `http://localhost:8080` during local development.

## Authentication

### `POST /auth/register`
Registers a new user.

```bash
curl -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"secret","username":"testuser","firstName":"John","lastName":"Doe","isExternal":false}'
```
Response example:
```json
{
  "accessToken": "<jwt-token>",
  "refreshToken": "<refresh-token>"
}
```

### `POST /auth/login`
Authenticates an existing user.

```bash
curl -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"secret"}'
```
Response example is the same as `POST /auth/register`.

### `POST /auth/refresh`
Obtains a new access token from a refresh token. Send the refresh token as a bearer token.

```bash
curl -X POST "$BASE_URL/auth/refresh" \
  -H "Authorization: Bearer <refresh-token>"
```
Response example is the same as `POST /auth/register`.

### `POST /auth/logout`
Logs out the current user. Requires authentication.

```bash
curl -X POST "$BASE_URL/auth/logout"
```
Response: `204 No Content` on success.

## Users

### `PUT /users`
Updates the current user profile. Fields mirror the `User` entity.

```bash
curl -X PUT "$BASE_URL/users" \
  -H "Content-Type: application/json" \
  -d '{"id":1,"correoUbb":"user@ubb.cl","nombres":"Juan","apellidos":"Pérez","username":"juan","campus":null}'
```
Response example:
```json
{
  "id": 1,
  "correoUbb": "user@ubb.cl",
  "nombres": "Juan",
  "apellidos": "Pérez",
  "username": "juan",
  "campus": null,
  "estado": "ACTIVO"
}
```

### `GET /users/{id}`
Returns a single user by id.

```bash
curl "$BASE_URL/users/1"
```
Response example follows the same shape as above.

### `GET /users/{id}/summary`
Returns a profile summary for the user.

```bash
curl "$BASE_URL/users/1/summary"
```
Response example:
```json
{
  "totalEvents": 3,
  "friends": 10,
  "trophies": 2
}
```

### `GET /users/{id}/events`
Lists events created by the user.

```bash
curl "$BASE_URL/users/1/events"
```
Response example:
```json
[
  {"id": 5, "titulo": "Charla"},
  {"id": 6, "titulo": "Taller"}
]
```

### `GET /users/{id}/calendar`
Returns calendar entries for the user.

```bash
curl "$BASE_URL/users/1/calendar"
```
Response example:
```json
[
  {"eventId": 5, "fechaInicio": "2024-01-01T10:00:00"}
]
```

### `GET /users/{id}/to-attend`
Returns events the user should attend.

```bash
curl "$BASE_URL/users/1/to-attend"
```
Response example:
```json
{
  "recommended": [ {"id": 7, "titulo": "Feria"} ]
}
```

### `GET /users/{id}/recommendations`
Returns up to 10 random users that are not yet friends.

```bash
curl "$BASE_URL/users/1/recommendations"
```
Response example:
```json
[
  {"id": 3, "username": "ana"}
]
```

## Events

### `POST /events`
Creates an event.

```bash
curl -X POST "$BASE_URL/events" \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Evento de prueba","descripcion":"Descripción","fechaInicio":"2024-01-01T10:00:00","fechaFin":"2024-01-01T12:00:00","lugar":"Auditorio","aforoMax":100}'
```
Response example:
```json
{
  "id": 10,
  "titulo": "Evento de prueba",
  "estadoValidacion": "PENDIENTE"
}
```

### `PUT /events`
Updates an event. Body is the same as creation with an `id` field.

### `POST /events/{id}/approve`
Marks the event as approved.

### `DELETE /events/{id}`
Removes an event.

### `GET /events/{id}`
Returns a single event.

### `GET /events/creator/{userId}`
Lists events created by a specific user.

### `GET /events/group/{groupId}`
Lists events belonging to a group.

### `GET /events/upcoming?after={datetime}`
Lists upcoming events after the given ISO date-time.

### `GET /events/public`
Lists all public events.

### `GET /events/pending`
Lists events waiting for approval.

### `GET /events?ids=1&ids=2`
Fetches multiple events by id.

Example for read-only endpoints:
```bash
curl "$BASE_URL/events/5"
```
Response example is similar to the one returned on creation.

## Registrations

### `POST /registrations`
Registers a user to an event.

```bash
curl -X POST "$BASE_URL/registrations" \
  -H "Content-Type: application/json" \
  -d '{"id":{"eventId":10,"userId":1}}'
```
Response example:
```json
{"id":{"eventId":10,"userId":1},"estado":"INSCRITO"}
```

### `POST /registrations/cancel`
Cancels a registration.

```bash
curl -X POST "$BASE_URL/registrations/cancel" \
  -H "Content-Type: application/json" \
  -d '{"eventId":10,"userId":1}'
```
Response example is the same as above with updated `estado`.

### `GET /registrations/{eventId}/{userId}`
Gets a specific registration.

## Friendships

### `POST /friendships`
Creates a friendship request.

```bash
curl -X POST "$BASE_URL/friendships" \
  -H "Content-Type: application/json" \
  -d '{"id":{"user1Id":1,"user2Id":2}}'
```
Response example:
```json
{"id":{"user1Id":1,"user2Id":2},"estado":"PENDIENTE"}
```

### `POST /friendships/accept`
Accepts a friendship.

```bash
curl -X POST "$BASE_URL/friendships/accept" \
  -H "Content-Type: application/json" \
  -d '{"user1Id":1,"user2Id":2}'
```

### `POST /friendships/reject`
Rejects a friendship request.

```bash
curl -X POST "$BASE_URL/friendships/reject" \
  -H "Content-Type: application/json" \
  -d '{"user1Id":1,"user2Id":2}'
```

### `GET /friendships/pending/{userId}`
Lists pending friendship requests for a user.

### `GET /friendships/{user1}/{user2}`
Gets friendship info.

## Groups

### `POST /groups`
Creates a group.

```bash
curl -X POST "$BASE_URL/groups" \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Grupo de prueba","descripcion":"Descripción"}'
```

### `PUT /groups`
Updates a group.

### `GET /groups/{id}`
Returns a group by id.

### `GET /groups/representative/{userId}`
Groups where the user is representative.

### `GET /groups/tags?ids=1&ids=2`
Groups filtered by tag ids.

### `GET /groups/search?name=text`
Search groups by name substring.

## Support Tickets

### `POST /tickets`
Opens a support ticket.

```bash
curl -X POST "$BASE_URL/tickets" \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"Necesito ayuda","reporter":{"id":1}}'
```

### `POST /tickets/{id}/close`
Closes a ticket.

### `GET /tickets/{id}`
Fetch a ticket by id.

## Trophies

### `GET /trophies/{id}`
Get a single trophy.

### `GET /trophies?ids=1&ids=2`
Get multiple trophies.

Response example for trophy endpoints:
```json
[{"id":1,"nombre":"Trofeo"}]
```
