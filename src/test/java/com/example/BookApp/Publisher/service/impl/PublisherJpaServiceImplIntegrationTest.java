package com.example.BookApp.publisher.service.impl;

import com.example.BookApp.common.CommonMessageConstants;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.service.PublisherJpaService;
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
class PublisherJpaServiceImplIntegrationTest {

    private static final String NAME = "Publisher";
    private static final String NAME_2 = "Publisher 2";
    private static final String TOO_LONG_STRING_32 = "32digitsIsMaxAllowedSoThisStringIsTooLong!";


    @Autowired
    PublisherJpaService publisherJpaService;
    @Test
    void save() {

        // given
        Publisher publisher = createPublisher(NAME);

        // when
        Publisher result = publisherJpaService.save(publisher);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getVersion()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenPublisherWithTheSameNameExists(){

        // given
        Publisher first = createPublisher(NAME);
        Publisher second = createPublisher(NAME_2);
        Publisher third = createPublisher(NAME);

        publisherJpaService.save(first);
        publisherJpaService.save(second);

        // when
        // then
        Assertions.assertThatThrownBy(()->publisherJpaService.save(third))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void shouldThrowExceptionWhenIncorrectNameProvided(String input, String msg){

        // given
        Publisher publisher = createPublisher(input);

        // when
        // then
        Assertions.assertThatThrownBy(()-> publisherJpaService.save(publisher)) //
                .isInstanceOf(ConstraintViolationException.class) //
                .hasMessageContaining(msg);
    }

    private Publisher createPublisher(String name){
        return Publisher.builder() //
                .name(name) //
                .build();
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("  ", CommonMessageConstants.NOT_BLANK_MSG),
                Arguments.of("A", CommonMessageConstants.VALUE_TOO_SHORT),
                Arguments.of(TOO_LONG_STRING_32, CommonMessageConstants.VALUE_TOO_LONG)
        );
    }
}