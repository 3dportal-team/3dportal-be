package ru.itis.tdportal.mainservice.models.entities;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import ru.itis.tdportal.core.models.enums.PortalUserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class PortalUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String hashPassword;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @ToString.Exclude
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private PortalUserRole userRole;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<ModelFile> models;

    @Column(name = "redis_user_id")
    private UUID redisUserId;

    @OneToMany(mappedBy = "user")
    private List<ModelFileAccess> accesses;
}
