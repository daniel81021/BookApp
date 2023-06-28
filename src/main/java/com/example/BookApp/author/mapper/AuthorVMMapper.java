package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorVMMapper {

    AuthorVM toAuthorVM(Author author);

    @Mapping(target = "audit", ignore = true)
    Author toAuthor(AuthorVM authorVM);

    List<AuthorVM> toAuthorVMs(List<Author> authors);
}
