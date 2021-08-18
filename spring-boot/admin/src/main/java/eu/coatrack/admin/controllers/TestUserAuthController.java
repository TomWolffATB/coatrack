package eu.coatrack.admin.controllers;

import eu.coatrack.admin.model.repository.UserRepository;
import eu.coatrack.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class TestUserAuthController {

    private static final Logger logger = LoggerFactory.getLogger(TestUserAuthController.class);

    @Autowired
    UserDetailsService userDetailsService;

    //localhost:8080/test-user-login?testUserId=verySecretId

    @GetMapping("/test-user-login")
    public String testUserLogin(@RequestParam String testUserId){
        logger.debug("Test User with the id {} requests authentication", testUserId);

        if (testUserId.equals("verySecretId")) {
            logger.debug("Test User login was successful!");

            UserDetails principal = userDetailsService.loadUserByUsername("ChristophBaierATB");

            Authentication authentication = new TestingAuthenticationToken(principal, principal.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            authentication.setAuthenticated(true);

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);

            return "redirect:/admin";
        }
        else {
            logger.debug("Test User login was not successful...");
            return "/index";
        }
    }
}
