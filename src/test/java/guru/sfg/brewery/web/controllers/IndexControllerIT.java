package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import guru.sfg.brewery.web.controllers.api.BeerRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class IndexControllerIT extends BaseIT{

    @MockBean
    private BeerService beerService;
    @MockBean
    private BreweryService breweryService;
    @MockBean
    private BeerRestController beerRestController;
    @MockBean
    private BeerRepository beerRepository;
    @MockBean
    private BeerInventoryRepository beerInventoryRepository;
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void testGetIndexSlash() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
