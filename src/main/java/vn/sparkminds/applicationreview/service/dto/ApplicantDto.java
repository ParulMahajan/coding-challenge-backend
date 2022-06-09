package vn.sparkminds.applicationreview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The email is required")
    private String email;

    @NotBlank(message = "The name is required")
    private String name;

    @Size(max = 500, message = "The github url is over maximum length")
    private String githubUrl;

    private List<ProjectDto> projects;
}
