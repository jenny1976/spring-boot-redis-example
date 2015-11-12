package com.upday.newsapi.repository;

import com.upday.newsapi.repository.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Keyword} entities.
 *
 * @author jschulz
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {


}
