package id.co.mii.mockup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class AuthController {

    @GetMapping("/hidden-basic-auth/{username}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public String hiddenBasicAuth(
            @PathVariable String username,
            @PathVariable String password,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Decode the Basic Auth header
        String decodedAuth = new String(Base64.getDecoder().decode(authorizationHeader.split(" ")[1]));
        String[] authParts = decodedAuth.split(":");
        String authUsername = authParts[0];
        String authPassword = authParts[1];

        // Check if the provided username and password match the path variables
        if (username.equals(authUsername) && password.equals(authPassword)) {
            return "Authenticated as " + username;
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
