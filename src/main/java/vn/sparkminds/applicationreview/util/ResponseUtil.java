package vn.sparkminds.applicationreview.util;

import org.springframework.http.ResponseEntity;
import vn.sparkminds.applicationreview.exception.ResourceNotFoundException;

import java.util.Optional;

public final class ResponseUtil {
    private ResponseUtil() {}

    public static <X>ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
    }
}
