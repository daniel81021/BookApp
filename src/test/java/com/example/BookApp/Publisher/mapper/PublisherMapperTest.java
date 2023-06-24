package com.example.BookApp.publisher.mapper;

import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.stream.Stream;

@SpringBootTest
class PublisherMapperTest {

    private static final Long ID = 1L;
    private static final Long ID_2 = 2L;
    private static final String NAME = "Publisher";
    private static final String NAME_2 = "Publisher 2";
    private static final String NAME_3 = "Publisher 3";
    private static final Long VERSION = 1L;
    private static final Long VERSION_2 = 2L;

    @Autowired
    PublisherMapper publisherMapper;

    @ParameterizedTest
    @MethodSource("provideData")
    void toPublisherJpa(Long id, String name, Long version) {

        // given
        Publisher publisher = createPublisher(id, name, version);

        // when
        PublisherJpa result = publisherMapper.toPublisherJpa(publisher);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(PublisherJpa.class);
        Assertions.assertThat(result) //
                .usingRecursiveComparison() //
                .ignoringFields("audit") //
                .isEqualTo(publisher);
        Assertions.assertThat(result.getAudit().getCreatedOn()).isNull();
        Assertions.assertThat(result.getAudit().getUpdatedOn()).isNull();
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void toPublisher(Long id, String name, Long version) {

        // given
        PublisherJpa publisherJpa = createPublisherJpa(id, name, version);

        // when
        Publisher r = publisherMapper.toPublisher(publisherJpa);

        // then
        Assertions.assertThat(r).isNotNull();
        Assertions.assertThat(r).isInstanceOf(Publisher.class);
        Assertions.assertThat(r).usingRecursiveComparison().ignoringFields("audit").isEqualTo(publisherJpa);
    }

    private Audit createAudit() {
        Audit audit = new Audit();
        audit.setCreatedOn(LocalDate.of(1999, 1, 1));
        audit.setUpdatedOn(LocalDate.of(2000, 1, 1));
        return audit;
    }

    private Publisher createPublisher(Long id, String name, Long version) {
        return Publisher.builder() //
                .id(id) //
                .name(name) //
                .version(version) //
                .build();
    }

    private PublisherJpa createPublisherJpa(Long id, String name, Long version) {
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setId(id);
        publisherJpa.setName(name);
        publisherJpa.setVersion(version);
        publisherJpa.setAudit(createAudit());
        return publisherJpa;
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(null, NAME, null),
                Arguments.of(ID, NAME_2, VERSION),
                Arguments.of(ID_2, NAME_3, VERSION_2)
        );
    }
}