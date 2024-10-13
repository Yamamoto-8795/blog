package com.blog.myblog.articles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.myblog.articles.dto.ArticleDto;
import com.blog.myblog.common.entity.ArticleEntity;


@CrossOrigin(origins = "${cors.allowed-origins}")
@RestController
public class ArticleController {
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/api/articles")
    public ResponseEntity<Page<ArticleDto>> showArticleList(@PageableDefault(size = 10) Pageable pageable) {
        Page<ArticleEntity> articles = articleService.getAllArticles(pageable);
        return ResponseEntity.ok(articleService.convertToArticleDto(articles));
    }
}
