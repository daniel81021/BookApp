package com.example.BookApp.author.service.impl;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.repository.AuthorJpaRepository;
import com.example.BookApp.author.service.AuthorJpaService;
import com.example.BookApp.common.CommonMessageConstants;
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
import java.util.stream.Stream;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorJpaServiceImplIntegrationTest {
    private static final String NAME = "Daniel";
    private static final String NAME_2 = "Emil";
    private static final String SURNAME = "Ziola";
    private static final String SURNAME_2 = "Karewicz";
    private static final String TOO_LONG_STRING_32 = "32digitsIsMaxAllowedSoThisStringIsTooLong!";

    @Autowired
    AuthorJpaService authorJpaService;
    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @ParameterizedTest
    @MethodSource("provideValues")
    void saveAuthor(String name, String surname) {

        // given
        Author author = createAuthor(name, surname);

        // when
        Author savedAuthor = authorJpaService.saveAuthor(author);

        // then
        Assertions.assertThat(savedAuthor).isNotNull();
        Assertions.assertThat(savedAuthor.getFirstName()).isEqualTo(name);
        Assertions.assertThat(savedAuthor.getLastName()).isEqualTo(surname);
        Assertions.assertThat(savedAuthor.getId()).isNotNull();
        Assertions.assertThat(savedAuthor.getVersion()).isNotNull();
        Assertions.assertThat(savedAuthor.getAudit()).isNotNull();
        Assertions.assertThat(savedAuthor.getAudit().getCreatedOn()).isEqualTo(savedAuthor.getAudit().getUpdatedOn());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void shouldThrowExceptionsWhenIncorrectName(String name, String msg) {

        // given
        Author author = createAuthor(name, SURNAME);

        // when
        // then
        Assertions.assertThatThrownBy(() -> authorJpaService.saveAuthor(author))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(msg);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void shouldThrowExceptionsWhenIncorrectSurname(String surname, String msg) {

        // given
        Author author = createAuthor(NAME, surname);

        // when
        // then
        Assertions.assertThatThrownBy(() -> authorJpaService.saveAuthor(author))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(msg);
    }

    @Test
    void shouldThrowExceptionWhenAuthorSavedSecondTime() {

        // given
        Author author = createAuthor(NAME, SURNAME);
        Author savedAuthor = authorJpaService.saveAuthor(author);
        Author author2 = createAuthor(NAME, SURNAME);

        // when
        // then
        Assertions.assertThat(savedAuthor) //
                .usingRecursiveComparison() //
                .ignoringFields("id", "version", "audit") //
                .isEqualTo(author);
        Assertions.assertThat(savedAuthor.getId()).isNotNull();
        Assertions.assertThat(savedAuthor.getVersion()).isNotNull();
        Assertions.assertThatThrownBy(() -> authorJpaService.saveAuthor(author2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void shouldFindAuthorById(){

        // given
        AuthorJpa authorJpa = createAuthorJpa(NAME, SURNAME);
        AuthorJpa savedAuthor = authorJpaRepository.save(authorJpa);

        AuthorJpa authorJpa2 = createAuthorJpa(NAME_2, SURNAME_2);
        AuthorJpa savedAuthor2 = authorJpaRepository.save(authorJpa2);

        // when
        Author foundById = authorJpaService.findById(savedAuthor2.getId());

        // then
        Assertions.assertThat(foundById).isNotNull();
        Assertions.assertThat(foundById.getClass()).isEqualTo(Author.class);
        Assertions.assertThat(foundById.getId()).isEqualTo(savedAuthor2.getId());
        Assertions.assertThat(foundById.getFirstName()).isEqualTo(savedAuthor2.getFirstName());
        Assertions.assertThat(foundById.getLastName()).isEqualTo(savedAuthor2.getLastName());
        Assertions.assertThat(foundById.getVersion()).isEqualTo(savedAuthor2.getVersion());
    }

    @Test
    void shouldThrowExceptionWhenIdIsIncorrect(){

        // given
        // when
        // then
        Assertions.assertThatThrownBy(()-> authorJpaService.findById(100L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Author createAuthor(String name, String surname) {
        return Author.builder() //
                .firstName(name) //
                .lastName(surname) //
                .build();
    }

    private AuthorJpa createAuthorJpa(String name, String surname){
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        return authorJpa;
    }

    private static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(NAME, SURNAME),
                Arguments.of(NAME_2, SURNAME_2),
                Arguments.of(NAME, SURNAME_2),
                Arguments.of(NAME_2, SURNAME)
        );
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of("", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("  ", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("A", CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TOO_LONG_STRING_32, CommonMessageConstants.VALUE_TOO_LONG)
        );
    }


}