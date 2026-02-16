package com.example.blog.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ArticleDTOTest {

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        
        ArticleDTO article = ArticleDTO.builder()
                .id(1L)
                .title("Test Title")
                .slug("test-slug")
                .content("Test content")
                .summary("Test summary")
                .createdAt(now)
                .updatedAt(now)
                .published(true)
                .build();

        assertEquals(1L, article.getId());
        assertEquals("Test Title", article.getTitle());
        assertEquals("test-slug", article.getSlug());
        assertEquals("Test content", article.getContent());
        assertEquals("Test summary", article.getSummary());
        assertEquals(now, article.getCreatedAt());
        assertEquals(now, article.getUpdatedAt());
        assertTrue(article.isPublished());
    }

    @Test
    void testBuilderWithNullValues() {
        ArticleDTO article = ArticleDTO.builder()
                .id(null)
                .title(null)
                .slug(null)
                .content(null)
                .summary(null)
                .createdAt(null)
                .updatedAt(null)
                .published(false)
                .build();

        assertNull(article.getId());
        assertNull(article.getTitle());
        assertNull(article.getSlug());
        assertNull(article.getContent());
        assertNull(article.getSummary());
        assertNull(article.getCreatedAt());
        assertNull(article.getUpdatedAt());
        assertFalse(article.isPublished());
    }

    @Test
    void testSettersAndGetters() {
        ArticleDTO article = ArticleDTO.builder().build();
        LocalDateTime now = LocalDateTime.now();

        article.setId(2L);
        article.setTitle("Another Title");
        article.setSlug("another-slug");
        article.setContent("Another content");
        article.setSummary("Another summary");
        article.setCreatedAt(now);
        article.setUpdatedAt(now);
        article.setPublished(false);

        assertEquals(2L, article.getId());
        assertEquals("Another Title", article.getTitle());
        assertEquals("another-slug", article.getSlug());
        assertEquals("Another content", article.getContent());
        assertEquals("Another summary", article.getSummary());
        assertEquals(now, article.getCreatedAt());
        assertEquals(now, article.getUpdatedAt());
        assertFalse(article.isPublished());
    }

    @Test
    void testBuilderChaining() {
        ArticleDTO article = ArticleDTO.builder()
                .id(3L)
                .title("Chained Title")
                .slug("chained-slug")
                .build();

        assertEquals(3L, article.getId());
        assertEquals("Chained Title", article.getTitle());
        assertEquals("chained-slug", article.getSlug());
        assertNull(article.getContent());
        assertNull(article.getSummary());
        assertNull(article.getCreatedAt());
        assertNull(article.getUpdatedAt());
        assertFalse(article.isPublished());
    }
}
