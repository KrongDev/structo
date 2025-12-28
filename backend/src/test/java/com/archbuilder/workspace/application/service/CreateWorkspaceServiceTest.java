package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.application.command.CreateWorkspaceCommand;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateWorkspaceServiceTest {

    @InjectMocks
    private CreateWorkspaceService createWorkspaceService;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Test
    @DisplayName("should create a workspace successfully")
    void create() {
        // given
        String name = "Test Workspace";
        String userId = "user-123";
        CreateWorkspaceCommand command = CreateWorkspaceCommand.builder()
                .name(name)
                .userId(userId)
                .build();

        Workspace savedWorkspace = Workspace.builder()
                .id("ws-123")
                .name(name)
                .userId(userId)
                .build();

        given(workspaceRepository.save(any(Workspace.class))).willReturn(savedWorkspace);

        // when
        Workspace result = createWorkspaceService.create(command);

        // then
        assertThat(result.getId()).isEqualTo("ws-123");
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(workspaceRepository).save(any(Workspace.class));
    }
}
