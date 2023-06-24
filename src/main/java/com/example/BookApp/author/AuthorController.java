package com.example.BookApp.author;

import com.example.BookApp.author.domain.Author;
import com.example.BookApp.author.service.AuthorJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorJpaService authorJpaService;

    @PostMapping("/add")
    @ResponseBody
    Author save(@RequestBody Author author){
        return authorJpaService.saveAuthor(author);
    }
}
