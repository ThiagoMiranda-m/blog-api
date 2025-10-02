package com.portfolio.blog_api.dto;

import java.time.LocalDateTime;

public record CommentResponse(Long id, String content, LocalDateTime createdAt, String authorUsername) {
}
