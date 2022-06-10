package vn.sparkminds.applicationreview.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.exception.ResourceNotFoundException;
import vn.sparkminds.applicationreview.repository.ApplicantRepository;
import vn.sparkminds.applicationreview.repository.ProjectRepository;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;
import vn.sparkminds.applicationreview.service.impl.ApplicantServiceImpl;
import vn.sparkminds.applicationreview.service.mapper.ApplicantMapper;
import vn.sparkminds.applicationreview.service.mapper.ApplicantMapperImpl;
import vn.sparkminds.applicationreview.service.mapper.ProjectMapper;
import vn.sparkminds.applicationreview.service.mapper.ProjectMapperImpl;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

    @Mock private ApplicantRepository applicantRepository;
    @Mock private ProjectRepository projectRepository;

    private ApplicantMapper applicantMapper;
    private ProjectMapper projectMapper;

    private ApplicantService applicantService;
    private Applicant testData;

    @BeforeEach
    private void setUp() {
        applicantMapper = new ApplicantMapperImpl();
        projectMapper = new ProjectMapperImpl();
        applicantService = new ApplicantServiceImpl(applicantRepository, applicantMapper, projectMapper);

        testData = new Applicant();
        testData.setId(1L);
        testData.setEmail("test1@gmail.com");
        testData.setName("test1");
        testData.setGithubUser("test1");
        testData.setProjects(new ArrayList<>());
        testData.setCreatedBy("ANONYMOUS");
    }

    @Test
    void canGetApplicants() {
        applicantService.getApplicants();
        verify(applicantRepository).findAll();
    }

    @Test
    void itShouldThrowErrorWhenGetApplicant() {
        assertThrows(ResourceNotFoundException.class, () -> applicantService.getApplicant(1L));
    }

    @Test
    void canGetApplicant() {
        when(applicantRepository.findById(1L)).thenReturn(Optional.of(testData));
        Applicant selectedApplicant = applicantService.getApplicant(1L);
        assertThat(selectedApplicant).isEqualTo(testData);

    }

    @Test
    void canCreateApplicant() {
        ApplicantDto testDto = applicantMapper.toDto(testData);
        when(applicantRepository.findByEmail("test1@gmail.com")).thenReturn(Optional.of(testData));
        applicantService.createApplicant(testDto);

        verify(applicantRepository).delete(testData);

        ArgumentCaptor<Applicant> applicantArgumentCaptor = ArgumentCaptor.forClass(Applicant.class);
        verify(applicantRepository).save(applicantArgumentCaptor.capture());

        Applicant applicant = applicantArgumentCaptor.getValue();

        assertThat(applicant.getEmail()).isEqualTo(testDto.getEmail());

    }

    @Test
    void canDeleteApplicant() {
        when(applicantRepository.findById(1L)).thenReturn(Optional.of(testData));
        applicantService.deleteApplicant(1L);
        verify(applicantRepository).delete(testData);
    }

    @Test
    void itShouldThrowErrorWhenDeleteApplicant() {
        assertThrows(ResourceNotFoundException.class, () -> applicantService.deleteApplicant(1L));
    }
}