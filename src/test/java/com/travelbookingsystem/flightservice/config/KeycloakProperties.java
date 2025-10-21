package com.travelbookingsystem.flightservice.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    Client client;
    List<User> users;

    @Getter
    @Setter
    public static class Client {

        String id;

        String secret;

    }

    @Getter
    @Setter
    public static class User {

        String username;

        String password;

        String role;

    }

    public User findUserByUsername(String username) {
        return users.stream().filter(u -> u.username.equals(username))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("User not found by username - %s !".formatted(username)));
    }

}
