package com.example.BookApp.book.repository;

import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.repository.AuthorJpaRepository;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.book.enums.CoverType;
import com.example.BookApp.common.CommonMessageConstants;
import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.repository.PublisherJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.ConstraintViolationException;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookJpaRepositoryTest {

    @Autowired
    AuthorJpaRepository authorJpaRepository;

    @Autowired
    PublisherJpaRepository publisherJpaRepository;

    @Autowired
    BookJpaRepository bookJpaRepository;

    private final String PUBLISHER_NAME = "Publisher";
    private static final String TOO_LONG_STRING_32 = "32digitsIsMaxAllowedSoThisStringIsTooLong!";
    private static final String TOO_LONG_STRING_50 = "50digitsIsMaxAllowedSoThisStringIsTooLongForTheTitle!";
    private final String NAME = "Daniel";
    private final String NAME_2 = "Krzysztof";
    private final String SURNAME = "Ziola";
    private final String SURNAME_2 = "Jarzyna";
    private static final String TITLE = "Title";
    private static final String TITLE_2 = "Title_2";
    private static final String CITY = "City";
    private final Year YEAR_1999 = Year.of(1999);
    private static final String ISBN = " 988 - 998 -  83-111  -  12 ";
    private static final String ISBN_TOO_SHORT = " 988 - 998 - ";
    private static final String ISBN_TOO_LONG = " 988 - 998 -  83-111  -  1212 ";
    private final CoverType COVER_TYPE = CoverType.SOFT;

    @Test
    void saveBook() {

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthor(NAME_2, SURNAME_2);
        AuthorJpa authorJpa3 = createAuthor(NAME_2, SURNAME);

        AuthorJpa saved = authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = authorJpaRepository.save(authorJpa2);
        AuthorJpa saved3 = authorJpaRepository.save(authorJpa3);

        Set<AuthorJpa> authors = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        PublisherJpa publisherJpa = createPublisherJpa();

        PublisherJpa savedPublisherJpa = publisherJpaRepository.save(publisherJpa);

        BookJpa bookJpa = createBookJpa(TITLE, authors, CITY, savedPublisherJpa, YEAR_1999, ISBN, COVER_TYPE);

        // when
        BookJpa result = bookJpaRepository.save(bookJpa);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(TITLE);
        Assertions.assertThat(result.getAuthors()).isNotNull();
        Assertions.assertThat(result.getAuthors().stream().map(AuthorJpa::getFirstName).collect(Collectors.toList())) //
                .containsExactlyInAnyOrder(NAME, NAME_2, NAME_2);
        Assertions.assertThat(result.getAuthors().stream().map(AuthorJpa::getLastName).collect(Collectors.toList())) //
                .containsExactlyInAnyOrder(SURNAME, SURNAME, SURNAME_2);
        Assertions.assertThat(result.getAuthors().stream().map(AuthorJpa::getId).collect(Collectors.toList())) //
                .containsExactlyInAnyOrder(saved3.getId(), saved2.getId(), saved.getId());
        Assertions.assertThat(result.getCity()).isEqualTo(CITY);
        Assertions.assertThat(result.getPublictaionYear()).isEqualTo(YEAR_1999);
        Assertions.assertThat(result.getPublisher()).isNotNull();
        Assertions.assertThat(result.getPublisher().getId()).isEqualTo(savedPublisherJpa.getId());
        Assertions.assertThat(result.getPublisher().getName()).isEqualTo(PUBLISHER_NAME);
        Assertions.assertThat(result.getCover()).isEqualTo(COVER_TYPE);
        Assertions.assertThat(result.getIsbn()).isEqualTo("9889988311112");
        Assertions.assertThat(result.getPublicationNumber()).isNull();
        Assertions.assertThat(result.getVersion()).isNotNull();
        Assertions.assertThat(result.getAudit()).isNotNull();
        Assertions.assertThat(result.getAudit().getCreatedOn()).isNotNull();
        Assertions.assertThat(result.getAudit().getUpdatedOn()).isEqualTo(result.getAudit().getCreatedOn());
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void shouldThrowExceptionsWhenIncorrectDataProvided(String title, String city, String isbn, String msg) {

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthor(NAME_2, SURNAME_2);
        AuthorJpa authorJpa3 = createAuthor(NAME_2, SURNAME);

        AuthorJpa saved = authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = authorJpaRepository.save(authorJpa2);
        AuthorJpa saved3 = authorJpaRepository.save(authorJpa3);

        Set<AuthorJpa> authors = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        PublisherJpa publisherJpa = createPublisherJpa();

        PublisherJpa savedPublisherJpa = publisherJpaRepository.save(publisherJpa);

        BookJpa bookJpa = createBookJpa(title, authors, city, savedPublisherJpa, YEAR_1999, isbn, COVER_TYPE);

        // when
        // then
        Assertions.assertThatThrownBy(() -> bookJpaRepository.save(bookJpa)) //
                .isInstanceOf(ConstraintViolationException.class) //
                .hasMessageContaining(msg);
    }

    @Test
    void shouldThrowExceptionsWhenCoverTypeIsNull() {

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthor(NAME_2, SURNAME_2);
        AuthorJpa authorJpa3 = createAuthor(NAME_2, SURNAME);

        AuthorJpa saved = authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = authorJpaRepository.save(authorJpa2);
        AuthorJpa saved3 = authorJpaRepository.save(authorJpa3);

        Set<AuthorJpa> authors = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        PublisherJpa publisherJpa = createPublisherJpa();

        PublisherJpa savedPublisherJpa = publisherJpaRepository.save(publisherJpa);

        BookJpa bookJpa = createBookJpa(TITLE, authors, CITY, savedPublisherJpa, YEAR_1999, ISBN, null);

        // when
        // then
        Assertions.assertThatThrownBy(() -> bookJpaRepository.save(bookJpa)) //
                .isInstanceOf(ConstraintViolationException.class) //
                .hasMessageContaining(CommonMessageConstants.NOT_BLANK_MSG);
    }

    @Test
    void shouldThrowExceptionsWhenBookAlreadyExists() {

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthor(NAME_2, SURNAME_2);
        AuthorJpa authorJpa3 = createAuthor(NAME_2, SURNAME);

        AuthorJpa saved = authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = authorJpaRepository.save(authorJpa2);
        AuthorJpa saved3 = authorJpaRepository.save(authorJpa3);

        Set<AuthorJpa> authors = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        PublisherJpa publisherJpa = createPublisherJpa();

        PublisherJpa savedPublisherJpa = publisherJpaRepository.save(publisherJpa);

        BookJpa bookJpa = createBookJpa(TITLE, authors, CITY, savedPublisherJpa, YEAR_1999, ISBN, COVER_TYPE);
        bookJpaRepository.save(bookJpa);

        BookJpa bookJpa2 = createBookJpa(TITLE, authors, CITY, savedPublisherJpa, YEAR_1999, ISBN, COVER_TYPE);

        // when
        // then
        Assertions.assertThatThrownBy(() -> bookJpaRepository.save(bookJpa2)) //
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldSaveBookWithTheSameParametersButOtherTitle() {

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthor(NAME_2, SURNAME_2);
        AuthorJpa authorJpa3 = createAuthor(NAME_2, SURNAME);

        AuthorJpa saved = authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = authorJpaRepository.save(authorJpa2);
        AuthorJpa saved3 = authorJpaRepository.save(authorJpa3);

        Set<AuthorJpa> authors = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        Set<AuthorJpa> authors2 = new HashSet<>();
        authors.add(saved);
        authors.add(saved2);
        authors.add(saved3);

        PublisherJpa publisherJpa = createPublisherJpa();

        PublisherJpa savedPublisherJpa = publisherJpaRepository.save(publisherJpa);

        BookJpa bookJpa = createBookJpa(TITLE, authors, CITY, savedPublisherJpa, YEAR_1999, ISBN, COVER_TYPE);
        bookJpaRepository.save(bookJpa);

        BookJpa bookJpa2 = createBookJpa(TITLE_2, authors2, CITY, savedPublisherJpa, YEAR_1999, ISBN, COVER_TYPE);

        // when
        BookJpa result = bookJpaRepository.save(bookJpa2);

        // then
        Assertions.assertThat(result).isNotNull();
    }


    private AuthorJpa createAuthor(String firstName, String lastName) {
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setFirstName(firstName);
        authorJpa.setLastName(lastName);
        return authorJpa;
    }

    private PublisherJpa createPublisherJpa() {
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setName(PUBLISHER_NAME);
        return publisherJpa;
    }

    private BookJpa createBookJpa(String title, Set<AuthorJpa> authors, String city, PublisherJpa publisherJpa, Year year, String isbn, CoverType type) {
        BookJpa bookJpa = new BookJpa();
        bookJpa.setTitle(title);
        bookJpa.setAuthors(authors);
        bookJpa.setCity(city);
        bookJpa.setPublisher(publisherJpa);
        bookJpa.setPublictaionYear(year);
        bookJpa.setIsbn(isbn);
        bookJpa.setCover(type);
        return bookJpa;
    }

    private static Stream<Arguments> prepareData() {
        return Stream.of(
                Arguments.of(null, CITY, ISBN, CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("", CITY, ISBN, CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("  ", CITY, ISBN, CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("A", CITY, ISBN, CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TOO_LONG_STRING_50, CITY, ISBN, CommonMessageConstants.VALUE_TOO_LONG),
                Arguments.of(TITLE, "A", ISBN, CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TITLE, TOO_LONG_STRING_32, ISBN, CommonMessageConstants.VALUE_TOO_LONG),
                Arguments.of(TITLE, CITY, null, CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of(TITLE, CITY, "", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of(TITLE, CITY, "  ", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of(TITLE, CITY, ISBN_TOO_SHORT, CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TITLE, CITY, ISBN_TOO_LONG, CommonMessageConstants.VALUE_TOO_LONG)
        );
    }


}