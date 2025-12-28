package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.application.command.UpdateWorkspaceCommand;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateWorkspaceServiceTest {

    @InjectMocks
    private UpdateWorkspaceService updateWorkspaceService;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Test
    @DisplayName("should update workspace name successfully")
    void update() {
        // given
        String workspaceId = "ws-123";
        String userId = "user-123";
        String newName = "New Name";

        UpdateWorkspaceCommand command = new UpdateWorkspaceCommand(userId, workspaceId, newName);

        Workspace existingWorkspace = Workspace.builder()
                .id(workspaceId)
                .userId(userId)
                .name("Old Name")
                .build();

        given(workspaceRepository.findById(workspaceId)).willReturn(Optional.of(existingWorkspace));
        given(workspaceRepository.save(any(Workspace.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        Workspace result = updateWorkspaceService.update(command);

        // then
        assertThat(result.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("should throw exception when workspace not found")
    void update_notFound() {
        // given
        UpdateWorkspaceCommand command = new UpdateWorkspaceCommand("user-1", "ws-none", "name");
        given(workspaceRepository.findById("ws-none")).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> updateWorkspaceService.update(command))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw exception when user does not own workspace")
    void update_notOwner() {
        // given
        String workspaceId = "ws-123";
        String userId = "user-123";
        String otherUser = "other-user";

        UpdateWorkspaceCommand command = new UpdateWorkspaceCommand(otherUser, workspaceId, "New Name");

        Workspace existingWorkspace = Workspace.builder()
                .id(workspaceId)
                .userId(userId)
                .name("Old Name")
                .build();

        given(workspaceRepository.findById(workspaceId)).willReturn(Optional.of(existingWorkspace));

        // when & then
        assertThatThrownBy(() -> updateWorkspaceService.update(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Permission denied");
    }
}
