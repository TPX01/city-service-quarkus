package io.example.service.common.rest.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApiError {

    public enum Type {
        BUSINESS, TECHNICAL
    }

    private Type type;
    private String description;
    private String fieldName;
    private String originalValue;
    private String spanId;
    private Throwable throwable;

    public ApiError(Type type, String description) {
        this.type = type;
        this.description = description;
    }

    public ApiError(Type type, String description, Throwable throwable) {
        this.type = type;
        this.description = description;
        this.throwable = throwable;
    }

    public ApiError(Type type, String description, String fieldName, String originalValue) {
        this.type = type;
        this.description = description;
        this.fieldName = fieldName;
        this.originalValue = originalValue;
    }

    public ApiError(Type type, String description, String fieldName, String originalValue, Throwable throwable) {
        this.type = type;
        this.description = description;
        this.fieldName = fieldName;
        this.originalValue = originalValue;
        this.throwable = throwable;
    }
}
