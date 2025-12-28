package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.application.command.CreateWorkspaceCommand;
import com.archbuilder.workspace.application.usecase.CreateWorkspaceUseCase;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWorkspaceService implements CreateWorkspaceUseCase {

    private final WorkspaceRepository workspaceRepository;

    @Override
    public Workspace create(CreateWorkspaceCommand command) {
        Workspace workspace = Workspace.builder()
                .userId(command.getUserId())
                .name(command.getName())
                .build();

        return workspaceRepository.save(workspace);
    }
}
