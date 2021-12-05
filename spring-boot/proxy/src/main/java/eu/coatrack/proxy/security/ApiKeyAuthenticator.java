package eu.coatrack.proxy.security;

/*-
 * #%L
 * coatrack-proxy
 * %%
 * Copyright (C) 2013 - 2020 Corizon | Institut für angewandte Systemtechnik Bremen GmbH (ATB)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import eu.coatrack.api.ApiKey;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.ConsumerAuthenticationCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 *  Checks if the API key token value sent by the client is valid. If so, an authentication object
 *  including the granted authorities is generated, to be further handled by access decision voters.
 *
 * @author gr-hovest, Christoph Baier
 */

@Service
@Slf4j
public class ApiKeyAuthenticator implements AuthenticationManager {

    private final Set<SimpleGrantedAuthority> authoritiesGrantedToCoatRackAdminApp = new HashSet<>();
    private final ConsumerAuthenticationCreator consumerAuthenticationCreator;

    public ApiKeyAuthenticator(ConsumerAuthenticationCreator consumerAuthenticationCreator) {
        this.consumerAuthenticationCreator = consumerAuthenticationCreator;

        authoritiesGrantedToCoatRackAdminApp.add(new SimpleGrantedAuthority(
                ServiceApiAccessRightsVoter.ACCESS_SERVICE_AUTHORITY_PREFIX + "refresh"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null)
            throw new BadCredentialsException("The authentication was null");

        try {
            String apiKeyValue = extractApiKeyValueFromAuthentication(authentication);
            return createAuthTokenIfApiKeyIsValid(apiKeyValue);
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            log.warn("The API keys validity could not be decided due to following error: ", e);
            return null;
        }
    }

    private String extractApiKeyValueFromAuthentication(Authentication authentication) {
        try{
            Assert.notNull(authentication.getCredentials(), "The credentials of " + authentication.getName()
                    + " were null.");
            Assert.isInstanceOf(String.class, authentication.getCredentials());
            String apiKeyValue = (String) authentication.getCredentials();
            Assert.hasText(apiKeyValue, "The credentials did not contain any letters.");
            return apiKeyValue;
        } catch (IllegalArgumentException e){
            throw new BadCredentialsException("The credentials of the authentication " + authentication.getName()
                    + " are not valid", e);
        }
    }

    private Authentication createAuthTokenIfApiKeyIsValid(String apiKeyValue) {
        if (doesApiKeyBelongToAdminApp(apiKeyValue))
            return createAdminAuthTokenFromApiKey(apiKeyValue);
        else
            return consumerAuthenticationCreator.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue);
    }

    private boolean doesApiKeyBelongToAdminApp(String apiKeyValue) {
        log.debug("Checking if API key with hashed value '{}' is an API key of the admin application.", sha256Hex(apiKeyValue));
        return apiKeyValue.equals(ApiKey.API_KEY_FOR_YGG_ADMIN_TO_ACCESS_PROXIES);
    }

    private Authentication createAdminAuthTokenFromApiKey(String apiKeyValue) {
        log.debug("Creating admin authentication token using API key with the hashed value {}.", sha256Hex(apiKeyValue));
        ApiKeyAuthToken apiKeyAuthTokenForValidApiKey = new ApiKeyAuthToken(apiKeyValue, authoritiesGrantedToCoatRackAdminApp);
        apiKeyAuthTokenForValidApiKey.setAuthenticated(true);
        return apiKeyAuthTokenForValidApiKey;
    }
}
