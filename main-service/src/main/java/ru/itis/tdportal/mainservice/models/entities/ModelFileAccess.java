package ru.itis.tdportal.mainservice.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "model_file_access")
@NoArgsConstructor
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
}
