package com.archbuilder.auth.infra.persistence.entity;

import com.archbuilder.common.domain.BaseEntity;
import com.archbuilder.auth.domain.model.RefreshToken;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshTokenEntity extends BaseEntity<RefreshToken> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Override
    public RefreshToken toDomain() {
        RefreshToken res = new RefreshToken();
        BeanUtils.copyProperties(this, res);
        return res;
    }

    public static RefreshTokenEntity fromDomain(RefreshToken refreshToken) {
        RefreshTokenEntity res = new RefreshTokenEntity();
        BeanUtils.copyProperties(refreshToken, res);
        return res;
    }
}