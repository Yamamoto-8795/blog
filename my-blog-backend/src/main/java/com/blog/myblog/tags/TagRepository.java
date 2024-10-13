package com.blog.myblog.tags;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.myblog.common.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    Optional<TagEntity> findByName(String name);
}
