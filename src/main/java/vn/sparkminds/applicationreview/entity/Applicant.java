package vn.sparkminds.applicationreview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "applicant")
@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Applicant extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Project> projects;
}
