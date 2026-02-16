package com.example.blog.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ArticleTest {

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(article.getCreatedAt());
        assertNotNull(article.getUpdatedAt());
        assertTrue(article.isPublished());
    }

    @Test
    void testSetAndGetId() {
        Long expectedId = 1L;
        article.setId(expectedId);
        assertEquals(expectedId, article.getId());
    }

    @Test
    void testSetAndGetTitle() {
        String expectedTitle = "Test Title";
        article.setTitle(expectedTitle);
        assertEquals(expectedTitle, article.getTitle());
    }

    @Test
    void testSetAndGetSlug() {
        String expectedSlug = "test-slug";
        article.setSlug(expectedSlug);
        assertEquals(expectedSlug, article.getSlug());
    }

    @Test
    void testSetAndGetContent() {
        String expectedContent = "Test content";
        LocalDateTime beforeUpdate = LocalDateTime.now();
        
        article.setContent(expectedContent);
        
        assertEquals(expectedContent, article.getContent());
        assertTrue(article.getUpdatedAt().isAfter(beforeUpdate) || article.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    void testSetAndGetSummary() {
        String expectedSummary = "Test summary";
        article.setSummary(expectedSummary);
        assertEquals(expectedSummary, article.getSummary());
    }

    @Test
    void testSetAndGetCreatedAt() {
        LocalDateTime expectedCreatedAt = LocalDateTime.now().minusDays(1);
        article.setCreatedAt(expectedCreatedAt);
        assertEquals(expectedCreatedAt, article.getCreatedAt());
    }

    @Test
    void testSetAndGetUpdatedAt() {
        LocalDateTime expectedUpdatedAt = LocalDateTime.now().minusHours(1);
        article.setUpdatedAt(expectedUpdatedAt);
        assertEquals(expectedUpdatedAt, article.getUpdatedAt());
    }

    @Test
    void testSetAndGetPublished() {
        article.setPublished(false);
        assertFalse(article.isPublished());
        
        article.setPublished(true);
        assertTrue(article.isPublished());
    }

    @Test
    void testContentUpdateUpdatesTimestamp() {
        LocalDateTime initialUpdatedAt = article.getUpdatedAt();
        
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        article.setContent("New content");
        assertTrue(article.getUpdatedAt().isAfter(initialUpdatedAt));
    }
}
