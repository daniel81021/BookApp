package com.example.BookApp.book.controller;

import com.example.BookApp.book.domain.Book;
import com.example.BookApp.book.domain.BookVM;
import com.example.BookApp.book.mapper.BookVMMapper;
import com.example.BookApp.book.service.BookJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookJpaService bookService;

    @Autowired
    private BookVMMapper mapper;

    @PostMapping("/add")
    @ResponseBody
    BookVM saveBook(@RequestBody BookVM bookVM){
        Book book = mapper.toBook(bookVM);
        Book savedBook = bookService.save(book);
        return mapper.toBookVM(savedBook);
    }

    @GetMapping("/{id}")
    @ResponseBody
    BookVM findBookById(@PathVariable Long id){
        Book book = bookService.findById(id);
        return mapper.toBookVM(book);
    }
}
