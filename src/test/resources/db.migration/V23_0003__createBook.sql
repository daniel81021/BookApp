CREATE TABLE book (
  id BIGINT AUTO_INCREMENT NOT NULL,
   title VARCHAR(50) NOT NULL,
   publisher_id BIGINT NULL,
   place_of_publication VARCHAR(32) NOT NULL,
   year_of_publication SMALLINT NULL,
   publication_number INT NULL,
   isbn_number VARCHAR(13) NOT NULL,
   cover VARCHAR(255) NOT NULL,
   version BIGINT NULL,
   created_on date NULL,
   updated_on date NULL,
   CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE book_authors (
  book_jpa_id BIGINT NOT NULL,
   authors_id BIGINT NOT NULL,
   CONSTRAINT pk_book_authors PRIMARY KEY (book_jpa_id, authors_id)
);

ALTER TABLE book ADD CONSTRAINT UniqueBook UNIQUE (title, publisher_id, year_of_publication, isbn_number, cover);

ALTER TABLE book_authors ADD CONSTRAINT uc_book_authors_authors UNIQUE (authors_id);

ALTER TABLE book ADD CONSTRAINT FK_BOOK_ON_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES publisher (id);

ALTER TABLE book_authors ADD CONSTRAINT fk_booaut_on_author_jpa FOREIGN KEY (authors_id) REFERENCES author (id);

ALTER TABLE book_authors ADD CONSTRAINT fk_booaut_on_book_jpa FOREIGN KEY (book_jpa_id) REFERENCES book (id);