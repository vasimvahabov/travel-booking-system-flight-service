DROP TABLE IF EXISTS flights;
CREATE TABLE flights (

    id BIGSERIAL PRIMARY KEY NOT NULL,

    version INTEGER NOT NULL,

    airplaneId BIGSERIAL NOT NULL,

    number VARCHAR(255) UNIQUE NOT NULL,

    departure_airport_code VARCHAR(255) NOT NULL,

    arrival_airport_code VARCHAR(255) NOT NULL,

    departure_date_time TIMESTAMP NOT NULL,

    arrival_date_time TIMESTAMP NOT NULL,

    created_date_time TIMESTAMP NOT NULL,

    last_modified_date_time TIMESTAMP NOT NULL,

    price NUMERIC(5, 2) NOT NULL

);