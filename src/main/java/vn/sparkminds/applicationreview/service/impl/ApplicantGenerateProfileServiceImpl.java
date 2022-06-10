package vn.sparkminds.applicationreview.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vn.sparkminds.applicationreview.constant.AppConstants;
import vn.sparkminds.applicationreview.entity.Applicant;
import vn.sparkminds.applicationreview.entity.Project;
import vn.sparkminds.applicationreview.service.ApplicantGenerateProfileService;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.lowagie.text.Chunk.NEWLINE;
import static com.lowagie.text.FontFactory.COURIER;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicantGenerateProfileServiceImpl implements ApplicantGenerateProfileService {

    private static final Font TITLE_FONT = FontFactory.getFont(COURIER, 20);
    private static final Font LABEL_FONT = FontFactory.getFont(COURIER, 18);
    private static final Font DEFAULT_FONT = FontFactory.getFont(COURIER, 12);

    private final RestTemplate restTemplate;

    @Override
    public byte[] generateApplicantProfileFile(@NonNull Applicant applicant) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, bos);
        document.open();
        generatePdfProfile(document, applicant);
        document.close();
        return bos.toByteArray();
    }

    @Override
    public byte[] generateAllApplicantProfileFile(List<Applicant> applicants) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, bos);
        document.open();
        applicants.forEach(applicant -> {
            generatePdfProfile(document, applicant);
            document.newPage();
        });
        document.close();
        return bos.toByteArray();
    }

    private void generatePdfProfile(@NonNull Document document, @NonNull Applicant applicant) {
        addTitle(document);
        addApplicantInfo(document, applicant);
        addPastProjects(document, applicant.getProjects());
    }

    private void addTitle(Document document) {
        Paragraph title = new Paragraph("Applicant Information", TITLE_FONT);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(NEWLINE);
    }

    private void addApplicantInfo(Document document, Applicant applicant) {
        document.add(new Paragraph(applicant.getName(), LABEL_FONT));
        document.add(new Paragraph("Email: " + applicant.getEmail(), DEFAULT_FONT));
        document.add(new Paragraph("Github: " + applicant.getGithubUser(), DEFAULT_FONT));
        getUserGithubImage(applicant.getGithubUser()).ifPresent(document::add);
        document.add(NEWLINE);
    }

    private void addPastProjects(Document document, List<Project> projects) {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineWidth(0.5f);
        document.add(NEWLINE);
        document.add(new Paragraph("Projects", LABEL_FONT));
        document.add(NEWLINE); document.add(lineSeparator);
        projects.forEach(project -> {
            document.add(new Paragraph("Project name: " + project.getName(), DEFAULT_FONT));
            document.add(new Paragraph("Employment mode: " + project.getEmploymentMode().toReadableString(), DEFAULT_FONT));
            document.add(new Paragraph("Capacity: " + project.getCapacity().toReadableString(), DEFAULT_FONT));
            document.add(new Paragraph("Duration in months: " + project.getDurationInMonths(), DEFAULT_FONT));
            document.add(new Paragraph("Start year: " + project.getStartYear(), DEFAULT_FONT));
            document.add(new Paragraph("Role: " + project.getRole(), DEFAULT_FONT));
            document.add(new Paragraph("Team size: " + project.getTeamSize(), DEFAULT_FONT));
            document.add(new Paragraph("Repository link: " + project.getRepositoryUrl(), DEFAULT_FONT));
            document.add(new Paragraph("Live product link: " + project.getLiveUrl(), DEFAULT_FONT));
            document.add(NEWLINE);
            document.add(lineSeparator);
        });
    }

    private Optional<Image> getUserGithubImage(@NonNull String username) {
        try {
            String url = AppConstants.GITHUB_USER_URL + username;
            HttpHeaders headers = new HttpHeaders();
            var entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode avatarUrlValue = root.path("avatar_url");
            String avatarUrl = avatarUrlValue.asText();
            Image image = Image.getInstance(new URL(avatarUrl));
            image.scaleAbsolute(100f, 100f);
            image.setAbsolutePosition(460, 650);
            return Optional.of(image);
        } catch (Exception e) {
            log.error("Get github image url of user: {} error", username, e);
            return Optional.empty();
        }
    }
}
