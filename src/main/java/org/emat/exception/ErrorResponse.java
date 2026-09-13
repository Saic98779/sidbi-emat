package org.emat.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/** Standard error response format. */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
