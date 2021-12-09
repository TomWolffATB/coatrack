package eu.coatrack.proxy.security;/*-
 * #%L
 * coatrack-proxy
 * %%
 * Copyright (C) 2013 - 2021 Corizon | Institut für angewandte Systemtechnik Bremen GmbH (ATB)
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
import eu.coatrack.proxy.security.authenticator.ApiKeyAuthenticator;
import eu.coatrack.proxy.security.authenticator.ServiceConsumerAuthenticationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiKeyAuthenticatorTest {

    @Mock private ServiceConsumerAuthenticationCreator serviceConsumerAuthenticationCreatorMock;
    @InjectMocks private ApiKeyAuthenticator apiKeyAuthenticator;

    private ApiKeyAuthToken apiKeyAuthToken;
    private final String apiKeyValue = "ee11ee22-ee33-ee44-ee55-ee66ee77ee88";

    @BeforeEach
    public void setup() {
        // Create an auth token for a valid api key without any granted authorities.
        apiKeyAuthToken = new ApiKeyAuthToken(apiKeyValue, null);
        apiKeyAuthToken.setAuthenticated(false);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validAuthenticationShouldBeAccepted() {
        apiKeyAuthToken.setAuthenticated(true);
        when(serviceConsumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue)).thenReturn(apiKeyAuthToken);

        Authentication resultAuthentication = apiKeyAuthenticator.authenticate(apiKeyAuthToken);
        assertTrue(resultAuthentication.isAuthenticated());
    }


    @Test
    public void nullArgumentShouldCauseException() {
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(null));
    }

    @Test
    public void nullCredentialsInAuthTokenShouldCauseException() {
        ApiKeyAuthToken nullToken = new ApiKeyAuthToken(null, null);
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(nullToken));
    }

    @Test
    public void nullAuthenticationShouldThrowBadCredentialsException() {
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(null));
    }


    @Test
    public void authenticationWithEmptyCredentialsShouldThrowBadCredentialsException() {
        apiKeyAuthToken = new ApiKeyAuthToken("", null);
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(null));
    }

    @Test
    public void tokenWithAdminsAccessKeyShouldBeAccepted() {
        apiKeyAuthToken = new ApiKeyAuthToken(ApiKey.API_KEY_FOR_YGG_ADMIN_TO_ACCESS_PROXIES, null);
        apiKeyAuthToken.setAuthenticated(false);
        assertTrue(apiKeyAuthenticator.authenticate(apiKeyAuthToken).isAuthenticated());
    }

    @Test
    public void invalidApiKeyShouldCauseBadCredentialsException() {
        when(serviceConsumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue)).thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(apiKeyAuthToken));
    }

    @Test
    public void undecidableApiKeyValidityShouldReturnNull() {
        when(serviceConsumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue)).thenThrow(RuntimeException.class);
        assertNull(apiKeyAuthenticator.authenticate(apiKeyAuthToken));
    }
}
