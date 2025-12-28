package com.archbuilder.workspace.infra.persistence;

import com.archbuilder.workspace.infra.persistence.entity.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkspaceJpaRepository extends JpaRepository<WorkspaceEntity, String> {
    List<WorkspaceEntity> findByUserId(String userId);
}
