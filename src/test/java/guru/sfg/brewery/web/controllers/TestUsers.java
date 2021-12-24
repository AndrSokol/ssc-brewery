package guru.sfg.brewery.web.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestUsers {
    ADMIN("spring", "guru"),
    USER("user", "password"),
    CUSTOMER("scott", "tiger");

    private final String username;
    private final String password;
}
