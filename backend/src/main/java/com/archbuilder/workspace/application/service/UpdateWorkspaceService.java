package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.application.command.UpdateWorkspaceCommand;
import com.archbuilder.workspace.application.usecase.GetWorkspaceUseCase;
import com.archbuilder.workspace.application.usecase.UpdateWorkspaceUseCase;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateWorkspaceService implements UpdateWorkspaceUseCase {
    private final GetWorkspaceUseCase getWorkspaceUseCase;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public Workspace update(UpdateWorkspaceCommand command) {
        Workspace workspace = getWorkspaceUseCase.findById(command.userId(), command.workspaceId());
        workspace.updateName(command.name());
        return workspaceRepository.save(workspace);
    }
}
