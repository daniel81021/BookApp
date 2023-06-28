package com.example.BookApp.author.service.impl;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorJpa;
import com.example.BookApp.author.mapper.AuthorJpaMapper;
import com.example.BookApp.author.repository.AuthorJpaRepository;
import com.example.BookApp.author.service.AuthorJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class AuthorJpaServiceImpl implements AuthorJpaService {

    @Autowired
    AuthorJpaRepository authorJpaRepository;

    @Autowired
    AuthorJpaMapper authorJpaMapper;

    @Override
    public Author saveAuthor(Author author) {
        AuthorJpa authorJpa = authorJpaMapper.toAuthorJpa(author);
        AuthorJpa savedAuthor = authorJpaRepository.save(authorJpa);
        return authorJpaMapper.toAuthor(savedAuthor);
    }

    @Override
    public Author findById(Long id) {
        return authorJpaRepository.findById(id) //
                .map(authorJpaMapper::toAuthor) //
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Author updateAuthor(Author author) {

        Author fromDb = this.findById(author.getId());

        if (Objects.equals(fromDb.getVersion(), author.getVersion())) {
            AuthorJpa toUpdate = authorJpaMapper.toAuthorJpa(author);
            AuthorJpa updated = authorJpaRepository.save(toUpdate);
            return authorJpaMapper.toAuthor(updated);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Author> findAllAuthors() {
//        List<AuthorJpa> authorJpas = authorJpaRepository.findAll(Sort.by(Sort.Order.desc("id")));
        List<AuthorJpa> authorJpas = authorJpaRepository.findAll();
        return authorJpaMapper.toAuthors(authorJpas);
    }
}
