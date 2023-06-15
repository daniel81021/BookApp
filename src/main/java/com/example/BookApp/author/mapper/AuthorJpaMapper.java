package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorJpaMapper {

    @Mapping(target = "audit", ignore = true)
    AuthorJpa toAuthorJpa(Author author);
    Author toAuthor(AuthorJpa authorJpa);
}
