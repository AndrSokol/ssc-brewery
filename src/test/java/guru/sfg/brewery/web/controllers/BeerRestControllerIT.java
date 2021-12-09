package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    public void findBeerWithHttpBasic() throws Exception {
        mockMvc.perform(get("/api/v1/beer").with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    public void findBeerWithHttpBasicScottTiger() throws Exception {
        mockMvc.perform(get("/api/v1/beer").with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/31313132"))
                .andExpect(status().isOk());
    }
}
