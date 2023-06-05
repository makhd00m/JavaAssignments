package com.scaler.blogapi;

import com.scaler.blogapi.security.jwt.JWTTokenService;
import com.scaler.blogapi.security.TokenService;
import com.scaler.blogapi.security.tokens.UserTokenRepository;
import com.scaler.blogapi.security.tokens.UserTokenService;
import com.scaler.blogapi.users.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogapiApplication.class, args);
    }

    public final static String TOKEN_SERVICE_TYPE = "JWT";  // "SST" or "JWT"

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public PasswordEncoder passwordEncoder() {
        /**
         * This is how we can migrate from Bcrypt to Argon2
         */
        var bcryptEncoder = new BCryptPasswordEncoder();
        var argon2Encoder = new Argon2PasswordEncoder(10, 32, 1, 8192, 3);

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return argon2Encoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                var passMatch = argon2Encoder.matches(rawPassword, encodedPassword);

                if(!passMatch) {
                    return bcryptEncoder.matches(rawPassword, encodedPassword);
                }
                return false;
            }
        };
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public TokenService tokenService(
            @Autowired UserTokenRepository userTokenRepository,
            @Autowired UsersRepository usersRepository
    ) {
        switch (TOKEN_SERVICE_TYPE) {
            case "SST":
                return new UserTokenService(userTokenRepository, usersRepository);
            case "JWT":
                return new JWTTokenService();
            default:
                throw new IllegalStateException("Unexpected value: " + TOKEN_SERVICE_TYPE);
        }
    }

}
