package vn.sparkminds.applicationreview.service;

import vn.sparkminds.applicationreview.entity.Applicant;

import java.util.List;

public interface ApplicantGenerateProfileService {
    byte[] generateApplicantProfileFile(Applicant applicant);
    byte[] generateAllApplicantProfileFile(List<Applicant> applicants);
}
