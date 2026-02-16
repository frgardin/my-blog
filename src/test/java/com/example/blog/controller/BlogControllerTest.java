package com.example.blog.controller;

import com.example.blog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogControllerTest {

    @Mock
    private ArticleService articleService;
    
    @Mock
    private Model model;
    
    private BlogController blogController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blogController = new BlogController(articleService);
    }

    @Test
    void testHome() {
        String expectedView = "index";
        when(articleService.renderHome(model)).thenReturn(expectedView);

        String result = blogController.home(model);

        assertEquals(expectedView, result);
        verify(articleService).renderHome(model);
    }

    @Test
    void testArticle() {
        String slug = "test-slug";
        String expectedView = "article";
        when(articleService.article(slug, model)).thenReturn(expectedView);

        String result = blogController.article(slug, model);

        assertEquals(expectedView, result);
        verify(articleService).article(slug, model);
    }

    @Test
    void testAbout() {
        String result = blogController.about(model);

        assertEquals("about", result);
        verifyNoInteractions(articleService);
    }

    @Test
    void testArticleWithDifferentSlugs() {
        String[] slugs = {"hello-world", "spring-boot-tutorial", "java-best-practices"};
        String expectedView = "article";
        
        for (String slug : slugs) {
            when(articleService.article(slug, model)).thenReturn(expectedView);

            String result = blogController.article(slug, model);

            assertEquals(expectedView, result);
            verify(articleService).article(slug, model);
        }
    }

    @Test
    void testArticleWithRedirect() {
        String slug = "non-existent-article";
        String expectedView = "redirect:/";
        when(articleService.article(slug, model)).thenReturn(expectedView);

        String result = blogController.article(slug, model);

        assertEquals(expectedView, result);
        verify(articleService).article(slug, model);
    }

    @Test
    void testHomeWithModelInteraction() {
        String expectedView = "index";
        when(articleService.renderHome(model)).thenReturn(expectedView);

        String result = blogController.home(model);

        assertEquals(expectedView, result);
        verify(articleService).renderHome(model);
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    void testAboutWithModelInteraction() {
        String result = blogController.about(model);

        assertEquals("about", result);
        verify(model, never()).addAttribute(anyString(), any());
        verifyNoInteractions(articleService);
    }
}
