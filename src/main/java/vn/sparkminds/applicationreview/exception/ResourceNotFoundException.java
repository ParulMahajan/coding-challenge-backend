package vn.sparkminds.applicationreview.exception;


import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import vn.sparkminds.applicationreview.constant.ErrorConstants;

public class ResourceNotFoundException extends AbstractThrowableProblem {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(ErrorConstants.NOT_FOUND, message, Status.NOT_FOUND);
    }
}
