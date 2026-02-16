package com.example.blog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MarkdownServiceTest {

    private MarkdownService markdownService;

    @BeforeEach
    void setUp() {
        markdownService = new MarkdownService();
    }

    @Test
    void testMarkdownToHtmlWithNullInput() {
        String result = markdownService.markdownToHtml(null);
        assertEquals("", result);
    }

    @Test
    void testMarkdownToHtmlWithEmptyString() {
        String result = markdownService.markdownToHtml("");
        assertEquals("", result);
    }

    @Test
    void testMarkdownToHtmlWithWhitespaceOnly() {
        String result = markdownService.markdownToHtml("   \n\t  \n   ");
        assertEquals("", result);
    }

    @Test
    void testMarkdownToHtmlWithSimpleText() {
        String result = markdownService.markdownToHtml("Simple text");
        assertEquals("<p>Simple text</p>\n", result);
    }

    @Test
    void testMarkdownToHtmlWithHeaders() {
        String markdown = "# Header 1\n## Header 2\n### Header 3";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<h1>Header 1</h1>"));
        assertTrue(result.contains("<h2>Header 2</h2>"));
        assertTrue(result.contains("<h3>Header 3</h3>"));
    }

    @Test
    void testMarkdownToHtmlWithBoldAndItalic() {
        String markdown = "This is **bold** and this is *italic* text.";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<strong>bold</strong>"));
        assertTrue(result.contains("<em>italic</em>"));
    }

    @Test
    void testMarkdownToHtmlWithLinks() {
        String markdown = "[Link text](https://example.com)";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<a href=\"https://example.com\">Link text</a>"));
    }

    @Test
    void testMarkdownToHtmlWithCodeBlocks() {
        String markdown = "```java\npublic class Test { }\n```";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<pre><code"));
        assertTrue(result.contains("public class Test { }"));
    }

    @Test
    void testMarkdownToHtmlWithInlineCode() {
        String markdown = "This is `inline code` text.";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<code>inline code</code>"));
    }

    @Test
    void testMarkdownToHtmlWithLists() {
        String markdown = "- Item 1\n- Item 2\n- Item 3";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("<li>Item 1</li>"));
        assertTrue(result.contains("<li>Item 2</li>"));
        assertTrue(result.contains("<li>Item 3</li>"));
        assertTrue(result.contains("</ul>"));
    }

    @Test
    void testMarkdownToHtmlWithBlockquotes() {
        String markdown = "> This is a blockquote";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<blockquote>"));
        assertTrue(result.contains("This is a blockquote"));
        assertTrue(result.contains("</blockquote>"));
    }

    @Test
    void testMarkdownToHtmlWithLineBreaks() {
        String markdown = "Line 1\nLine 2\n\nLine 3";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<p>Line 1\nLine 2</p>"));
        assertTrue(result.contains("<p>Line 3</p>"));
    }

    @Test
    void testMarkdownToHtmlWithComplexDocument() {
        String markdown = "# Title\n\nThis is a paragraph with **bold** and *italic* text.\n\n## Subtitle\n\n- List item 1\n- List item 2\n\n> A quote\n\n```java\ncode example\n```";
        String result = markdownService.markdownToHtml(markdown);
        
        assertTrue(result.contains("<h1>Title</h1>"));
        assertTrue(result.contains("<strong>bold</strong>"));
        assertTrue(result.contains("<em>italic</em>"));
        assertTrue(result.contains("<h2>Subtitle</h2>"));
        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("<blockquote>"));
        assertTrue(result.contains("<pre><code"));
    }
}
