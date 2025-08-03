DROP TABLE IF EXISTS books;
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    version INTEGER NOT NULL,
    airplaneId BIGSERIAL NOT NULL,
    number VARCHAR(255) UNIQUE NOT NULL,
    departureAirportCode VARCHAR(255) NOT NULL,
    arrivalAirportCode VARCHAR(255) NOT NULL,
    departureDateTime TIMESTAMP NOT NULL,
    arrivalDateTime TIMESTAMP NOT NULL,
    price NUMERIC(5, 2) NOT NULL
);