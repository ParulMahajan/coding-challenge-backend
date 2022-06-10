package vn.sparkminds.applicationreview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Column(name = "github_user", length = 100, nullable = false)
    private String githubUser;

    @OneToMany(mappedBy = "applicant", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    public void addProject(Project project) {
        if (projects == null) projects = new ArrayList<>();
        projects.add(project);
        project.setApplicant(this);
    }

    public void addProjects(List<Project> projects) {
        projects.forEach(this::addProject);
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", githubUser='" + githubUser + '\'' +
                '}';
    }
}
