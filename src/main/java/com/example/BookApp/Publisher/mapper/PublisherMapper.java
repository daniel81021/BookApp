package com.example.BookApp.publisher.mapper;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.domain.PublisherVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(target = "audit", ignore = true)
    PublisherJpa toPublisherJpa(Publisher publisher);

    Publisher toPublisher(PublisherJpa publisherJpa);

}
