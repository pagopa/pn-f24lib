package org.f24.exception;

public enum ErrorEnum {

    PATTERN("pattern", "Invalid {field}: {details}."),
    MAX_ITEMS("maxItems", "Too much records for {field}. Max amount of items: {details}."),
    MIN_ITEMS("minItems", "Minimum amount of records required: {details}."),
    TYPE("type", "Field {field} is required."),
    TAX_CODE("taxCode", "Invalid tax code: it not corresponds to other personal data."),
    MOTIVE_RECORD("motiveRecord", "The credit and debit fields cannot be filled with a value at the same time"),
    NEGATIVE_NUM("negativeNum", "This value can`t be less than zero"),
    FIELD_OBSOLETE("emptyField", "Impossible to get field, it`s not present in the acroform: "),
    GENERATOR_OBSOLETE("emptyGen", "Impossible to get generator class, use valid generator:"),
    VALIDATOR_OBSOLETE("emptyGen", "Impossible to get validator class, use valid validator:"),
    RECORD_EMPTY("recordEmpty", "Record can`t be empty"),
    ACROFORM_EMPTY("emtyAcro", "Impossible to get acroform, because it is empty"),
    DEFAULT("default", "Error occurred during validation."),
    MULTI_TAX_PAYER("multiTaxPayer", "Only one type of tax payer is allowed.");

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
