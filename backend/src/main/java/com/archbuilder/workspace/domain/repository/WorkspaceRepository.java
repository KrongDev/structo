package com.archbuilder.workspace.domain.repository;

import com.archbuilder.workspace.domain.model.Workspace;
import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository {
    Workspace save(Workspace workspace);

    Optional<Workspace> findById(String id);

    List<Workspace> findByUserId(String userId);

    void deleteById(String id);
}
