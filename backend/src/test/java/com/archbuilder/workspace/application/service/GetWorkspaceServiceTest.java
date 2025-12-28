package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.domain.exception.WorkspaceNotFoundException;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetWorkspaceServiceTest {

    @InjectMocks
    private GetWorkspaceService getWorkspaceService;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Test
    @DisplayName("should find workspace by id and user id")
    void findById() {
        // given
        String workspaceId = "ws-1";
        String userId = "user-1";

        Workspace workspace = Workspace.builder()
                .id(workspaceId)
                .userId(userId)
                .build();

        given(workspaceRepository.findById(workspaceId)).willReturn(Optional.of(workspace));

        // when
        Workspace result = getWorkspaceService.findById(userId, workspaceId);

        // then
        assertThat(result.getId()).isEqualTo(workspaceId);
    }

    @Test
    @DisplayName("should throw exception when workspace not found")
    void findById_notFound() {
        // given
        given(workspaceRepository.findById("ws-none")).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> getWorkspaceService.findById("user-1", "ws-none"))
                .isInstanceOf(WorkspaceNotFoundException.class);
    }

    @Test
    @DisplayName("should create empty list when finding all by user id if none exist")
    void findAllByUserId_empty() {
        // given
        String userId = "user-1";
        given(workspaceRepository.findByUserId(userId)).willReturn(Collections.emptyList());

        // when
        List<Workspace> result = getWorkspaceService.findAllByUserId(userId);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return list of workspaces")
    void findAllByUserId() {
        // given
        String userId = "user-1";
        Workspace w1 = Workspace.builder().id("1").userId(userId).build();
        Workspace w2 = Workspace.builder().id("2").userId(userId).build();

        given(workspaceRepository.findByUserId(userId)).willReturn(List.of(w1, w2));

        // when
        List<Workspace> result = getWorkspaceService.findAllByUserId(userId);

        // then
        assertThat(result).hasSize(2);
    }
}
