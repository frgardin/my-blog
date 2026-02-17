package com.example.blog.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.blog.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    
    Optional<Article> findBySlug(String slug);
    
    List<Article> findByPublishedTrueOrderByCreatedAtDesc();
    
    @Query("SELECT a FROM Article a WHERE a.published = true ORDER BY a.createdAt DESC")
    List<Article> findPublishedArticlesOrderByCreatedAtDesc();
    
    @Query("SELECT a FROM Article a WHERE a.published = true AND (a.title LIKE %:keyword% OR a.summary LIKE %:keyword%) ORDER BY a.createdAt DESC")
    List<Article> searchPublishedArticles(@Param("keyword") String keyword);
    
    @Query("SELECT a FROM Article a WHERE a.published = true AND (a.title LIKE %:keyword% OR a.summary LIKE %:keyword% OR EXISTS (SELECT 1 FROM a.tags t WHERE t LIKE %:keyword%)) ORDER BY a.createdAt DESC")
    List<Article> searchPublishedArticlesWithTags(@Param("keyword") String keyword);
    
    @Query("SELECT DISTINCT a FROM Article a JOIN a.tags t WHERE a.published = true AND t IN :tags ORDER BY a.createdAt DESC")
    List<Article> findPublishedArticlesByTags(@Param("tags") Set<String> tags);
    
    @Query("SELECT DISTINCT t FROM Article a JOIN a.tags t WHERE a.published = true")
    List<String> findAllTags();
}
