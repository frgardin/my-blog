package com.example.blog.mapper;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.model.Article;

public interface ArticleMapper {
    
    static ArticleDTO toDTO(Article article) {
        if (article == null) {
            return null;
        }
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .slug(article.getSlug())
                .content(article.getContent())
                .summary(article.getSummary())
                .published(article.isPublished())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
}
