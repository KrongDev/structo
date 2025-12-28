package com.archbuilder.workspace.application.usecase;

import com.archbuilder.workspace.application.command.CreateWorkspaceCommand;
import com.archbuilder.workspace.domain.model.Workspace;

public interface CreateWorkspaceUseCase {
    Workspace create(CreateWorkspaceCommand command);
}
