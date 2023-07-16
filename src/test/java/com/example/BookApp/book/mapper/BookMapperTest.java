package com.example.BookApp.book.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    private final Long ID = 1L;
    private final Long ID_2 = 2L;
    private final Long ID_3 = 3L;
    private final String PUBLISHER_NAME = "Publisher";
    private final String PUBLISHER_NAME_2 = "Publisher 2";
    private final Long VERSION = 1L;
    private final String NAME = "Name";
    private final String NAME_2 = "Name_2";
    private final String NAME_3 = "Name_3";
    private final String SURNAME = "Surname";
    private final String SURNAME_2 = "Surname_2";
    private final String SURNAME_3 = "Surname_3";
    private final String CITY = "City";
    private final String YEAR = "1999";
    private final Integer PUB_NO = 1;
    private final String ISBN = "988-99-993-87-098";
    private final String ISBN_2 = "988-99-993";
    private final CoverType COVER_TYPE = CoverType.SOFT;
    private final String TITLE = "Title";
    private final String TITLE_2 = "Title_2";

    @Test
    void toBookJpa() {

        // given
        Author author = createAuthor(ID, NAME, SURNAME);
        Author author2 = createAuthor(ID_2, NAME_2, SURNAME_2);
        Author author3 = createAuthor(ID_3, NAME_3, SURNAME_3);

        Set<Author> authors = new HashSet<>();
        authors.add(author);
        authors.add(author2);
        authors.add(author3);

        Publisher publisher = createPublisher(ID, PUBLISHER_NAME);

        Book book = createBook(ID, TITLE, authors, publisher, ISBN);

        // when
        BookJpa r = bookMapper.toBookJpa(book);

        // then
        Assertions.assertThat(r).isNotNull();
        Assertions.assertThat(r) //
                .usingRecursiveComparison() //
                .ignoringFields("audit", "publisher.audit", "authors.audit") //
                .isEqualTo(book);
        Assertions.assertThat(r).isInstanceOf(BookJpa.class);
    }

    @Test
    void testWhenBookIsNull() {

        // given
        // when
        BookJpa r = bookMapper.toBookJpa(null);

        // then
        Assertions.assertThat(r).isNull();
    }

    @Test
    void testWhenBookJpaIsNull() {

        // given
        // when
        Book r = bookMapper.toBook(null);

        // then
        Assertions.assertThat(r).isNull();
    }

    @Test
    void toBook() {

        // given
        AuthorJpa author = createAuthorJpa(ID, NAME, SURNAME);
        AuthorJpa author2 = createAuthorJpa(ID_2, NAME_2, SURNAME_2);
        AuthorJpa author3 = createAuthorJpa(ID_3, NAME_3, SURNAME_3);

        Set<AuthorJpa> authorJpas = new HashSet<>();
        authorJpas.add(author);
        authorJpas.add(author2);
        authorJpas.add(author3);

        PublisherJpa publisher = createPublisherJpa(ID_2, PUBLISHER_NAME_2);

        BookJpa bookJpa = createBookJpa(ID_2, TITLE_2, authorJpas, publisher, ISBN_2);

        // when
        Book r = bookMapper.toBook(bookJpa);

        // then
        Assertions.assertThat(r).isNotNull();
        Assertions.assertThat(r) //
                .usingRecursiveComparison() //
                .ignoringFields("audit", "publisher.audit", "authors.audit") //
                .isEqualTo(bookJpa);
        Assertions.assertThat(r).isInstanceOf(Book.class);
    }

    private Audit createAudit() {
        Audit audit = new Audit();
        audit.setCreatedOn(LocalDate.of(1999, 1, 1));
        audit.setUpdatedOn(LocalDate.of(2000, 1, 1));
        return audit;
    }

    private Publisher createPublisher(Long id, String name) {
        return Publisher.builder() //
                .id(id) //
                .name(name) //
                .version(VERSION) //
                .build();
    }

    private PublisherJpa createPublisherJpa(Long id, String name) {
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setId(id);
        publisherJpa.setName(name);
        publisherJpa.setVersion(VERSION);
        publisherJpa.setAudit(createAudit());
        return publisherJpa;
    }

    private Author createAuthor(Long id, String name, String surname) {
        return Author.builder() //
                .id(id) //
                .firstName(name) //
                .lastName(surname) //
                .version(VERSION) //
                .audit(new Audit()) //
                .build();
    }

    private AuthorJpa createAuthorJpa(Long id, String name, String surname) {
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setId(id);
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        authorJpa.setVersion(VERSION);
        authorJpa.setAudit(new Audit());
        return authorJpa;
    }

    private Book createBook(Long id, String title, Set<Author> authors, Publisher publisher, String isbn) {
        return Book.builder() //
                .id(id) //
                .title(title) //
                .authors(authors) //
                .city(CITY) //
                .publicationYear(YEAR) //
                .publicationNumber(PUB_NO) //
                .publisher(publisher) //
                .isbn(isbn) //
                .cover(COVER_TYPE) //
                .version(VERSION) //
                .audit(new Audit()) //
                .build();
    }

    private BookJpa createBookJpa(Long id, String title, Set<AuthorJpa> authorJpas, PublisherJpa publisher, String isbn) {
        Audit audit = createAudit();
        BookJpa bookJpa = new BookJpa(); //
        bookJpa.setId(id); //
        bookJpa.setTitle(title); //
        bookJpa.setAuthors(authorJpas); //
        bookJpa.setCity(CITY); //
        bookJpa.setPublicationYear(YEAR); //
        bookJpa.setPublicationNumber(PUB_NO); //
        bookJpa.setPublisher(publisher); //
        bookJpa.setIsbn(isbn); //
        bookJpa.setCover(COVER_TYPE); //
        bookJpa.setVersion(VERSION); //
        bookJpa.setAudit(audit); //
        return bookJpa;
    }
}