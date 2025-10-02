package com.portfolio.blog_api.repository;

import com.portfolio.blog_api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
