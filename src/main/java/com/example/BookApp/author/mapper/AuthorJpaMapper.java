package com.example.BookApp.author.mapper;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorJpaMapper {

    @Mapping(target = "audit", ignore = true)
    AuthorJpa toAuthorJpa(Author author);
    Author toAuthor(AuthorJpa authorJpa);

    List<Author> toAuthors(List<AuthorJpa> authorJpas);

    List<AuthorJpa> toAuthorJpas(List<Author> authors);
}
