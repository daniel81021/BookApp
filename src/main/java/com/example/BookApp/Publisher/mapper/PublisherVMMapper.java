package com.example.BookApp.publisher.mapper;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherVMMapper {

    PublisherVM toPublisherVM(Publisher publisher);

    Publisher toPublisher(PublisherVM publisherVM);
}
