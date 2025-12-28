package com.archbuilder.workspace.application.usecase;

import com.archbuilder.workspace.domain.model.Workspace;

import java.util.List;

public interface GetWorkspaceUseCase {
    List<Workspace> findAllByUserId(String userId);

    Workspace findById(String userId, String id);

    void delete(String id);
}
