package vn.sparkminds.applicationreview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "applicationAuditorAware")
@EnableJpaRepositories({"vn.sparkminds.applicationreview.repository"})
public class DatabaseConfiguration {
}
