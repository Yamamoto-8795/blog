package com.blog.myblog.articles;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.myblog.articles.dto.ArticleDto;
import com.blog.myblog.common.entity.ArticleEntity;
import com.blog.myblog.common.entity.CategoryEntity;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Page<ArticleEntity> getAllArticles(Pageable pageable) {
        return articleRepository.findAllByArticleStatus(1, pageable);
    }


    public ArticleEntity getArticle(Integer id) {
        return articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Page<ArticleDto> convertToArticleDto(Page<ArticleEntity> articles) {
        Page<ArticleDto> articleDtos = articles.map(article -> {
            ArticleDto articleDto = new ArticleDto();

            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setContent(article.getContent());
            articleDto.setLikeQuantity(article.getLikeQuantity());
            articleDto.setCreatedAt(article.getCreatedAt());
            articleDto.setCategoryName(Optional.ofNullable(article.getCategory()).map(CategoryEntity::getName).orElse(null));
            articleDto.setUserName(article.getUser().getName());
            articleDto.setUserId(article.getUser().getId());
            articleDto.setTags(article.getArticleTags().stream().map(tag -> tag.getTag().getName()).collect(Collectors.toList()));
            articleDto.setCommentCount(article.getComments().size());

            return articleDto;
        });
        return articleDtos;
    }

}
