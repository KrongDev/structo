package com.archbuilder.workspace.interfaces.controller;

import com.archbuilder.common.dto.ApiResponse;
import com.archbuilder.workspace.application.command.CreateWorkspaceCommand;
import com.archbuilder.workspace.application.command.UpdateWorkspaceCommand;
import com.archbuilder.workspace.application.usecase.CreateWorkspaceUseCase;
import com.archbuilder.workspace.application.usecase.GetWorkspaceUseCase;
import com.archbuilder.workspace.application.usecase.UpdateWorkspaceUseCase;
import com.archbuilder.workspace.domain.model.Workspace;
import com.archbuilder.workspace.interfaces.controller.dto.CreateWorkspaceRequest;
import com.archbuilder.workspace.interfaces.controller.dto.UpdateWorkspaceRequest;
import com.archbuilder.workspace.interfaces.controller.dto.WorkspaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final CreateWorkspaceUseCase createWorkspaceUseCase;
    private final UpdateWorkspaceUseCase updateWorkspaceUseCase;
    private final GetWorkspaceUseCase getWorkspaceUseCase;

    @PostMapping
    public ApiResponse<WorkspaceResponse> create(
            @AuthenticationPrincipal String userId,
            @RequestBody CreateWorkspaceRequest request) {
        CreateWorkspaceCommand command = new CreateWorkspaceCommand(userId, request.name());
        Workspace workspace = createWorkspaceUseCase.create(command);
        return ApiResponse.ok(WorkspaceResponse.fromDomain(workspace));
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkspaceResponse> update(
            @AuthenticationPrincipal String userId,
            @PathVariable String id,
            @RequestBody UpdateWorkspaceRequest request) {
        UpdateWorkspaceCommand command = new UpdateWorkspaceCommand(userId, id, request.name());

        Workspace workspace = updateWorkspaceUseCase.update(command);
        return ApiResponse.ok(WorkspaceResponse.fromDomain(workspace));
    }

    @GetMapping("/me")
    public ApiResponse<List<WorkspaceResponse>> getWorkspace(
            @AuthenticationPrincipal String userId) {
        List<Workspace> workspaces = getWorkspaceUseCase.findAllByUserId(userId);
        return ApiResponse.ok(workspaces.stream().map(WorkspaceResponse::fromDomain).toList());
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkspaceResponse> getWorkspace(
            @AuthenticationPrincipal String userId,
            @PathVariable String id) {
        Workspace workspaces = getWorkspaceUseCase.findById(userId, id);
        return ApiResponse.ok(WorkspaceResponse.fromDomain(workspaces));
    }
}
