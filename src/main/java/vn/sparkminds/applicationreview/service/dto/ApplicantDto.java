package vn.sparkminds.applicationreview.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The email is required")
    @Size(max = 100, message = "The email is over maximum length")
    private String email;

    @NotBlank(message = "The name is required")
    @Size(max = 100, message = "The name is over maximum length")
    private String name;

    @NotBlank(message = "The github username is required")
    @Size(max = 100, message = "The github username is over maximum length")
    private String githubUser;

    @Valid
    @NotEmpty(message = "The project is required at least 1")
    private List<ProjectDto> projects;
}
