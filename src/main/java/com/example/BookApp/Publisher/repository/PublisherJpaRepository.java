package com.example.BookApp.publisher.repository;

import com.example.BookApp.publisher.domain.PublisherJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherJpaRepository extends JpaRepository<PublisherJpa, Long> {
}
