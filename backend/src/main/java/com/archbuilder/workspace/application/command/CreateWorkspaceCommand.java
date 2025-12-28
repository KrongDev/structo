package com.archbuilder.workspace.application.command;

public record CreateWorkspaceCommand(
        String userId,
        String name
) {
}
