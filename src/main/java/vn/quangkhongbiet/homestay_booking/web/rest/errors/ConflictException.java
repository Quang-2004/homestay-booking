package vn.quangkhongbiet.homestay_booking.web.rest.errors;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import tech.jhipster.web.rest.errors.ProblemDetailWithCause;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class ConflictException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public ConflictException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.CONFLICT, defaultMessage, entityName, errorKey);
    }

    public ConflictException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(
            HttpStatus.CONFLICT,
            ProblemDetailWithCauseBuilder.instance()
                .withStatus(HttpStatus.CONFLICT.value())
                .withType(type)
                .withDetail(defaultMessage)
                .withProperty("message", "error." + errorKey)
                .withProperty("params", entityName)
                .build(),
            null
        );
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public ProblemDetailWithCause getProblemDetailWithCause() {
        return (ProblemDetailWithCause) this.getBody();
    }
}
