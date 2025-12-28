package com.archbuilder.workspace.application.service;

import com.archbuilder.workspace.application.usecase.GetWorkspaceUseCase;
import com.archbuilder.workspace.domain.exception.WorkspaceAccessDeniedException;
import com.archbuilder.workspace.domain.exception.WorkspaceNotFoundException;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.domain.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkspaceService implements GetWorkspaceUseCase {
    private final WorkspaceRepository workspaceRepository;

    @Override
    public List<Workspace> findAllByUserId(String userId) {
        return workspaceRepository.findByUserId(userId);
    }

    @Override
    public Workspace findById(String userId, String id) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new WorkspaceNotFoundException(id));
        if(!workspace.getUserId().equals(userId)) {
            throw new WorkspaceAccessDeniedException(userId, workspace.getName());
        }
        return workspace;
    }

    @Override
    public void delete(String id) {
        workspaceRepository.deleteById(id);
    }
}
