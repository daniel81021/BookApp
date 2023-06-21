package com.example.BookApp.Publisher.repository;

import com.example.BookApp.author.constants.AuthorMessageConstants;
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
import java.util.stream.Stream;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublisherJpaRepositoryTest {

    private static final String NAME = "Publisher";
    private static final String NAME_2 = "Publisher 2";
    private static final String TOO_LONG_STRING_32 = "32digitsIsMaxAllowedSoThisStringIsTooLong!";



    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @ParameterizedTest
    @MethodSource("provideNames")
    void save(String input, String result) {

        // given
        PublisherJpa publisherJpa = createPublisherJpa(input);

        // when
        PublisherJpa saved = publisherJpaRepository.save(publisherJpa);

        // then
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getVersion()).isNotNull();
        Assertions.assertThat(saved.getAudit().getCreatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getUpdatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getUpdatedOn()).isEqualTo(saved.getAudit().getCreatedOn());
        Assertions.assertThat(saved.getName()).isEqualTo(result);
    }

    @Test
    void shouldThrowExceptionWhenPublisherExists() {

        // given
        PublisherJpa publisherJpa = createPublisherJpa(NAME);

        publisherJpaRepository.save(publisherJpa);

        PublisherJpa publisherJpa2 = createPublisherJpa(NAME);

        // when
        // then
        Assertions.assertThatThrownBy(() -> publisherJpaRepository.save(publisherJpa2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void shouldThrowExceptionWhenNameIsIncorrect(String input, String msg){

        // given
        PublisherJpa publisherJpa = createPublisherJpa(input);

        // when
        // then
        Assertions.assertThatThrownBy(()-> publisherJpaRepository.save(publisherJpa))//
                .isInstanceOf(ConstraintViolationException.class) //
                .hasMessageContaining(msg);
    }

    private PublisherJpa createPublisherJpa(String name) {
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setName(name);
        return publisherJpa;
    }

    private static Stream<Arguments> provideNames() {
        return Stream.of(
                Arguments.of(NAME, NAME),
                Arguments.of(NAME_2, NAME_2)
        );
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("", AuthorMessageConstants.NOT_BLANK_MSG),
                Arguments.of("  ", AuthorMessageConstants.NOT_BLANK_MSG),
                Arguments.of("A", CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TOO_LONG_STRING_32, CommonMessageConstants.VALUE_TOO_LONG)
        );
    }
}