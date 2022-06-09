package vn.sparkminds.applicationreview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vn.sparkminds.applicationreview.entity.enumeration.CapacityType;
import vn.sparkminds.applicationreview.entity.enumeration.EmploymentMode;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private EmploymentMode employmentMode;
    private CapacityType capacity;
    private Integer durationInMonths;
    private Integer startYear;
    private String role;
    private Integer teamSize;
    private String repositoryUrl;
    private String liveUrl;
}
