package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import ru.itis.tdportal.mainservice.models.enums.ModelToUserRelation;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldNameConstants
@Table(name = "model_file_access")
public class ModelFileAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_file_id")
    private ModelFile modelFile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PortalUser user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModelToUserRelation relation;
}
