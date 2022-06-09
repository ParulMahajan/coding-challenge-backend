package vn.sparkminds.applicationreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.service.ApplicantGenerateProfileService;
import vn.sparkminds.applicationreview.service.ApplicantService;
import vn.sparkminds.applicationreview.service.dto.ApplicantDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/applicants")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final ApplicantGenerateProfileService applicantGenerateProfileService;

    @GetMapping(produces = "application/api-v1+json")
    public ResponseEntity<List<Applicant>> getApplicants() {
        return ResponseEntity.ok(applicantService.getApplicants());
    }

    @GetMapping(value = "/{applicantId}", produces = "application/api-v1+json")
    public ResponseEntity<Applicant> getApplicant(@PathVariable Long applicantId) {
        return ResponseEntity.ok(applicantService.getApplicant(applicantId));
    }

    @GetMapping(value = "/profile-pdf/{applicantId}", produces = "application/api-v1+json")
    public ResponseEntity<?> getApplicantProfilePdf(@PathVariable Long applicantId) {
        Applicant applicant = applicantService.getApplicant(applicantId);
        byte[] contentBytes = applicantGenerateProfileService.generateApplicantProfileFile(applicant);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + applicant.getEmail() + ".pdf")
                .contentLength(contentBytes.length)
                .body(contentBytes);
    }

    @GetMapping(value = "/profile-pdf", produces = "application/api-v1+json")
    public ResponseEntity<?> getAllApplicantProfilePdf() {
        List<Applicant> applicants = applicantService.getApplicants();

        if (applicants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        byte[] contentBytes = applicantGenerateProfileService.generateAllApplicantProfileFile(applicants);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=all_applicant.pdf")
                .contentLength(contentBytes.length)
                .body(contentBytes);
    }

    @PostMapping(consumes = "application/json", produces = "application/api-v1+json")
    public ResponseEntity<Applicant> createApplicant(@Valid @RequestBody ApplicantDto request) {
        return ResponseEntity.ok(applicantService.createApplicant(request));
    }

    @DeleteMapping(value = "/{applicantId}", produces = "application/api-v1+json")
    public ResponseEntity<?> deleteApplicant(@PathVariable Long applicantId) {
        applicantService.deleteApplicant(applicantId);
        return ResponseEntity.noContent().build();
    }
}
