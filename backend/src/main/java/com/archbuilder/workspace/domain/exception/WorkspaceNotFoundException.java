package com.archbuilder.workspace.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class WorkspaceNotFoundException extends DomainException {
    public WorkspaceNotFoundException(String id) {
        super(
            "WORKSPACE_NOT_FOUND",
            "워크스페이스가 존재하지 않습니다: " + id
        );
    }
}
