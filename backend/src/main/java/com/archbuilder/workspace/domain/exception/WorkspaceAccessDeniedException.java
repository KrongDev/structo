package com.archbuilder.workspace.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class WorkspaceAccessDeniedException extends DomainException {

    public WorkspaceAccessDeniedException(String workspaceName, String userId) {
        super(
            "WORKSPACE_ACCESS_DENIED",
            "User " + userId + " has no access to workspace " + workspaceName
        );
    }
}