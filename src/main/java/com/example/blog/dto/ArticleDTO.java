package com.example.blog.dto;

import java.time.LocalDateTime;

public class ArticleDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean published;

    private ArticleDTO(
        Long id,
        String title,
        String slug,
        String content,
        String summary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean published
    ) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.summary = summary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.published = published;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String slug;
        private String content;
        private String summary;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean published;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder published(boolean published) {
            this.published = published;
            return this;
        }

        public ArticleDTO build() {
            return new ArticleDTO(
                id,
                title,
                slug,
                content,
                summary,
                createdAt,
                updatedAt,
                published
            );
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isPublished() {
        return published;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}