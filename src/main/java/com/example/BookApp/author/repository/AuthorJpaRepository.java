package com.example.BookApp.author.repository;

import com.example.BookApp.author.domain.AuthorJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends JpaRepository<AuthorJpa, Long> {
}
