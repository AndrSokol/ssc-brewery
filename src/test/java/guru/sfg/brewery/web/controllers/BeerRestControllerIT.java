package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static guru.sfg.brewery.bootstrap.DefaultBreweryLoader.BEER_1_UPC;
import static guru.sfg.brewery.web.controllers.TestUsers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    Beer beerToFind;

    @Autowired
    BeerRepository beerRepository;

    @BeforeEach
    public void setUp(){
        beerToFind = beerRepository.findByUpc(BEER_1_UPC);
    }

    @DisplayName("Delete Tests")
    @Nested
    class DeteleTests {
        public Beer beerToDelete(){
            Random random = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(random.nextInt(9999999)))
                    .build());
        }

        @Test
        void deleteBeerHttpBasicAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerBadCreds() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring")
                    .header("Api-Secret", "guruXXX"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeer() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring")
                    .header("Api-Secret", "guru"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerHttpBasic() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Find by Id")
    class FindBeer {
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerRestControllerIT#getStreamAllUsers")
        void findBeerAuth(String username, String password) throws Exception {
            mockMvc.perform(get("/api/v1/beer")
                    .with(httpBasic(ADMIN.getUsername(), ADMIN.getPassword())))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void findBeers_NoUser() throws Exception {
            mockMvc.perform(get("/api/v1/beer"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Find by Id")
    class FindById {
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerRestControllerIT#getStreamAllUsers")
        void findBeerByIdAuth(String username, String password) throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind.getId())
                    .with(httpBasic(ADMIN.getUsername(), ADMIN.getPassword())))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void findBeerById_NoUser() throws Exception {
            mockMvc.perform(get("/api/v1/beer/" + beerToFind.getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Find By UPC")
    class FindByUPC{
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerRestControllerIT#getStreamAllUsers")
        void findBeerByUpcAuth(String username, String password) throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind.getUpc())
                    .with(httpBasic(username, password)))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void findBeerByUpc_NoUser() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind.getUpc()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeerByUpc_RandomUser() throws Exception {
            mockMvc.perform(get("/api/v1/beerUpc/" + beerToFind.getUpc())
                    .with(httpBasic("random", "user")))
                    .andExpect(status().isUnauthorized());
        }
    }
}
