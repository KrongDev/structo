package com.archbuilder.workspace.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateWorkspaceCommand {
    private final String userId;
    private final String name;
}
