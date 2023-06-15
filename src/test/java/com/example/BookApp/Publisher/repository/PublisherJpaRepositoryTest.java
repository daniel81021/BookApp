package com.example.BookApp.Publisher.repository;

import com.example.BookApp.Publisher.domain.PublisherJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublisherJpaRepositoryTest {

    private final String NAME = "Publisher";

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

    private PublisherJpa createPublisherJpa(String name){
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setName(name);
        return publisherJpa;
    }

}