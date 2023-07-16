package com.example.BookApp.book.mapper;

import com.example.BookApp.author.mapper.AuthorJpaMapper;
import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.publisher.mapper.PublisherMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PublisherMapper.class, AuthorJpaMapper.class})
public interface BookMapper {
    @Mapping(target = "audit", ignore = true)
    BookJpa toBookJpa(Book book);

    Book toBook(BookJpa bookJpa);
}
