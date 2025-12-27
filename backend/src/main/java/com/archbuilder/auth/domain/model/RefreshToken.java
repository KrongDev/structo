package com.archbuilder.auth.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RefreshToken {
    private String token;
    private String userId;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;

    public static RefreshToken of(String token, String userId, LocalDateTime expiryDate, LocalDateTime createdAt) {
        return RefreshToken.builder()
                .token(token)
                .userId(userId)
                .expiryDate(expiryDate)
                .createdAt(createdAt)
                .build();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}