package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import guru.sfg.brewery.web.controllers.api.BeerRestController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

abstract public class BaseIT {
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

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

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
}
