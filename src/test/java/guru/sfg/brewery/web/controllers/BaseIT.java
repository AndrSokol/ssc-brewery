package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static guru.sfg.brewery.web.controllers.TestUsers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

abstract public class BaseIT {
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(
                Arguments.of(ADMIN.getUsername(), ADMIN.getPassword()),
                Arguments.of(USER.getUsername(), USER.getPassword()),
                Arguments.of(CUSTOMER.getUsername(), CUSTOMER.getPassword())
        );
    }

    public static Stream<Arguments> getStreamAdminCustomer() {
        return Stream.of(
                Arguments.of(ADMIN.getUsername(), ADMIN.getPassword()),
                Arguments.of(CUSTOMER.getUsername(), CUSTOMER.getPassword())
        );
    }

    public static Stream<Arguments> getStreamUsersWithoutAdmin(){
        return Stream.of(
                Arguments.of(USER.getUsername(), USER.getPassword()),
                Arguments.of(CUSTOMER.getUsername(), CUSTOMER.getPassword())
        );
    }
}
