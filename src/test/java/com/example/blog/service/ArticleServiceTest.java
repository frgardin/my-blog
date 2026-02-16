package com.example.blog.service;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.model.Article;
import com.example.blog.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;
    
    @Mock
    private MarkdownService markdownService;
    
    @Mock
    private Model model;
    
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articleService = new ArticleService(articleRepository, markdownService);
    }

    @Test
    void testRenderHome() {
        LocalDateTime now = LocalDateTime.now();
        Article article1 = createTestArticle(1L, "Title 1", "slug-1", now);
        Article article2 = createTestArticle(2L, "Title 2", "slug-2", now.minusHours(1));
        
        List<Article> articles = Arrays.asList(article1, article2);
        when(articleRepository.findPublishedArticlesOrderByCreatedAtDesc()).thenReturn(articles);

        String result = articleService.renderHome(model);

        assertEquals("index", result);
        verify(model).addAttribute(eq("articles"), any(List.class));
        verify(articleRepository).findPublishedArticlesOrderByCreatedAtDesc();
    }

    @Test
    void testArticleWithExistingPublishedArticle() {
        String slug = "test-slug";
        LocalDateTime now = LocalDateTime.now();
        Article article = createTestArticle(1L, "Test Title", slug, now);
        
        when(articleRepository.findBySlug(slug)).thenReturn(Optional.of(article));
        when(markdownService.markdownToHtml(article.getContent())).thenReturn("<p>Test content</p>\n");

        String result = articleService.article(slug, model);

        assertEquals("article", result);
        verify(model).addAttribute(eq("article"), any(ArticleDTO.class));
        verify(markdownService).markdownToHtml(article.getContent());
    }

    @Test
    void testArticleWithNonExistentArticle() {
        String slug = "non-existent-slug";
        
        when(articleRepository.findBySlug(slug)).thenReturn(Optional.empty());

        String result = articleService.article(slug, model);

        assertEquals("redirect:/", result);
        verify(model, never()).addAttribute(anyString(), any());
        verify(markdownService, never()).markdownToHtml(anyString());
    }

    @Test
    void testArticleWithUnpublishedArticle() {
        String slug = "unpublished-slug";
        LocalDateTime now = LocalDateTime.now();
        Article article = createTestArticle(1L, "Unpublished Title", slug, now);
        article.setPublished(false);
        
        when(articleRepository.findBySlug(slug)).thenReturn(Optional.of(article));

        String result = articleService.article(slug, model);

        assertEquals("redirect:/", result);
        verify(model, never()).addAttribute(anyString(), any());
        verify(markdownService, never()).markdownToHtml(anyString());
    }

    @Test
    void testArticleWithNullContent() {
        String slug = "test-slug";
        LocalDateTime now = LocalDateTime.now();
        Article article = createTestArticle(1L, "Test Title", slug, now);
        article.setContent(null);
        
        when(articleRepository.findBySlug(slug)).thenReturn(Optional.of(article));
        when(markdownService.markdownToHtml(null)).thenReturn("");

        String result = articleService.article(slug, model);

        assertEquals("article", result);
        verify(model).addAttribute(eq("article"), any(ArticleDTO.class));
        verify(markdownService).markdownToHtml(null);
    }

    @Test
    void testArticleWithEmptyContent() {
        String slug = "test-slug";
        LocalDateTime now = LocalDateTime.now();
        Article article = createTestArticle(1L, "Test Title", slug, now);
        article.setContent("");
        
        when(articleRepository.findBySlug(slug)).thenReturn(Optional.of(article));
        when(markdownService.markdownToHtml("")).thenReturn("");

        String result = articleService.article(slug, model);

        assertEquals("article", result);
        verify(model).addAttribute(eq("article"), any(ArticleDTO.class));
        verify(markdownService).markdownToHtml("");
    }

    @Test
    void testRenderHomeWithEmptyList() {
        when(articleRepository.findPublishedArticlesOrderByCreatedAtDesc()).thenReturn(Arrays.asList());

        String result = articleService.renderHome(model);

        assertEquals("index", result);
        verify(model).addAttribute("articles", Arrays.asList());
        verify(articleRepository).findPublishedArticlesOrderByCreatedAtDesc();
    }

    private Article createTestArticle(Long id, String title, String slug, LocalDateTime createdAt) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setSlug(slug);
        article.setContent("Test content for " + title);
        article.setSummary("Test summary for " + title);
        article.setCreatedAt(createdAt);
        article.setUpdatedAt(createdAt);
        article.setPublished(true);
        return article;
    }
}
