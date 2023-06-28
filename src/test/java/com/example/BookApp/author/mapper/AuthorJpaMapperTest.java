package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.common.Audit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@SpringBootTest
class AuthorJpaMapperTest {

    private final Long ID = 1L;
    private final Long ID_2 = 2L;
    private final Long ID_3 = 3L;
    private final Long VERSION = 1L;
    private static final String NAME = "Name";
    private static final String NAME_2 = "Name_2";
    private static final String NAME_3 = "Name_3";
    private static final String SURNAME = "Surname";
    private static final String SURNAME_2 = "Surname_2";
    private static final String SURNAME_3 = "Surname_3";

    @Autowired
    AuthorJpaMapper authorJpaMapper;

    @ParameterizedTest
    @MethodSource("provideValues")
    void toAuthorJpa(String name, String surname) {

        // given
        Author author = createAuthor(name, surname);

        // when
        AuthorJpa authorJpa = authorJpaMapper.toAuthorJpa(author);

        // then
        Assertions.assertThat(authorJpa) //
                .usingRecursiveComparison() //
                .isEqualTo(author);

    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void toAuthor(String name, String surname) {

        // given
        AuthorJpa authorJpa = createAuthorJpa(name, surname);

        // when
        Author author = authorJpaMapper.toAuthor(authorJpa);

        // then
        Assertions.assertThat(author) //
                .usingRecursiveComparison() //
                .isEqualTo(authorJpa);
    }

    @Test
    void authorsToAuthorJpas() {

        // given
        Author author = createAuthor(NAME, SURNAME);
        Author author2 = createAuthor(NAME_2, SURNAME_2);
        author2.setId(ID_2);
        Author author3 = createAuthor(NAME_3, SURNAME_3);
        author3.setId(ID_3);

        List<Author> authors = List.of(author, author2, author3);

        // when
        List<AuthorJpa> result = authorJpaMapper.toAuthorJpas(authors);

        // then
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.stream().map(AuthorJpa::getId)) //
                .containsExactlyInAnyOrder(ID_3, ID_2, ID);
        Assertions.assertThat(result.stream().map(AuthorJpa::getFirstName)) //
                .containsExactlyInAnyOrder(NAME_3, NAME_2, NAME);
        Assertions.assertThat(result.stream().map(AuthorJpa::getLastName)) //
                .containsExactlyInAnyOrder(SURNAME_3, SURNAME_2, SURNAME);
        Assertions.assertThat(result.stream().map(AuthorJpa::getAudit)).noneMatch(Objects::isNull);
        Assertions.assertThat(result.stream().map(r -> r.getAudit().getCreatedOn())).allMatch(Objects::isNull);
        Assertions.assertThat(result.stream().map(r -> r.getAudit().getUpdatedOn())).allMatch(Objects::isNull);
    }

    @Test
    void AuthorJpasToAuthors() {

        // given
        AuthorJpa authorJpa = createAuthorJpa(NAME, SURNAME);
        AuthorJpa authorJpa2 = createAuthorJpa(NAME_2, SURNAME_2);
        authorJpa2.setId(ID_2);
        AuthorJpa authorJpa3 = createAuthorJpa(NAME_3, SURNAME_3);
        authorJpa3.setId(ID_3);

        List<AuthorJpa> authorJpas = List.of(authorJpa, authorJpa2, authorJpa3);

        // when
        List<Author> authors = authorJpaMapper.toAuthors(authorJpas);

        // then
        Assertions.assertThat(authors).hasSize(3);
        Assertions.assertThat(authors.stream().map(Author::getId)) //
                .containsExactlyInAnyOrder(ID_3, ID_2, ID);
        Assertions.assertThat(authors.stream().map(Author::getFirstName)) //
                .containsExactlyInAnyOrder(NAME_3, NAME_2, NAME);
        Assertions.assertThat(authors.stream().map(Author::getLastName)) //
                .containsExactlyInAnyOrder(SURNAME_3, SURNAME_2, SURNAME);
        Assertions.assertThat(authors.stream().map(Author::getAudit)).noneMatch(Objects::isNull);
        Assertions.assertThat(authors.stream().map(r -> r.getAudit().getCreatedOn())).allMatch(Objects::isNull);
        Assertions.assertThat(authors.stream().map(r -> r.getAudit().getUpdatedOn())).allMatch(Objects::isNull);
    }

    private Author createAuthor(String name, String surname) {
        return Author.builder() //
                .id(ID) //
                .firstName(name) //
                .lastName(surname) //
                .version(VERSION) //
                .audit(new Audit()) //
                .build();
    }

    private AuthorJpa createAuthorJpa(String name, String surname) {
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setId(ID);
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        authorJpa.setVersion(VERSION);
        authorJpa.setAudit(new Audit());
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
}