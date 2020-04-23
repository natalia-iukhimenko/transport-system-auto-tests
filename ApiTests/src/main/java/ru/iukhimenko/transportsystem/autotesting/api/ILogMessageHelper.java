package ru.iukhimenko.transportsystem.autotesting.api;

public interface ILogMessageHelper {
    public default String getRequestExecutionResultMessage(String endpoint, int statusCode, String statusText) {
        String message = endpoint + ": " + statusCode + " - " + statusText;
        return message;
    }
}
