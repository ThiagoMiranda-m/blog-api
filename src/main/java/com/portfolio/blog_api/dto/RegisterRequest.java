package com.portfolio.blog_api.dto;

import com.portfolio.blog_api.model.Role;

public record RegisterRequest(String username, String password, Role role) {
}
