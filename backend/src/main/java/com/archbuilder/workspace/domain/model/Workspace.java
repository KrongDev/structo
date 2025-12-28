package com.archbuilder.workspace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workspace {
    private String id;
    // owner
    private String userId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateName(String name) {
        this.name = name;
    }
}
