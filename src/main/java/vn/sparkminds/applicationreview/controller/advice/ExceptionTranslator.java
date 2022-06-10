package vn.sparkminds.applicationreview.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@Slf4j
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

    @Override
    public ResponseEntity<Problem> process(ResponseEntity<Problem> entity, NativeWebRequest request) {
        return ProblemHandling.super.process(entity, request);
    }
}
