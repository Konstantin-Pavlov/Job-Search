package kg.attractor.jobsearch.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
    private String type;
    private String title;
    private int status;
    private String detail;
    private Object[] detailMessageArguments;
}