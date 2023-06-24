package com.example.BookApp.publisher.controller;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherVM;
import com.example.BookApp.publisher.mapper.PublisherVMMapper;
import com.example.BookApp.publisher.service.PublisherJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher")
public class PublisherJpaController {

    @Autowired
    PublisherJpaService publisherJpaService;

    @Autowired
    PublisherVMMapper publisherVMMapper;

    @PostMapping(value = "/add")
    PublisherVM save(@RequestBody PublisherVM publisherVM) {
        Publisher publisher = publisherVMMapper.toPublisher(publisherVM);
        return publisherVMMapper.toPublisherVM(publisherJpaService.save(publisher));
    }
}
