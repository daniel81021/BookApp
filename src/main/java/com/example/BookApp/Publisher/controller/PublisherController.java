package com.example.BookApp.publisher.controller;

import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.repository.PublisherJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @PostMapping("/add")
    @ResponseBody
    PublisherJpa save(@RequestBody PublisherJpa publisherJpa){
        return publisherJpaRepository.save(publisherJpa);
    }
}
