package org.emat.exception;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Global exception handler for the application. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String PATH = "path";
    private static final String ERROR_CODE = "errorCode";
    private static final String ORACLE_DETAIL = "oracleDetail";
    private static final Pattern ORACLE_ERROR_PATTERN = Pattern.compile("ORA-\\d{5}:[^\\n\\r]*");
    private static final Pattern ORACLE_CODE_PATTERN = Pattern.compile("ORA-\\d{5}");
    private static final String ORA_UNIQUE_VIOLATION_CODE = "ORA-00001";
    private static final String PAN_COLUMN = "PAN_NO";
    private static final String PAN_CONSTRAINT = "SYS_C008558";
    private static final Map<String, HttpStatus> ORACLE_STATUS_MAP =
            Map.ofEntries(
                    Map.entry("ORA-00001", HttpStatus.CONFLICT),
                    Map.entry("ORA-01400", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-02290", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-02291", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-02292", HttpStatus.CONFLICT),
                    Map.entry("ORA-00942", HttpStatus.NOT_FOUND),
                    Map.entry("ORA-00904", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01017", HttpStatus.UNAUTHORIZED),
                    Map.entry("ORA-28000", HttpStatus.UNAUTHORIZED),
                    Map.entry("ORA-01722", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-12899", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01438", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-00054", HttpStatus.LOCKED),
                    Map.entry("ORA-12514", HttpStatus.SERVICE_UNAVAILABLE),
                    Map.entry("ORA-12154", HttpStatus.SERVICE_UNAVAILABLE),
                    Map.entry("ORA-01000", HttpStatus.TOO_MANY_REQUESTS),
                    Map.entry("ORA-00933", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-00936", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-00947", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-00923", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-00907", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-04098", HttpStatus.INTERNAL_SERVER_ERROR),
                    Map.entry("ORA-04088", HttpStatus.INTERNAL_SERVER_ERROR),
                    Map.entry("ORA-00604", HttpStatus.INTERNAL_SERVER_ERROR),
                    Map.entry("ORA-06512", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-06502", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01858", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01843", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01847", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01830", HttpStatus.BAD_REQUEST),
                    Map.entry("ORA-01555", HttpStatus.CONFLICT),
                    Map.entry("ORA-02100", HttpStatus.SERVICE_UNAVAILABLE),
                    Map.entry("ORA-03113", HttpStatus.SERVICE_UNAVAILABLE),
                    Map.entry("ORA-03114", HttpStatus.SERVICE_UNAVAILABLE),
                    Map.entry("ORA-01012", HttpStatus.UNAUTHORIZED));
    private static final Map<String, String> ORACLE_ERROR_MESSAGES =
            Map.ofEntries(
                    Map.entry("ORA-00001", "A duplicate value violates a unique database constraint."),
                    Map.entry("ORA-01400", "A required value is null."),
                    Map.entry("ORA-02290", "A check constraint validation failed."),
                    Map.entry("ORA-02291", "A referenced parent record does not exist."),
                    Map.entry("ORA-02292", "The record is still referenced by child data."),
                    Map.entry("ORA-00942", "The database table or view does not exist."),
                    Map.entry("ORA-00904", "An invalid identifier was used."),
                    Map.entry("ORA-01017", "Database authentication failed."),
                    Map.entry("ORA-28000", "The database account is locked."),
                    Map.entry("ORA-01722", "An invalid number was provided."),
                    Map.entry("ORA-12899", "A value is too large for the target column."),
                    Map.entry("ORA-01438", "A numeric value exceeds the allowed precision."),
                    Map.entry("ORA-00054", "The resource is busy or locked by another transaction."),
                    Map.entry("ORA-12514", "The database service is unavailable."),
                    Map.entry("ORA-12154", "The database connection identifier could not be resolved."),
                    Map.entry("ORA-01000", "The database has reached the maximum open cursor limit."),
                    Map.entry("ORA-00933", "The SQL statement is malformed."),
                    Map.entry("ORA-00936", "A SQL expression is missing."),
                    Map.entry("ORA-00947", "The INSERT statement has an invalid value count."),
                    Map.entry("ORA-00923", "A SQL keyword is missing or misplaced."),
                    Map.entry("ORA-00907", "A SQL parenthesis is missing."),
                    Map.entry("ORA-04098", "A database trigger is invalid."),
                    Map.entry("ORA-04088", "A database trigger failed during execution."),
                    Map.entry("ORA-00604", "An Oracle internal error occurred."),
                    Map.entry("ORA-06512", "A PL/SQL program raised an exception."),
                    Map.entry("ORA-06502", "A PL/SQL numeric or value error occurred."),
                    Map.entry("ORA-01858", "A non-numeric character was found in a numeric context."),
                    Map.entry("ORA-01843", "The month value is invalid."),
                    Map.entry("ORA-01847", "The day of month is invalid."),
                    Map.entry("ORA-01830", "The date format is invalid."),
                    Map.entry("ORA-01555", "The snapshot is too old for the requested read."),
                    Map.entry("ORA-02100", "The Oracle database is unavailable."),
                    Map.entry("ORA-03113", "The database communication channel ended unexpectedly."),
                    Map.entry("ORA-03114", "The database is not connected."),
                    Map.entry("ORA-01012", "The user is not logged in to the database."));

    /** Handle EntityNotFoundException. */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    /** Handle IllegalArgumentException. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    /** Handle validation failures. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        String detail =
                ex.getBindingResult().getFieldErrors().stream()
                        .findFirst()
                        .map(
                                fieldError ->
                                        fieldError.getField()
                                                + " "
                                                + fieldError.getDefaultMessage())
                        .orElse("Request validation failed");
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Bad Request", detail, request);
    }

    /** Handle CAPTCHA validation failures. */
    @ExceptionHandler(CaptchaValidationException.class)
    public ResponseEntity<ProblemDetail> handleCaptchaValidationException(
            CaptchaValidationException ex, WebRequest request) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    /** Handle bad request parameter issues. */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ProblemDetail> handleBadRequestExceptions(
            Exception ex, WebRequest request) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), request);
    }

    /**
     * Handle malformed/unreadable JSON request bodies (e.g. a plain ID sent for an encrypted
     * field).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        String detail = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                "Malformed request body: " + detail,
                request);
    }

    /** Handle authentication failures. */
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<ProblemDetail> handleAuthenticationException(
            Exception ex, WebRequest request) {
        return buildProblemDetail(
                HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), request);
    }

    /** Handle Vault AppRole authentication/authorization failures. */
    @ExceptionHandler(VaultAppRoleAuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleVaultAppRoleAuthenticationException(
            VaultAppRoleAuthenticationException ex, WebRequest request) {
        return buildProblemDetail(
                ex.getStatus(), ex.getStatus().getReasonPhrase(), ex.getMessage(), request);
    }

    /** Handle authorization failures. */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        return buildProblemDetail(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request);
    }

    /** Handle database integrity violations with clear Oracle details. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        return buildOracleProblemDetail(ex, request);
    }

    /** Handle generic exceptions. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception ex, WebRequest request) {
        String oracleDetail = extractOracleDetail(ex);
        String oracleCode = extractOracleCode(oracleDetail);
        if (oracleCode != null) {
            return buildOracleProblemDetail(ex, request);
        }
        return buildProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage() == null ? "An unexpected server error occurred" : ex.getMessage(),
                request);
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(
            HttpStatus status, String title, String detail, WebRequest request) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        status,
                        detail == null || detail.isBlank() ? status.getReasonPhrase() : detail);
        problemDetail.setTitle(title);
        problemDetail.setProperty(PATH, request.getDescription(false).replace("uri=", ""));
        problemDetail.setProperty(TIMESTAMP, LocalDateTime.now());
        return ResponseEntity.status(status).body(problemDetail);
    }

    private ResponseEntity<ProblemDetail> buildOracleProblemDetail(Throwable throwable, WebRequest request) {
        String oracleDetail = extractOracleDetail(throwable);
        String oracleCode = extractOracleCode(oracleDetail);
        if (oracleCode == null) {
            return buildProblemDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error",
                    throwable.getMessage() == null ? "An unexpected server error occurred" : throwable.getMessage(),
                    request);
        }

        if (ORA_UNIQUE_VIOLATION_CODE.equals(oracleCode)) {
            String normalized = oracleDetail.toUpperCase(Locale.ROOT);
            if (normalized.contains(PAN_COLUMN) || normalized.contains(PAN_CONSTRAINT)) {
                return buildProblemDetail(
                        HttpStatus.CONFLICT, "Conflict", "PAN number already exists", request);
            }
            return buildProblemDetail(
                    HttpStatus.CONFLICT,
                    "Conflict",
                    "Duplicate value violates a unique database constraint: " + oracleDetail,
                    request);
        }

        HttpStatus status = ORACLE_STATUS_MAP.getOrDefault(oracleCode, HttpStatus.INTERNAL_SERVER_ERROR);
        String title = ORACLE_ERROR_MESSAGES.getOrDefault(oracleCode, "Database error");
        ResponseEntity<ProblemDetail> response =
                buildProblemDetail(status, status.getReasonPhrase(), oracleDetail, request);
        ProblemDetail problemDetail = response.getBody();
        if (problemDetail != null) {
            problemDetail.setProperty(ERROR_CODE, oracleCode);
            problemDetail.setProperty(ORACLE_DETAIL, oracleDetail);
            problemDetail.setProperty("reason", title);
        }
        return response;
    }

    private String extractOracleCode(String oracleDetail) {
        if (oracleDetail == null || oracleDetail.isBlank()) {
            return null;
        }
        Matcher matcher = ORACLE_CODE_PATTERN.matcher(oracleDetail);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        return null;
    }

    private String extractOracleDetail(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String message = current.getMessage();
            if (message != null) {
                Matcher matcher = ORACLE_ERROR_PATTERN.matcher(message);
                if (matcher.find()) {
                    return matcher.group().trim();
                }
            }
            current = current.getCause();
        }
        return throwable.getMessage() == null
                ? "Database integrity violation"
                : throwable.getMessage();
    }
}
