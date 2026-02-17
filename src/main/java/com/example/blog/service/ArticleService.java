package com.example.blog.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.mapper.ArticleMapper;
import com.example.blog.model.Article;
import com.example.blog.model.Article_;
import com.example.blog.repository.ArticleRepository;

import jakarta.persistence.criteria.JoinType;

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
        model.addAttribute("allTags", getAllTags());
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

    public String search(String keyword, Set<String> tags, Model model) {
        Specification<Article> spec = Specification.where((root, query, cb) -> {
            query.distinct(true);
            root.fetch(Article_.TAGS, JoinType.LEFT);
            return cb.equal(root.get(Article_.PUBLISHED), true);
        });
        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                var tagsJoin = root.join(Article_.TAGS, JoinType.LEFT);
                return cb.or(
                        cb.like(root.get(Article_.TITLE), "%" + keyword + "%"),
                        cb.like(root.get(Article_.SUMMARY), "%" + keyword + "%"),
                        cb.like(root.get(Article_.CONTENT), "%" + keyword + "%"),
                        cb.like(tagsJoin.as(String.class), "%" + keyword + "%"));
            });
        }
        if (tags != null && !tags.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get(Article_.TAGS).in(tags));
        }
        List<ArticleDTO> searchResults = articleRepository.findAll(spec)
                .stream()
                .map(ArticleMapper::toDTO)
                .toList();
        model.addAttribute("articles", searchResults);
        model.addAttribute("searchKeyword", keyword);
        model.addAttribute("allTags", getAllTags());
        return "index";
    }

    private List<ArticleDTO> getPublishedArticlesOrderedByCreatedAtDesc() {
        return articleRepository.findPublishedArticlesOrderByCreatedAtDesc().stream().map(ArticleMapper::toDTO)
                .toList();
    }

    private List<String> getAllTags() {
        return articleRepository.findAllTags().stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
