package ru.iukhimenko.transportsystem.autotesting.api;

public interface ILogMessageHelper {
    public default String getRequestExecutionResultMessage(String endpoint, int statusCode) {
        String message = endpoint + ": " + statusCode;
        return message;
    }
}
