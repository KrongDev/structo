package com.archbuilder.user.infra.persistence.entity;

import com.archbuilder.common.domain.BaseEntity;
import com.archbuilder.user.domain.model.User;
import com.archbuilder.user.domain.model.vo.AuthProvider;
import com.archbuilder.user.domain.model.vo.PlanType;
import com.archbuilder.user.domain.model.vo.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        },
        indexes = @Index(name = "idx_email", columnList = "email")
)
public class UserEntity extends BaseEntity<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType = PlanType.FREE;

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    @Override
    public User toDomain() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
