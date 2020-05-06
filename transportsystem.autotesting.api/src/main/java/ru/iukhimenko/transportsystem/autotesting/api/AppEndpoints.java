package ru.iukhimenko.transportsystem.autotesting.api;

public class AppEndpoints {
    public static final String REGISTER_ENDPOINT = "/api/auth/signup";
    public static final String USERS_ENDPOINT = "/api/auth/users";
    public static final String AUTH_ENDPOINT = "/api/auth/signin";
    public static final String ENGINES_ADD_ENDPOINT = "/api/engines/add";
    private static String engines_engine_endpoint = "/api/engines/%s";
    public static final String ENGINES_EDIT_ENDPOINT = "/api/engines/edit";

    public static String ENGINES_ENGINE_ENDPOINT(Integer id) {
        return String.format(engines_engine_endpoint, id);
    }
}
