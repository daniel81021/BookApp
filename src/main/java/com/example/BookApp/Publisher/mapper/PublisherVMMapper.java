package com.example.BookApp.publisher.mapper;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherVMMapper {

    PublisherVM toPublisherVM(Publisher publisher);

    @Mapping(target = "audit", ignore = true)
    Publisher toPublisher(PublisherVM publisherVM);
}
