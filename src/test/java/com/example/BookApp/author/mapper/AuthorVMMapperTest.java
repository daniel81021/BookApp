package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorVM;
import com.example.BookApp.common.Audit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class AuthorVMMapperTest {

    private final String NAME = "Daniel";
    private final String NAME_2 = "Krzysztof";
    private final String NAME_3 = "Rafal";
    private final String NAME_4 = "Robert";
    private final String SURNAME = "Surname";
    private final String SURNAME_2 = "Surname2";
    private final String SURNAME_3 = "Surname3";
    private final String SURNAME_4 = "Surname4";
    private final Long ID = 1L;
    private final Long ID_2 = 2L;
    private final Long VERSION = 1L;
    private final Long VERSION_2 = 2L;

    @Autowired
    private AuthorVMMapper authorVMMapper;

    @ParameterizedTest
    @MethodSource("provideData")
    void toAuthorVM(Long id, String name, String surname, Long version) {

        // given
        Author author = createAuthor(id, name, surname, version);

        // when
        AuthorVM authorVM = authorVMMapper.toAuthorVM(author);

        // then
        Assertions.assertThat(authorVM) //
                .usingRecursiveComparison() //
                .ignoringFields("audit") //
                .isEqualTo(author);
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void toAuthor(Long id, String name, String surname, Long version) {

        // given
        AuthorVM authorVM = createAuthorVM(id, name, surname, version);

        // when
        Author author = authorVMMapper.toAuthor(authorVM);

        // then
        Assertions.assertThat(author) //
                .usingRecursiveComparison() //
                .ignoringFields("audit") //
                .isEqualTo(authorVM);

        Assertions.assertThat(author.getAudit()).isNull();
    }

    private Author createAuthor(Long id, String name, String surname, Long version) {
        return Author.builder()
                .firstName(name)
                .lastName(surname)
                .id(id)
                .version(version)
                .audit(new Audit())
                .build();
    }

    private AuthorVM createAuthorVM(Long id, String name, String surname, Long version) {
        return AuthorVM.builder()
                .firstName(name)
                .lastName(surname)
                .id(id)
                .version(version)
                .build();
    }

    private Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(null, NAME, SURNAME, null),
                Arguments.of(null, NAME_2, SURNAME_2, null),
                Arguments.of(ID, NAME_3, SURNAME_3, VERSION),
                Arguments.of(ID_2, NAME_4, SURNAME_4, VERSION_2)
        );
    }
}