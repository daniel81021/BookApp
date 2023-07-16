package com.example.BookApp.book.service.impl;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.book.mapper.BookMapper;
import com.example.BookApp.book.repository.BookJpaRepository;
import com.example.BookApp.book.service.BookJpaService;
import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BookJpaServiceImplTest {

    private static final String PUBLISHER_NAME = "Publisher";
    private final String NAME = "Daniel";
    private static final String NAME_2 = "Krzysztof";
    private static final String SURNAME = "Ziola";
    private static final String SURNAME_2 = "Jarzyna";
    private static final String TITLE = "Title";
    private static final String CITY = "City";
    private static final String YEAR_1999 = "1999";
    private static final String ISBN = " 988 - 998 -  83-111  -  12 ";
    private static final CoverType COVER_TYPE = CoverType.SOFT;
    private static final Long ID = 1L;
    private static final Long ID_2 = 2L;
    private static final Long VERSION = 1L;

    @Mock
    private BookJpaRepository bookJpaRepositoryMock;

    @Mock
    private BookMapper bookMapperMock;

    @InjectMocks
    private BookJpaServiceImpl bookJpaServiceImpl;

    @Test
    void save() {

        // given
        Author author = createAuthor(ID, NAME, SURNAME, VERSION);
        Author author2 = createAuthor(ID_2, NAME_2, SURNAME_2, VERSION);
        Set<Author> authors = new HashSet<>(Set.of(author, author2));

        Publisher publisher = createPublisher();
        Book book = createBook(null, TITLE, authors, CITY, publisher, YEAR_1999, ISBN, COVER_TYPE, null);

        AuthorJpa authorJpa = createAuthorJpa(ID, NAME, SURNAME, VERSION);
        AuthorJpa authorJpa2 = createAuthorJpa(ID_2, NAME_2, SURNAME_2, VERSION);

        Set<AuthorJpa> authorJpas = new HashSet<>(Set.of(authorJpa, authorJpa2));

        PublisherJpa publisherJpa = createPublisherJpa();

        BookJpa bookJpa = createBookJpa(null, TITLE, authorJpas, CITY, publisherJpa, YEAR_1999, ISBN, COVER_TYPE, null);
        BookJpa savedJpa = createBookJpa(ID, TITLE, authorJpas, CITY, publisherJpa, YEAR_1999, ISBN, COVER_TYPE, VERSION);

        Book savedBook = createBook(ID, TITLE, authors, CITY, publisher, YEAR_1999, ISBN, COVER_TYPE, VERSION);


        Mockito.when(bookMapperMock.toBookJpa(book)).thenReturn(bookJpa);
        Mockito.when(bookJpaRepositoryMock.save(bookJpa)).thenReturn(savedJpa);
        Mockito.when(bookMapperMock.toBook(savedJpa)).thenReturn(savedBook);

        // when
        Book r = bookJpaServiceImpl.save(book);

        // then
        Assertions.assertThat(r).isNotNull();
        Assertions.assertThat(r) //
                .usingRecursiveComparison() //
                .ignoringFields("id", "version", "audit", "author.audit", "publisher.audit") //
                .isEqualTo(book);
    }

    private AuthorJpa createAuthorJpa(Long id, String firstName, String lastName, Long version) {
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setId(id);
        authorJpa.setFirstName(firstName);
        authorJpa.setLastName(lastName);
        authorJpa.setVersion(version);
        return authorJpa;
    }

    private PublisherJpa createPublisherJpa() {
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setId(ID);
        publisherJpa.setName(PUBLISHER_NAME);
        publisherJpa.setVersion(VERSION);
        return publisherJpa;
    }

    private BookJpa createBookJpa(Long id, String title, Set<AuthorJpa> authors, String city, PublisherJpa publisherJpa, String year, String isbn, CoverType type, Long version) {
        BookJpa bookJpa = new BookJpa();
        bookJpa.setId(id);
        bookJpa.setTitle(title);
        bookJpa.setAuthors(authors);
        bookJpa.setCity(city);
        bookJpa.setPublisher(publisherJpa);
        bookJpa.setPublicationYear(year);
        bookJpa.setIsbn(isbn);
        bookJpa.setCover(type);
        bookJpa.setAudit(new Audit());
        bookJpa.setVersion(version);
        return bookJpa;
    }

    private Author createAuthor(Long id, String firstName, String lastName, Long version) {
        return Author.builder() //
                .id(id) //
                .firstName(firstName) //
                .lastName(lastName) //
                .audit(new Audit()) //
                .version(version) //
                .build();
    }

    private Publisher createPublisher() {
        return Publisher.builder() //
                .id(ID) //
                .name(NAME) //
                .version(VERSION) //
                .build();
    }

    private Book createBook(Long id, String title, Set<Author> authors, String city, Publisher publisher, String year, String isbn, CoverType type, Long version) {
        return Book.builder() //
                .id(id) //
                .title(title) //
                .authors(authors) //
                .city(city) //
                .publicationYear(year) //
                .publisher(publisher) //
                .publicationNumber(1) //
                .isbn(isbn) //
                .cover(type) //
                .version(version) //
                .build();
    }
}