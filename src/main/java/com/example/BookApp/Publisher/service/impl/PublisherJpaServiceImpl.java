package com.example.BookApp.publisher.service.impl;

import com.example.BookApp.publisher.domain.Publisher;
import com.example.BookApp.publisher.domain.PublisherJpa;
import com.example.BookApp.publisher.mapper.PublisherMapper;
import com.example.BookApp.publisher.repository.PublisherJpaRepository;
import com.example.BookApp.publisher.service.PublisherJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PublisherJpaServiceImpl implements PublisherJpaService {

    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @Autowired
    private PublisherMapper publisherMapper;

    @Override
    public Publisher save(Publisher publisher) {
        PublisherJpa publisherJpa = publisherMapper.toPublisherJpa(publisher);
        PublisherJpa saved = publisherJpaRepository.save(publisherJpa);
        return publisherMapper.toPublisher(saved);
    }



}
