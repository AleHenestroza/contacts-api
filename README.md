# TiendaNube Contacts API

Simple CRUD API for managing the contacts of an email marketing service. It allows all basic CRUD operations like

- Create a new contact
- Retrieve a contact by ID
- Update a contact (supporting partial updates)
- Delete a contact

## Requirements

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

This project can be run entirely inside Docker containers, since it's using a docker-compose solution for both the API
and the MongoDB database.

In order to run the project, make sure you have both Docker and Docker Compose installed on your machine. The Docker
daemon must be running in order to build and run the containers. To run the project, follow the instructions below.

- Clone the repository

```shell
git clone https://github.com/AleHenestroza/contacts-api.git
```

- Change to the project directory

```shell
cd contacts-api
```

- Create a `.env` file based on the [.env-example](.env-example) file

```shell
cp .env.example .env
```

- Run the project

```shell
docker-compose up --build
```

- The API will be available at `http://localhost:8080` (default port used is 8080, to change this, modify the
  variable `BACKEND_PORT` in the .env file)

## API Documentation

The API Swagger documentation is available at `http://localhost:8080/swagger-ui/index.html`. You can use the Swagger UI
to test the endpoints and see the expected request and response formats. OpenAPI definitions can be found
at `http://localhost:8080/api/docs`.

Alternatively, a tool like Postman can be used to test the API. The API has the following endpoints:

- `POST /contacts`: Create a new contact

```shell
curl --location --request POST 'http://localhost:8080/contacts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "testuser@gmail.com",
    "first_name": "Test",
    "last_name": "User"
}'
```

- `GET /contacts/{id}`: List all contacts

```shell
curl --location -g --request GET 'http://localhost:8080/contacts/{id}'
```

- `PUT /contacts/{id}`: Update a contact (partial update supported)

```shell
curl --location -g --request PUT 'http://localhost:8080/contacts/{id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "newemail@email.com",
    "first_name": "NewFirstName",
    "last_name": "NewLastName"
}'
```

- `DELETE /contacts/{id}`: Delete a contact

```shell
curl --location -g --request DELETE 'http://localhost:8080/contacts/{id}'
```
