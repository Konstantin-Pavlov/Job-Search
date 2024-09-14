package kg.attractor.jobsearch.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorResponseBody {
    private String error;
    private Map<String, List<String>> reasons;
}
