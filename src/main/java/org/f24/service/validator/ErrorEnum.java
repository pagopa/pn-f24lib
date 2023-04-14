package org.f24.service.validator;

public enum ErrorEnum {

    PATTERN("pattern", "Invalid {field}: {details}."),
    MAX_ITEMS("maxItems", "Too much records for {field}. Max amount of items: {details}."),
    MIN_ITEMS("minItems", "Minimum amount of records required: {details}."),
    TYPE("type", "Field {field} is required."),
    DEFAULT("default", "Error occurred during validation.");

    private String code;
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(String field) {
        return getMessage().replace("{field}", field);
    }

    public String getMessage(String field, String details) {
        return getMessage(field).replace("{details}", details);
    }

}
