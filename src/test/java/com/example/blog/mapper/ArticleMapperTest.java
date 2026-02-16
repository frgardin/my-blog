package com.example.blog.mapper;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.model.Article;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ArticleMapperTest {

    @Test
    void testToDTOWithValidArticle() {
        LocalDateTime now = LocalDateTime.now();
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setSlug("test-slug");
        article.setContent("Test content");
        article.setSummary("Test summary");
        article.setCreatedAt(now);
        article.setUpdatedAt(now);
        article.setPublished(true);

        ArticleDTO dto = ArticleMapper.toDTO(article);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test Title", dto.getTitle());
        assertEquals("test-slug", dto.getSlug());
        assertEquals("Test content", dto.getContent());
        assertEquals("Test summary", dto.getSummary());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertTrue(dto.isPublished());
    }

    @Test
    void testToDTOWithNullArticle() {
        ArticleDTO dto = ArticleMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    void testToDTOWithEmptyArticle() {
        Article article = new Article();
        
        ArticleDTO dto = ArticleMapper.toDTO(article);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getTitle());
        assertNull(dto.getSlug());
        assertNull(dto.getContent());
        assertNull(dto.getSummary());
        assertNotNull(dto.getCreatedAt());
        assertNotNull(dto.getUpdatedAt());
        assertTrue(dto.isPublished());
    }

    @Test
    void testToDTOWithPartialArticle() {
        Article article = new Article();
        article.setId(2L);
        article.setTitle("Partial Title");
        article.setPublished(false);

        ArticleDTO dto = ArticleMapper.toDTO(article);

        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("Partial Title", dto.getTitle());
        assertNull(dto.getSlug());
        assertNull(dto.getContent());
        assertNull(dto.getSummary());
        assertNotNull(dto.getCreatedAt());
        assertNotNull(dto.getUpdatedAt());
        assertFalse(dto.isPublished());
    }

    @Test
    void testToDTOPreservesAllFields() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now().minusHours(1);
        
        Article article = new Article();
        article.setId(999L);
        article.setTitle("Complete Article");
        article.setSlug("complete-article");
        article.setContent("Complete content with **markdown**");
        article.setSummary("Complete summary");
        article.setCreatedAt(createdAt);
        article.setUpdatedAt(updatedAt);
        article.setPublished(false);

        ArticleDTO dto = ArticleMapper.toDTO(article);

        assertEquals(999L, dto.getId());
        assertEquals("Complete Article", dto.getTitle());
        assertEquals("complete-article", dto.getSlug());
        assertEquals("Complete content with **markdown**", dto.getContent());
        assertEquals("Complete summary", dto.getSummary());
        assertEquals(createdAt, dto.getCreatedAt());
        assertEquals(updatedAt, dto.getUpdatedAt());
        assertFalse(dto.isPublished());
    }
}
