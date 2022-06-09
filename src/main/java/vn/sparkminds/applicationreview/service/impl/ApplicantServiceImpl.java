package vn.sparkminds.applicationreview.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.entity.Project;
import vn.sparkminds.applicationreview.exception.EmailAlreadyUsedException;
import vn.sparkminds.applicationreview.exception.ResourceNotFoundException;
import vn.sparkminds.applicationreview.repository.ApplicantRepository;
import vn.sparkminds.applicationreview.repository.ProjectRepository;
import vn.sparkminds.applicationreview.service.ApplicantService;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;
import vn.sparkminds.applicationreview.service.mapper.ApplicantMapper;
import vn.sparkminds.applicationreview.service.mapper.ProjectMapper;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    /** Repositories */
    private final ApplicantRepository applicantRepository;
    private final ProjectRepository projectRepository;

    /** Mappers */
    private final ApplicantMapper applicantMapper;
    private final ProjectMapper projectMapper;

    @Override
    public List<Applicant> getApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant getApplicant(Long applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Applicant %s not found", applicantId)
                ));
    }

    @Override
    @Transactional
    public Applicant createApplicant(ApplicantDto request) {
        Applicant applicant = applicantMapper.toEntity(request);
        List<Project> projects = projectMapper.toEntity(request.getProjects());

        if (applicantRepository.existsByEmail(applicant.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        projects.forEach(project -> project.setApplicant(applicant));
        applicant.setProjects(projects);

        return applicantRepository.save(applicant);
    }

    @Override
    @Transactional
    public void updateApplicant(Long applicantId, ApplicantDto request) {
        Applicant applicant = getApplicant(applicantId);
        List<Project> newProjects = projectMapper.toEntity(request.getProjects());

        projectRepository.deleteAll(applicant.getProjects());

        applicant.setName(request.getName());
        applicant.setGithubUrl(request.getGithubUrl());
        newProjects.forEach(project -> project.setApplicant(applicant));
        applicant.setProjects(newProjects);
    }

    @Override
    @Transactional
    public void deleteApplicant(Long applicantId) {
        Applicant applicant = getApplicant(applicantId);
        projectRepository.deleteAll(applicant.getProjects());
        applicantRepository.delete(applicant);
    }

}
