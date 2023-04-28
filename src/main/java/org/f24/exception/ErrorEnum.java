package org.f24.exception;

public enum ErrorEnum {

    PATTERN("pattern", "Invalid {field}: {details}."),
    MAX_ITEMS("maxItems", "Too much records for {field}. Max amount of items: {details}."),
    MIN_ITEMS("minItems", "Minimum amount of records required: {details}."),
    TYPE("type", "Field {field} is required."),
    TAX_CODE("taxCode", "Invalid tax code: it not corresponds to other personal data."),
    DEFAULT("default", "Error occurred during validation."),
    MOTIVE_RECORD("motiveRecord", "The credit and debit fields cannot be filled with a value at the same time"),
    NEGATIVE_NUM("negativeNum", "This value can`t be less than zero"),
    FIELD_OBSOLETE("emptyField", "Impossible to get field, it`s not present in the acroform: "),
    ACROFORM_EMPTY("emtyAcro", "Impossible to get acroform, because it is empty");

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
