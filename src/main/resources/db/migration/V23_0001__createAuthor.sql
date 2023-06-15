CREATE TABLE author (
  id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(32) NOT NULL,
   last_name VARCHAR(32) NOT NULL,
   version BIGINT NULL,
   created_on date NULL,
   updated_on date NULL,
   CONSTRAINT pk_author PRIMARY KEY (id)
);

ALTER TABLE author ADD CONSTRAINT UniqueFirstAndLastName UNIQUE (first_name, last_name);