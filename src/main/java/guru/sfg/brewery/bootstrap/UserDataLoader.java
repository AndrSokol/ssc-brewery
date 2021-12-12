package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.AuthorityEnum;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0){
            loadSecurityData();
        }
    }

    private void loadSecurityData() {
        Authority adminAuthority = authorityRepository
                .save(Authority.builder().role(AuthorityEnum.ADMIN.name()).build());
        Authority userAuthority = authorityRepository
                .save(Authority.builder().role(AuthorityEnum.USER.name()).build());
        Authority customerAuthority = authorityRepository
                .save(Authority.builder().role(AuthorityEnum.CUSTOMER.name()).build());

        User springGuruAdmin = User.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .authority(adminAuthority)
                .build();

        User userPasswordUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(userAuthority)
                .build();

        User scottTigerCustomer = User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(customerAuthority)
                .build();

        userRepository.saveAll(List.of(scottTigerCustomer, springGuruAdmin, userPasswordUser));

        log.debug("Users loaded: " + userRepository.count());
    }
}
