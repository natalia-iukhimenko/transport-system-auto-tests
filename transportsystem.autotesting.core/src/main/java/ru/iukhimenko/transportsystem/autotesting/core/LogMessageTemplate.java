package ru.iukhimenko.transportsystem.autotesting.core;

public final class LogMessageTemplate {
    public static String getHttpMessageText(String endpoint, int statusCode) {
        return endpoint + ": OK " + statusCode;
    }

    public static String getHttpErrorMessageText(String endpoint, int status, String body) {
        return endpoint + ": ERROR " + status + " " + body;
    }
}
