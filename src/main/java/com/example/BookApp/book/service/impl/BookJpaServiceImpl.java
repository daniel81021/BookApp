package com.example.BookApp.book.service.impl;

import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookJpa;
import com.example.BookApp.book.mapper.BookMapper;
import com.example.BookApp.book.repository.BookJpaRepository;
import com.example.BookApp.book.service.BookJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BookJpaServiceImpl implements BookJpaService {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book save(Book book) {
        BookJpa bookJpa = bookMapper.toBookJpa(book);
        BookJpa saved = bookJpaRepository.save(bookJpa);
        return bookMapper.toBook(saved);
    }

    @Override
    public Book findById(Long id) {
        Optional<BookJpa> book = bookJpaRepository.findById(id);
        if(book.isPresent()){
            return bookMapper.toBook(book.get());
        } else {
            throw new IllegalArgumentException();
        }

    }
}
