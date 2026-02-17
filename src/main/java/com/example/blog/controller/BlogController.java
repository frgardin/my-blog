package com.example.blog.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.blog.service.ArticleService;

@Controller
public class BlogController {
    
    private final ArticleService articleService;
    
    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        return articleService.renderHome(model);
    }
    
    @GetMapping("/article/{slug}")
    public String article(@PathVariable String slug, Model model) {
        return articleService.article(slug, model);
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
    
    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Set<String> tags,
            Model model) {
        return articleService.search(q, tags, model);
    }
}
