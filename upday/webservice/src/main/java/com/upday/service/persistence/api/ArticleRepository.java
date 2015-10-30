package com.upday.service.persistence.api;

import com.upday.service.persistence.domain.Article;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage {@link Article} entities.
 * 
 * @author jschulz
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    
//    List<Article> findByAuthor(Long authorId);
//    
//    List<Article> findByKeyword(Long keywordId);
//    
//    List<Article> findByKeywordName(String keywordName);
    
}
