package ru.iukhimenko.transportsystem.autotesting.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @JsonInclude(NON_NULL)
    private Integer id;
    private @NonNull String username;
    private @NonNull String password;
}