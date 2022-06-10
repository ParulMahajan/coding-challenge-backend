package vn.sparkminds.applicationreview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import vn.sparkminds.applicationreview.entity.enumeration.CapacityType;
import vn.sparkminds.applicationreview.entity.enumeration.EmploymentMode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The name is required")
    @Size(max = 255, message = "The name is over maximum length")
    private String name;

    @NotNull(message = "The employment mode is required")
    private EmploymentMode employmentMode;

    @NotNull(message = "The capacity is required")
    private CapacityType capacity;

    @NotNull(message = "The duration in months is required")
    @Min(value = 1, message = "The duration in months must be greater than 1")
    @Max(value = 999999, message = "The duration in months must be less than 1000000")
    private Integer durationInMonths;

    @NotNull(message = "The start year is required")
    @Min(value = 1970, message = "The start year must be greater than 1970")
    @Max(value = 9999, message = "The start year must be less than 9999")
    private Integer startYear;

    @NotBlank(message = "The role is required")
    @Size(max = 255, message = "The role is over maximum length")
    private String role;

    @NotNull(message = "The team size is required")
    @Min(value = 1, message = "The team size must be greater than 1")
    @Max(value = 999999, message = "The team size must be less than 1000000")
    private Integer teamSize;

    @Size(max = 500, message = "The repository url is over maximum length")
    private String repositoryUrl;

    @Size(max = 500, message = "The live url is over maximum length")
    private String liveUrl;
}
