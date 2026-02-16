package com.example.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MarkdownService markdownService;

    public ArticleService(ArticleRepository articleRepository, MarkdownService markdownService) {
        this.articleRepository = articleRepository;
        this.markdownService = markdownService;
    }

    public String renderHome(Model model) {
        model.addAttribute("articles", getPublishedArticlesOrderedByCreatedAtDesc());
        return "index";
    }

    public String article(String slug, Model model) {
        var article = articleRepository.findBySlug(slug)
                            .map(ArticleMapper::toDTO)
                            .orElse(null);
        if (article == null || !article.isPublished()) {
            return "redirect:/";
        }
        
        String htmlContent = markdownService.markdownToHtml(article.getContent());
        article.setContent(htmlContent);
        
        model.addAttribute("article", article);
        return "article";
    }

    private List<ArticleDTO> getPublishedArticlesOrderedByCreatedAtDesc() {
        return articleRepository.findPublishedArticlesOrderByCreatedAtDesc().stream().map(ArticleMapper::toDTO).toList();
    }
}
