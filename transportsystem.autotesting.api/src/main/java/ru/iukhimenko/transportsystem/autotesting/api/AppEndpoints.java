package ru.iukhimenko.transportsystem.autotesting.api;

public class AppEndpoints {
    public static final String REGISTER_ENDPOINT = "/api/auth/signup";
    public static final String USERS_ENDPOINT = "/api/auth/users";
    public static final String AUTH_ENDPOINT = "/api/auth/signin";
    public static final String ENGINES_ADD_ENDPOINT = "/api/engines/add";
    public static final String ENGINES_ALL_ENDPOINT = "/api/engines";
    public static final String ENGINES_EDIT_ENDPOINT = "/api/engines/edit";
    private static String engines_delete_endpoint = "/api/engines/delete/%s";
    private static String engines_engine_endpoint = "/api/engines/%s";
    public static final String TRANSPORTS_ADD = "/api/transports/add";

    public static String ENGINES_ENGINE_ENDPOINT(Integer id) {
        return String.format(engines_engine_endpoint, id);
    }

    public static String ENGINES_DELETE_ENDPOINT(Integer id) {
        return String.format(engines_delete_endpoint, id);
    }
}
