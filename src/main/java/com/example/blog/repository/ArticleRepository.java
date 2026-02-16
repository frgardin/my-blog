package com.example.blog.repository;

import com.example.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    Optional<Article> findBySlug(String slug);
    
    List<Article> findByPublishedTrueOrderByCreatedAtDesc();
    
    @Query("SELECT a FROM Article a WHERE a.published = true ORDER BY a.createdAt DESC")
    List<Article> findPublishedArticlesOrderByCreatedAtDesc();
    
    @Query("SELECT a FROM Article a WHERE a.published = true AND a.title LIKE %:keyword% OR a.summary LIKE %:keyword% ORDER BY a.createdAt DESC")
    List<Article> searchPublishedArticles(String keyword);
}
