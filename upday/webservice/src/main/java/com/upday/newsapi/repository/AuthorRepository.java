package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Author} entities.
 * 
 * @author jschulz
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    
}
