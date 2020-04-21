package ru.iukhimenko.transportsystem.autotesting.api.tests;

import com.github.javafaker.Faker;
import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.Test;
import ru.iukhimenko.transportsystem.autotesting.api.ApiTest;
import ru.iukhimenko.transportsystem.autotesting.core.model.User;

public class AuthenticationTest extends ApiTest {
    @Test
    @Description("A user can log in with correct credentials")
    public void successfulAuthenticationTest() {
        String username = new Faker().name().username(), password = "1111111";
        new Faker().expression("");
        User user = new User(username, "1");
    }

    /*
    Корректный логин, некорректный пароль
    Логин не в том регистре
    Пароль не в том регистре
    Без пароля
    Без логина
    Корректный пароль, некорректный логин
    Пробелы в начале и конце логина
    * */
}
