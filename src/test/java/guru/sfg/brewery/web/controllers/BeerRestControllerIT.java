package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerUrlParams() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111?apiKey=spring&apiSecret=guru"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111")
                .header("Api-Key", "spring")
                .header("Api-Secret", "guruXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111")
                .header("Api-Key", "spring")
                .header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ec7e1b00-8a2e-4ba9-83e1-086d9421d111"))
                .andExpect(status().isUnauthorized());
    }

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
