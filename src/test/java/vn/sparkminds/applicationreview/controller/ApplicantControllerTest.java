package vn.sparkminds.applicationreview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vn.sparkminds.applicationreview.IntegrationTest;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.entity.Project;
import vn.sparkminds.applicationreview.entity.enumeration.CapacityType;
import vn.sparkminds.applicationreview.entity.enumeration.EmploymentMode;
import vn.sparkminds.applicationreview.repository.ApplicantRepository;
import vn.sparkminds.applicationreview.repository.ProjectRepository;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;
import vn.sparkminds.applicationreview.service.dto.ProjectDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@IntegrationTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicantControllerTest {

    private static final String BASE_URL = "/api/applicants";

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        if (!applicantRepository.existsById(1L)) {
            createDataTest("test@gmail.com");
        }
    }

    void createDataTest(String email) {
        Project project = new Project();
        project.setName("Challenge");
        project.setEmploymentMode(EmploymentMode.FREELANCE);
        project.setCapacity(CapacityType.FULL_TIME);
        project.setDurationInMonths(1);
        project.setStartYear(2022);
        project.setRole("Developer");
        project.setTeamSize(1);
        project.setRepositoryUrl("https://github.com/AM-code-treasure/coding-challenge-backend");
        project.setLiveUrl("https://github.com/AM-code-treasure/coding-challenge-backend");

        Applicant applicant = new Applicant();
        applicant.setEmail(email);
        applicant.setName("test");
        applicant.setGithubUser("testcode");
        applicant.setProjects(List.of(project));
        project.setApplicant(applicant);

        applicantRepository.save(applicant);
    }

    ProjectDto createTestProjectDto() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName("Challenge");
        projectDto.setEmploymentMode(EmploymentMode.FREELANCE);
        projectDto.setCapacity(CapacityType.FULL_TIME);
        projectDto.setDurationInMonths(1);
        projectDto.setStartYear(2022);
        projectDto.setRole("Developer");
        projectDto.setTeamSize(1);
        projectDto.setRepositoryUrl("https://github.com/AM-code-treasure/coding-challenge-backend");
        projectDto.setLiveUrl("https://github.com/AM-code-treasure/coding-challenge-backend");
        return projectDto;
    }

    @Test
    @Order(1)
    void canGetApplicants() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].email").value(hasItem("test@gmail.com")))
                .andExpect(jsonPath("$.[*].projects.[*].name").value(hasItem("Challenge")));
    }

    @Test
    @Order(2)
    void canGetApplicant() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.projects.[*].name").value(hasItem("Challenge")));
    }

    @Test
    @Order(3)
    void willThrowNotFoundWhenGetApplicant() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void canGetApplicantProfilePdf() throws Exception {
        mockMvc.perform(get(BASE_URL + "/profile-pdf/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void willThrowNotFoundWhenGetApplicantProfilePdf() throws Exception {
        mockMvc.perform(get(BASE_URL + "/profile-pdf/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void canGetAllApplicantProfilePdf() throws Exception {
        mockMvc.perform(get(BASE_URL + "/profile-pdf"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void createApplicant() throws Exception {
        String email = "create@gmail.com";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(List.of(createTestProjectDto()));

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Optional<Applicant> applicant = applicantRepository.findByEmail(email);
        assertThat(applicant).isPresent();
        assertThat(applicant.get().getProjects().size()).isEqualTo(1);
        assertThat(applicant.get().getProjects().get(0).getName()).isEqualTo("Challenge");
    }

    @Test
    @Order(8)
    void willBadRequestWhenCreateApplicantWithNullPastProject() throws Exception {
        String email = "create@gmail.com";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(null);

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void willBadRequestWhenCreateApplicantWithEmptyPastProject() throws Exception {
        String email = "create@gmail.com";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(new ArrayList<>());

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    void willBadRequestWhenCreateApplicantWithInvalidEmail() throws Exception {
        String email = "create";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(List.of(createTestProjectDto()));

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    void willBadRequestWhenCreateApplicantWithNullName() throws Exception {
        String email = "create@gmail.com";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName(null);
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(List.of(createTestProjectDto()));

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(12)
    void willBadRequestWhenCreateApplicantWithNullGithub() throws Exception {
        String email = "create@gmail.com";
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser(null);
        applicantDto.setProjects(List.of(createTestProjectDto()));

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(13)
    void willBadRequestWhenCreateApplicantWithInvalidProject() throws Exception {
        String email = "create@gmail.com";
        ProjectDto projectDto = createTestProjectDto();
        projectDto.setName(null);
        ApplicantDto applicantDto = new ApplicantDto();
        applicantDto.setEmail(email);
        applicantDto.setName("test");
        applicantDto.setGithubUser("testcode");
        applicantDto.setProjects(List.of(projectDto));

        String json = mapper.writeValueAsString(applicantDto);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(14)
    void canDeleteApplicant() throws Exception {
        String email = "delete@gmail.com";
        createDataTest(email);
        Applicant applicant = applicantRepository.findByEmail(email).get();
        Project project = applicant.getProjects().get(0);
        mockMvc.perform(delete(BASE_URL + "/" + applicant.getId()))
                .andExpect(status().isNoContent());
        assertThat(applicantRepository.findByEmail(email)).isEmpty();
        assertThat(projectRepository.findById(project.getId())).isEmpty();
    }

    @Test
    @Order(15)
    void willThrowNotFoundWhenDeleteApplicant() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(16) // This case must be run at last, cause need to delete all data
    void willReturnNoContentWhenGetAllApplicantProfilePdf() throws Exception {
        projectRepository.deleteAll();
        applicantRepository.deleteAll();
        mockMvc.perform(get(BASE_URL + "/profile-pdf"))
                .andExpect(status().isNoContent());
    }
}