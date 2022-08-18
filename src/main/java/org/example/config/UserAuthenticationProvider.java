package org.example.config;

import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Optional;

@Component
@EnableWebSecurity
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();;
        char[] password = authentication.getCredentials().toString().toCharArray();

        Optional<User> user = userRepository.findByUsername(username);

        try{
            if(user.isPresent() && passwordEncoder.matches(String.valueOf(password), user.get().getPassword())){
                return new UsernamePasswordAuthenticationToken(username, String.valueOf(password));
            }
            else return null;
        }
        finally {
            Arrays.fill(password, '0');
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
