package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static guru.sfg.brewery.web.controllers.TestUsers.ADMIN;
import static guru.sfg.brewery.web.controllers.TestUsers.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerIT extends BaseIT{
    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.CustomerControllerIT#getStreamAdminCustomer")
    void findListCustomersAuth(String username, String password) throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic(ADMIN.getUsername(), ADMIN.getPassword())))
                .andExpect(status().isOk());
    }

    @Test
    void findListCustomersUserAuth() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findListCustomersNotAuth() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic(USER.getUsername(), USER.getPassword())))
                .andExpect(status().isForbidden());
    }

    @Nested
    @DisplayName("Add Customers")
    class AddCustomers {

        @Rollback
        @Test
        void processCreationForm() throws Exception {
            mockMvc.perform(post("/customers/new")
                    .param("customerName", "Foo Customer")
                    .with(httpBasic(ADMIN.getUsername(), ADMIN.getPassword())))
                    .andExpect(status().is3xxRedirection());
        }

        @Rollback
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.CustomerControllerIT#getStreamUsersWithoutAdmin")
        void processCreationFormForbiden(String username, String password) throws Exception {
            mockMvc.perform(post("/customers/new")
                    .param("customerName", "Foo Customer")
                    .with(httpBasic(username, password)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void processCreationFormNotAuth() throws Exception {
            mockMvc.perform(post("/customers/new")
                    .param("customerName", "Foo Customer"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
