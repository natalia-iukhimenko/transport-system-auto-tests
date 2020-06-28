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
    public static final String TRANSPORTS_ADD_ENDPOINT = "/api/transports/add";
    private static String transports_transport_endpoint = "/api/transports/%s";
    public static final String TRANSPORT_MODELS_ADD_ENDPOINT = "/api/transportmodels/add";
    private static final String transport_model_endpoint = "/api/transportmodels/%s";
    public static final String TRANSPORT_MODELS_ENDPOINT = "/api/transportmodels";

    public static String ENGINES_ENGINE_ENDPOINT(Integer id) {
        return String.format(engines_engine_endpoint, id);
    }

    public static String ENGINES_DELETE_ENDPOINT(Integer id) {
        return String.format(engines_delete_endpoint, id);
    }

    public static String TRANSPORTS_TRANSPORT_ENDPOINT(Integer id) {
        return String.format(transports_transport_endpoint, id);
    }

    public static String TRANSPORT_MODEL_ENDPOINT(Integer id) {
        return String.format(transport_model_endpoint, id);
    }
}
