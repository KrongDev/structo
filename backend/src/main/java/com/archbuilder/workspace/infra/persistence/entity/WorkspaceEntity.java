package com.archbuilder.workspace.infra.persistence.entity;

import com.archbuilder.common.domain.BaseEntity;
import com.archbuilder.workspace.domain.model.Workspace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workspaces")
public class WorkspaceEntity extends BaseEntity<Workspace> {

    @Id
    @UuidGenerator
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Override
    public Workspace toDomain() {
        Workspace res = new Workspace();
        BeanUtils.copyProperties(this, res);
        return res;
    }

    public static WorkspaceEntity fromDomain(Workspace workspace) {
        WorkspaceEntity res = new WorkspaceEntity();
        BeanUtils.copyProperties(workspace, res);
        return res;
    }
}
