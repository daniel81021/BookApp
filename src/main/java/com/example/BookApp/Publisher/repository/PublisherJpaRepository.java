package com.example.BookApp.Publisher.repository;

import com.example.BookApp.Publisher.domain.PublisherJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherJpaRepository extends JpaRepository<PublisherJpa, Long> {
}
