package com.archbuilder.workspace.application.command;

public record UpdateWorkspaceCommand(
    String userId,
    String workspaceId,
    String name
) {
}
