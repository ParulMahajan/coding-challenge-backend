package vn.sparkminds.applicationreview.constant;

import java.net.URI;

public final class ErrorConstants {
    private ErrorConstants() {}

    public static final String PROBLEM_BASE_URL = "https://application-review/problem";
    public static final URI NOT_FOUND = URI.create(PROBLEM_BASE_URL + "/not-found");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
}
