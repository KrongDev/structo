package com.archbuilder.workspace.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access= AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private String id;
    // owner
    private String userId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Workspace of(String id, String userId, String name) {
        return Workspace.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .build();
    }

    public static Workspace of(String userId, String name) {
        return Workspace.builder()
                .userId(userId)
                .name(name)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }
}
