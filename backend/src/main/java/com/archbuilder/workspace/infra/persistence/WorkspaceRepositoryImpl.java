package com.archbuilder.workspace.infra.persistence;

import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import com.archbuilder.workspace.infra.persistence.entity.WorkspaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepositoryImpl implements WorkspaceRepository {

    private final WorkspaceJpaRepository jpaRepository;

    @Override
    public Workspace save(Workspace workspace) {
        return jpaRepository.save(WorkspaceEntity.fromDomain(workspace)).toDomain();
    }

    @Override
    public Optional<Workspace> findById(String id) {
        return jpaRepository.findById(id).map(WorkspaceEntity::toDomain);
    }

    @Override
    public List<Workspace> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(WorkspaceEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }
}
