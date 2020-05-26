package ru.iukhimenko.transportsystem.autotesting.api.tags;
import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("api_smoke")
public @interface ApiSmoke {
}
