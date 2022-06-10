package vn.sparkminds.applicationreview.service;

import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;

import java.util.List;

public interface ApplicantService {

    List<Applicant> getApplicants();

    Applicant getApplicant(Long applicantId);

    Applicant createApplicant(ApplicantDto request);

    void deleteApplicant(Long applicantId);
}
