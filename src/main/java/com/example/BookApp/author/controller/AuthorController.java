package com.example.BookApp.author.controller;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.domain.AuthorVM;
import com.example.BookApp.author.mapper.AuthorVMMapper;
import com.example.BookApp.author.service.AuthorJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorJpaService authorJpaService;

    @Autowired
    private AuthorVMMapper authorVMMapper;

    @PostMapping("/add")
    @ResponseBody
    AuthorVM save(@RequestBody AuthorVM authorVM) {
        Author author = authorVMMapper.toAuthor(authorVM);
        Author saved = authorJpaService.saveAuthor(author);
        return authorVMMapper.toAuthorVM(saved);
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    AuthorVM findById(@PathVariable Long id) { //
        return authorVMMapper.toAuthorVM(authorJpaService.findById(id));
    }

    @PatchMapping("/update")
    @ResponseBody
    AuthorVM updateAuthor(@RequestBody AuthorVM authorVM){
        Author author = authorVMMapper.toAuthor(authorVM);
        Author updatedAuthor = authorJpaService.updateAuthor(author);
        return authorVMMapper.toAuthorVM(updatedAuthor);
    }

    @GetMapping("/findAll")
    @ResponseBody
    List<AuthorVM> findAll(){
        List<Author> authors = authorJpaService.findAllAuthors();
        return authorVMMapper.toAuthorVMs(authors);
    }
}
