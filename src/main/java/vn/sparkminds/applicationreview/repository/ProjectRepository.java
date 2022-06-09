package vn.sparkminds.applicationreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.applicationreview.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
