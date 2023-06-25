package com.example.BookApp.author.service.impl;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.mapper.AuthorJpaMapper;
import com.example.BookApp.author.repository.AuthorJpaRepository;
import com.example.BookApp.common.Audit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthorJpaServiceImplTest {

    private final Long ID = 1L;
    private final Long VERSION = 1L;
    private static final String NAME = "Daniel";
    private static final String SURNAME = "Emil";
    @InjectMocks
    private AuthorJpaServiceImpl authorJpaService;

    @Mock
    private AuthorJpaRepository authorJpaRepositoryMock;

    @Mock
    private AuthorJpaMapper authorJpaMapperMock;

    @Test
    void saveAuthor() {

        // given
        Author authorNoIdAndVersion = createAuthorNoIdAndVersion(NAME, SURNAME);
        Author author = createAuthor(NAME, SURNAME);

        AuthorJpa authorJpaNoIdAndVersion = createAuthorJpaNoIdAndVersion(NAME, SURNAME);

        Audit audit = createAudit();
        AuthorJpa savedAuthorJpa = createAuthorJpa(NAME, SURNAME);
        savedAuthorJpa.setAudit(audit);

        Mockito.when(authorJpaMapperMock.toAuthorJpa(authorNoIdAndVersion)).thenReturn(authorJpaNoIdAndVersion);
        Mockito.when(authorJpaRepositoryMock.save(authorJpaNoIdAndVersion)).thenReturn(savedAuthorJpa);
        Mockito.when(authorJpaMapperMock.toAuthor(savedAuthorJpa)).thenReturn(author);

        // when
        Author savedAuthor = authorJpaService.saveAuthor(authorNoIdAndVersion);

        // then
        Assertions.assertThat(savedAuthor).isNotNull();
        Mockito.verify(authorJpaMapperMock, times(1)).toAuthorJpa(authorNoIdAndVersion);
        Mockito.verify(authorJpaRepositoryMock, times(1)).save(authorJpaNoIdAndVersion);
        Mockito.verify(authorJpaMapperMock, times(1)).toAuthor(savedAuthorJpa);
    }

    @Test
    void shouldReturnAuthorFoundById(){

        // given
        AuthorJpa authorJpa = createAuthorJpa(NAME, SURNAME);
        Author author = createAuthor(NAME, SURNAME);
        Mockito.when(authorJpaRepositoryMock.findById(ID)).thenReturn(Optional.of(authorJpa));
        Mockito.when(authorJpaMapperMock.toAuthor(authorJpa)).thenReturn(author);

        // when
        Author result = authorJpaService.findById(ID);

        // then
        Assertions.assertThat(result.getId()).isEqualTo(ID);

        Mockito.verify(authorJpaRepositoryMock, times(1)).findById(ID);
        Mockito.verify(authorJpaMapperMock, times(1)).toAuthor(authorJpa);
    }

    @Test
    void shouldThrowExceptionWhenIdIsWrong() {

        // given
        Mockito.when(authorJpaRepositoryMock.findById(ID)).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(()-> authorJpaService.findById(ID))
                        .isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(authorJpaRepositoryMock, times(1)).findById(ID);
    }

    private Author createAuthor(String name, String surname){
        return Author.builder() //
                .id(ID) //
                .firstName(name) //
                .lastName(surname) //
                .version(VERSION) //
                .build();
    }

    private Author createAuthorNoIdAndVersion(String name, String surname){
        return Author.builder() //
                .firstName(name) //
                .lastName(surname) //
                .build();
    }

    private AuthorJpa createAuthorJpa(String name, String surname){
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setId(ID);
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        authorJpa.setVersion(VERSION);
        authorJpa.setAudit(new Audit());
        return authorJpa;
    }

    private AuthorJpa createAuthorJpaNoIdAndVersion(String name, String surname){
        AuthorJpa authorJpa = new AuthorJpa();
        authorJpa.setFirstName(name);
        authorJpa.setLastName(surname);
        return authorJpa;
    }

    private Audit createAudit(){
        Audit audit = new Audit();
        audit.setCreatedOn(LocalDate.of(1999,1,1));
        audit.setUpdatedOn(LocalDate.of(1999,1,1));
        return audit;
    }
}