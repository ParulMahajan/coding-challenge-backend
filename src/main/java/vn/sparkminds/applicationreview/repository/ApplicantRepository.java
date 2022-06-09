package vn.sparkminds.applicationreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.applicationreview.entity.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    boolean existsByEmail(String email);
}
