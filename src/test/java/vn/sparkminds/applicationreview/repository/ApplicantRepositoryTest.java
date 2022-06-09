package vn.sparkminds.applicationreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vn.sparkminds.applicationreview.entity.Applicant;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ApplicantRepositoryTest {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Test
    void itShouldPresentWhenFindByEmail() {
        Applicant applicant = new Applicant();
        applicant.setEmail("test1@gmail.com");
        applicant.setName("test1");
        applicant.setGithubUser("test1");
        applicant.setProjects(new ArrayList<>());
        applicant.setCreatedBy("ANONYMOUS");

        applicantRepository.save(applicant);

        Optional<Applicant> selectedApplicant = applicantRepository
                .findByEmail(applicant.getEmail());

        assertThat(selectedApplicant).isPresent();
    }
}