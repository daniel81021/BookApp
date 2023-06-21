package com.example.BookApp.publisher.repository;

import com.example.BookApp.publisher.domain.PublisherJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublisherJpaRepositoryTest {

    private final String NAME = "Publisher";
    private final String NAME_SMALL = "publisher";



    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @Test
    void save(){

        // given
        PublisherJpa publisherJpa = createPublisherJpa(NAME);

        // when
        PublisherJpa saved = publisherJpaRepository.save(publisherJpa);

        // then
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getVersion()).isNotNull();
        Assertions.assertThat(saved.getAudit().getCreatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getUpdatedOn()).isNotNull();
        Assertions.assertThat(saved.getAudit().getUpdatedOn()).isEqualTo(saved.getAudit().getCreatedOn());
        Assertions.assertThat(saved.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldThrowExceptionWhenPublisherExists(){

        // given
        PublisherJpa publisherJpa = createPublisherJpa(NAME);

        PublisherJpa saved = publisherJpaRepository.save(publisherJpa);

        PublisherJpa publisherJpa2 = createPublisherJpa(NAME);

        // when
        // then
        Assertions.assertThatThrownBy(()-> publisherJpaRepository.save(publisherJpa2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldThrowExceptionWhenPublisherExistsButWithCapitalLetter(){


        // given
        PublisherJpa publisherJpa = createPublisherJpa(NAME);

        PublisherJpa saved = publisherJpaRepository.save(publisherJpa);

        PublisherJpa publisherJpa2 = createPublisherJpa(NAME_SMALL);

        // when
        // then
        Assertions.assertThatThrownBy(()-> publisherJpaRepository.save(publisherJpa2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
    private PublisherJpa createPublisherJpa(String name){
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setName(name);
        return publisherJpa;
    }

}