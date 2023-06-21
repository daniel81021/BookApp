CREATE TABLE publisher (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(32) NOT NULL,
   version BIGINT NULL,
   created_on date NULL,
   updated_on date NULL,
   CONSTRAINT pk_publisher PRIMARY KEY (id)
);

ALTER TABLE publisher ADD CONSTRAINT uc_publisher_name UNIQUE (name);