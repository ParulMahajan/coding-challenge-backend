package vn.sparkminds.applicationreview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.sparkminds.applicationreview.entity.enumeration.CapacityType;
import vn.sparkminds.applicationreview.entity.enumeration.EmploymentMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "project")
@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Project extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_mode", length = 50, nullable = false)
    private EmploymentMode employmentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "capacity", length = 50, nullable = false)
    private CapacityType capacity;

    @Column(name = "duration_in_months", nullable = false)
    private Integer durationInMonths;

    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "team_size", nullable = false)
    private Integer teamSize;

    @Column(name ="repository_url", length = 500)
    private String repositoryUrl;

    @Column(name ="live_url", length = 500)
    private String liveUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_applicant_id", nullable = false)
    @JsonIgnore
    private Applicant applicant;
}
