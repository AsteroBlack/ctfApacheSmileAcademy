package ci.smile.simswaporange.utils;

import org.springframework.dao.EmptyResultDataAccessException;
public class CustomEntityNotFoundException extends EmptyResultDataAccessException {
    private final String code;
    private final String message;
    private final boolean hasError;

    public CustomEntityNotFoundException(String code, String message, boolean hasError) {
        super(1);
        this.code = code;
        this.message = message;
        this.hasError = hasError;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean hasError() {
        return hasError;
    }
}

