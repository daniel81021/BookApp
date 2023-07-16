package com.example.BookApp.book.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.domain.AuthorVM;
import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.book.domain.BookVM;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.domain.PublisherVM;
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
class BookVMMapperTest {

    @Autowired
    private BookVMMapper mapper;

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
    void toBookVM() {

        // given
        Author author = createAuthor(ID, NAME, SURNAME);
        Author author2 = createAuthor(ID_2, NAME_2, SURNAME_2);
        Author author3 = createAuthor(ID_3, NAME_3, SURNAME_3);

        Set<Author> authors = new HashSet<>(Set.of(author, author2, author3));

        Publisher publisher = createPublisher(ID, PUBLISHER_NAME);

        Book book = createBook(ID, TITLE, authors, publisher, ISBN);

        // when
        BookVM bookVM = this.mapper.toBookVM(book);

        // then
        Assertions.assertThat(bookVM).isNotNull();
        Assertions.assertThat(bookVM) //
                .usingRecursiveComparison() //
                .ignoringFields("audit") //
                .isEqualTo(book);

    }

    @Test
    void toBook() {

        // given
        AuthorVM authorVM = createAuthorVM(ID_3, NAME_3, SURNAME_3);
        AuthorVM authorVM2 = createAuthorVM(ID_2, NAME_2, SURNAME_2);
        AuthorVM authorVM3 = createAuthorVM(ID, NAME, SURNAME);

        Set<AuthorVM> authorVMs = new HashSet<>(Set.of(authorVM, authorVM2, authorVM3));

        PublisherVM publisherVM = createPublisherVM(ID, PUBLISHER_NAME_2);

        BookVM bookVM = createBookVM(ID, TITLE_2, authorVMs, publisherVM, ISBN_2);

        // when
        Book book = this.mapper.toBook(bookVM);

        // then
        Assertions.assertThat(book).isNotNull();
    }
    private Publisher createPublisher(Long id, String name) {
        return Publisher.builder() //
                .id(id) //
                .name(name) //
                .version(VERSION) //
                .build();
    }

    private PublisherVM createPublisherVM(Long id, String name){
        return PublisherVM.builder()
                .id(id) //
                .name(name) //
                .version(VERSION) //
                .build();
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

    private AuthorVM createAuthorVM(Long id, String name, String surname) {
        return AuthorVM.builder() //
                .firstName(name) //
                .lastName(surname) //
                .id(id) //
                .version(VERSION) //
                .build();
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
                .audit(new Audit()) //
                .version(VERSION) //
                .build();
    }

    private BookVM createBookVM(Long id, String title, Set<AuthorVM> authorVMs, PublisherVM publisherVM, String isbn) {
        return BookVM.builder() //
                .id(id) //
                .title(title) //
                .authors(authorVMs) //
                .city(CITY) //
                .publicationYear(YEAR) //
                .publicationNumber(PUB_NO) //
                .publisher(publisherVM) //
                .isbn(isbn) //
                .cover(COVER_TYPE) //
                .version(VERSION) //
                .build();
    }
}