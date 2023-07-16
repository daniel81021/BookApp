package com.example.BookApp.book.mapper;

import com.example.BookApp.author.mapper.AuthorVMMapper;
import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookVM;
import com.example.BookApp.publisher.mapper.PublisherVMMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PublisherVMMapper.class, AuthorVMMapper.class})
public interface BookVMMapper {

    @Mapping(target = "audit", ignore = true)
    Book toBook(BookVM bookVM);

    BookVM toBookVM(Book book);
}
