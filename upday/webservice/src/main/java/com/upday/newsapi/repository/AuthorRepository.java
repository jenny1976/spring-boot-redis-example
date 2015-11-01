package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Author;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Author} entities.
 * 
 * @author jschulz
 */
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    
}
