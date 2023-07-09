package com.example.BookApp.book.repository;

import com.example.BookApp.book.domain.BookJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookJpaRepository extends JpaRepository<BookJpa, Long> {
}
