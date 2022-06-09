package vn.sparkminds.applicationreview.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import vn.sparkminds.applicationreview.constant.ErrorConstants;

public class EmailAlreadyUsedException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email already used", Status.BAD_REQUEST);
    }
}
