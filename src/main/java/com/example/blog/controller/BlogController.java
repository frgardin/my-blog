package com.example.blog.controller;

import com.example.blog.model.Article;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.service.MarkdownService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BlogController {
    
    private final ArticleRepository articleRepository;
    private final MarkdownService markdownService;
    
    public BlogController(ArticleRepository articleRepository, MarkdownService markdownService) {
        this.articleRepository = articleRepository;
        this.markdownService = markdownService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleRepository.findPublishedArticlesOrderByCreatedAtDesc();
        model.addAttribute("articles", articles);
        return "index";
    }
    
    @GetMapping("/article/{slug}")
    public String article(@PathVariable String slug, Model model) {
        Article article = articleRepository.findBySlug(slug).orElse(null);
        if (article == null || !article.isPublished()) {
            return "redirect:/";
        }
        
        String htmlContent = markdownService.markdownToHtml(article.getContent());
        article.setContent(htmlContent);
        
        model.addAttribute("article", article);
        return "article";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
}
