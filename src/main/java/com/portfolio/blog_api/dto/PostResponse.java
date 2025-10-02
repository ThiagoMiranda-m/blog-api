package com.portfolio.blog_api.dto;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, LocalDateTime createdAt, String authorUsername) {
}
