package com.example.BookApp.author.repository;

import com.example.BookApp.author.domain.AuthorJpa;
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
import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorJpaRepositoryTest {

    /**
     * Constant {@link String} with correct firstName;
     */
    private final String NAME = "Daniel";

    /**
     * Constant {@link String} with correct lastName;
     */
    private final String SURNAME = "Ziola";

    /**
     * Constant {@link String} with too long String for first- or lastName (more than 32 digits);
     */
    private static final String TOO_LONG_STRING_32 = "32digitsIsMaxAllowedSoThisStringIsTooLong!";

    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @Test
    void saveAuthor(){

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);
        authorJpa.setId(null);
        authorJpa.setVersion(null);

        // when
        AuthorJpa saved = this.authorJpaRepository.save(authorJpa);

        // then
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getFirstName()).isEqualTo(NAME);
        Assertions.assertThat(saved.getLastName()).isEqualTo(SURNAME);
        Assertions.assertThat(saved.getVersion()).isNotNull();
        Assertions.assertThat(saved.getAudit()).isNotNull();
        Assertions.assertThat(saved.getAudit().getCreatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getCreatedOn()).isEqualTo(LocalDate.now());
        Assertions.assertThat(saved.getAudit().getUpdatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getCreatedOn()).isEqualTo(saved.getAudit().getUpdatedOn());
    }

    @Test
    void shouldSaveAuthorWithTheSameNameButDifferentSurnameAsFirst(){

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);

        AuthorJpa authorJpa2 = createAuthor(NAME, SURNAME+"2");

        // when
        AuthorJpa saved = this.authorJpaRepository.save(authorJpa);
        AuthorJpa saved2 = this.authorJpaRepository.save(authorJpa2);

        // then
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getFirstName()).isEqualTo(NAME);
        Assertions.assertThat(saved.getLastName()).isEqualTo(SURNAME);
        Assertions.assertThat(saved2.getId()).isNotNull();
        Assertions.assertThat(saved2.getFirstName()).isEqualTo(NAME);
        Assertions.assertThat(saved2.getLastName()).isEqualTo(SURNAME+"2");

    }

    @Test
    void shouldThrowExceptionWhenTheSameAuthorIsSaved(){

        // given
        AuthorJpa authorJpa = createAuthor(NAME, SURNAME);

        AuthorJpa authorJpa2 = createAuthor(NAME, SURNAME);

        // when
        // then
        AuthorJpa saved = this.authorJpaRepository.save(authorJpa);

        Assertions.assertThat(saved.getId()).isNotNull();

        Assertions.assertThatThrownBy(()-> this.authorJpaRepository.save(authorJpa2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void shouldThrowExceptionWhenNameTooShortOrBlankOrTooLong(String input, String msg){

        // given
        AuthorJpa authorJpa = createAuthor(input, SURNAME);

        // when
        // then
        Assertions.assertThatThrownBy(()-> this.authorJpaRepository.save(authorJpa))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(msg);

    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void shouldThrowExceptionWhenSurnameTooShortOrBlankOrTooLong(String input, String msg){

        // given
        AuthorJpa authorJpa = createAuthor(NAME, input);

        // when
        // then
        Assertions.assertThatThrownBy(()-> this.authorJpaRepository.save(authorJpa))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(msg);

    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("  ", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("A", CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TOO_LONG_STRING_32, CommonMessageConstants.VALUE_TOO_LONG)
        );
    }

    /**
     * This method creates simple {@link AuthorJpa} object with first- and lastName;
     * @param firstName {@link String} name;
     * @param lastName {@link String} surname;
     * @return {@link AuthorJpa} object with first- and lastName;
     * Both provided params can not be blank, null, empty (ex. ""), longer than 32 and shorter than 2 digits.
     */
    private AuthorJpa createAuthor(String firstName, String lastName){
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setFirstName(firstName);
        authorJpa.setLastName(lastName);
        return authorJpa;
    }
}