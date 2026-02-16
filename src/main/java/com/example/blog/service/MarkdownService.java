package com.example.blog.service;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
    
    private final Parser parser;
    private final HtmlRenderer renderer;
    
    public MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }
    
    public String markdownToHtml(String markdown) {
        if (markdown == null || markdown.trim().isEmpty()) {
            return "";
        }
        
        org.commonmark.node.Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
