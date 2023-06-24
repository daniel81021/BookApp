package com.example.BookApp.publisher.service.impl;

import com.example.BookApp.common.Audit;
import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.mapper.PublisherMapper;
import com.example.BookApp.publisher.repository.PublisherJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class PublisherJpaServiceImplTest {

    private final String NAME = "Publisher";
    private final Long ID = 1L;
    private final Long VERSION = 1L;

    @InjectMocks
    private PublisherJpaServiceImpl publisherJpaService;

    @Mock
    private PublisherMapper publisherMapperMock;

    @Mock
    private PublisherJpaRepository publisherJpaRepositoryMock;

    @Test
    void save() {

        // given
        Publisher input = createPublisher(null, NAME, null);
        PublisherJpa afterMapping = createPublisherJpa(null, NAME, null, new Audit());
        Audit audit = createAudit(LocalDate.of(1999,1,1), LocalDate.of(2000,1,1));
        PublisherJpa afterSave = createPublisherJpa(ID, NAME, VERSION, audit);
        Publisher output = createPublisher(ID, NAME, VERSION);

        Mockito.when(publisherMapperMock.toPublisherJpa(input)).thenReturn(afterMapping);
        Mockito.when(publisherJpaRepositoryMock.save(afterMapping)).thenReturn(afterSave);
        Mockito.when(publisherMapperMock.toPublisher(afterSave)).thenReturn(output);

        // when
        Publisher result = publisherJpaService.save(input);

        // then
        Assertions.assertThat(result).isEqualTo(output);
        Mockito.verify(publisherMapperMock).toPublisherJpa(input);
        Mockito.verify(publisherJpaRepositoryMock).save(afterMapping);
        Mockito.verify(publisherMapperMock).toPublisher(afterSave);
    }

    private Publisher createPublisher(Long id, String name, Long version){
        return Publisher.builder()
                .id(id)
                .name(name)
                .version(version)
                .build();
    }

    private Audit createAudit(LocalDate createdOn, LocalDate updatedOn){
        Audit audit = new Audit();
        audit.setCreatedOn(createdOn);
        audit.setUpdatedOn(updatedOn);
        return audit;
    }

    private PublisherJpa createPublisherJpa(Long id, String name, Long version, Audit audit){
        PublisherJpa publisherJpa = new PublisherJpa();
        publisherJpa.setId(id);
        publisherJpa.setName(name);
        publisherJpa.setVersion(version);
        publisherJpa.setAudit(audit);
        return publisherJpa;
    }

}