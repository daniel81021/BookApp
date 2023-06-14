package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.common.Audit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class AuthorJpaMapperTest {

    private final Long ID = 1L;
    private final Long VERSION = 1L;
    private static final String NAME = "Daniel";
    private static final String NAME_2 = "Emil";
    private static final String SURNAME = "Ziola";
    private static final String SURNAME_2 = "Karewicz";

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
                .ignoringFields("audit") //
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
                .ignoringFields("audit") //
                .isEqualTo(authorJpa);
    }

    private Author createAuthor(String name, String surname){
        return Author.builder() //
                .id(ID) //
                .firstName(name) //
                .lastName(surname) //
                .version(VERSION) //
                .build();
    }

    private AuthorJpa createAuthorJpa(String name, String surname){
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setId(ID);
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        authorJpa.setVersion(VERSION);
        authorJpa.setAudit(new Audit());
        return authorJpa;
    }

    private static Stream<Arguments> provideValues(){
        return Stream.of(
                Arguments.of(NAME, SURNAME),
                Arguments.of(NAME_2, SURNAME_2),
                Arguments.of(NAME, SURNAME_2),
                Arguments.of(NAME_2, SURNAME)
        );
    }
}