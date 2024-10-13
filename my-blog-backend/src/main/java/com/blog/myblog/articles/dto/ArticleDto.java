package com.blog.myblog.articles.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Integer id;

    private String title;

    private String content;

    private Long likeQuantity;

    private Integer articleStatus;

    private String categoryName;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    private String userName;

    private Integer userId;

    private List<String> tags;

    private Integer commentCount;
}
