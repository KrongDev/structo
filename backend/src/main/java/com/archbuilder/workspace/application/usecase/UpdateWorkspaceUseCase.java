package com.archbuilder.workspace.application.usecase;

import com.archbuilder.workspace.application.command.UpdateWorkspaceCommand;
import com.archbuilder.workspace.domain.model.Workspace;

public interface UpdateWorkspaceUseCase {
    Workspace update(UpdateWorkspaceCommand command);
}
