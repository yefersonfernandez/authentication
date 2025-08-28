package com.powerup.enums;

public enum ExceptionMessages {

    USER_WITH_EMAIL_EXISTS("User with email '%s' already exists"),
    BASE_SALARY_OUT_OF_RANGE("Field 'baseSalary' is out of the allowed range: %s"),
    USER_NOT_FOUND("User with identity document '%s' does not exist");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }

}
