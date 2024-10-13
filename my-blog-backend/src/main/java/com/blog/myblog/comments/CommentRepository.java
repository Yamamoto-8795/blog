package com.blog.myblog.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.myblog.common.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
