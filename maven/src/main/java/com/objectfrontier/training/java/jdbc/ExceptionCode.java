package com.objectfrontier.training.java.jdbc;

public enum ExceptionCode {

    FIRST_NAME_EMPTY("First name should not be empty"),
    LAST_NAME_EMPTY("Last name should not be empty"),
    EMAIL_EMPTY("Email address should not be empty"),
    BIRTH_DATE_EMPTY("Birth date should not be empty"),
    CREATED_DATE_EMPTY("Created date should not be empty"),
    INVALID_DATE_FORMAT("Invalid date format"),
    EMAIL_ADDRESS_DUPLICATE("Duplicate Email address entry"),
    ID_AUTOINCREMENT("Id should be auto-incremented"),
    SAME_FIRST_NAME_LAST_NAME("First name and last name should not be same"),
    ID_NOT_FOUND("No entry found in the given ID"),
    STREET_NAME_EMPTY("Street should not be empty"),
    CITY_NAME_EMPTY("City should not be empty"),
    POSTAL_CODE_EMPTY("Postal code should not be empty");

    public String errorMsg;

    private ExceptionCode(String error) {
        this.errorMsg = error;
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }
}
