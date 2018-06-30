package com.summer.demo.Repository;

import com.summer.demo.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    public Author findByAuthorId(int authorId);
}
