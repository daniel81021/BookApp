CREATE TABLE product_jpa (
  id BIGINT AUTO_INCREMENT NOT NULL,
   book_id BIGINT NULL,
   product_name VARCHAR(50) NOT NULL,
   `condition` VARCHAR(255) NOT NULL,
   rating VARCHAR(255) NOT NULL,
   buy_price DOUBLE NULL,
   version BIGINT NULL,
   created_on date NULL,
   updated_on date NULL,
   CONSTRAINT pk_productjpa PRIMARY KEY (id)
);

ALTER TABLE product_jpa ADD CONSTRAINT FK_PRODUCTJPA_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);