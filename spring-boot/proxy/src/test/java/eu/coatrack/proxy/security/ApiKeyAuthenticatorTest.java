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
import eu.coatrack.api.ServiceApi;
import eu.coatrack.proxy.security.exceptions.LocalApiKeyListWasNotInitializedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiKeyAuthenticatorTest {

    private ApiKeyAuthToken apiKeyAuthToken;
    private ApiKeyAuthenticator apiKeyAuthenticator;
    private ApiKey apiKey;
    private ConsumerAuthenticationCreator consumerAuthenticationCreatorMock;

    @BeforeEach
    public void setup() {
        apiKey = createSampleApiKeyForTesting();

        // Create an auth token for a valid api key without any granted authorities.
        apiKeyAuthToken = new ApiKeyAuthToken(apiKey.getKeyValue(), null);
        apiKeyAuthToken.setAuthenticated(false);

        consumerAuthenticationCreatorMock = mock(ConsumerAuthenticationCreator.class);
        apiKeyAuthenticator = new ApiKeyAuthenticator(consumerAuthenticationCreatorMock);
    }

    private ApiKey createSampleApiKeyForTesting() {
        ServiceApi serviceApi = new ServiceApi();
        serviceApi.setUriIdentifier("weather-data-service");

        ApiKey localApiKey = new ApiKey();
        localApiKey.setKeyValue("ee11ee22-ee33-ee44-ee55-ee66ee77ee88");
        localApiKey.setServiceApi(serviceApi);

        return localApiKey;
    }

    @Test
    public void validAuthenticationShouldBeAccepted() {
        apiKeyAuthToken.setAuthenticated(true);
        when(consumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKey.getKeyValue())).thenReturn(apiKeyAuthToken);

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
        when(consumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKey.getKeyValue())).thenThrow(BadCredentialsException.class);
        assertThrows(BadCredentialsException.class, () -> apiKeyAuthenticator.authenticate(apiKeyAuthToken));
    }

    @Test
    public void undecidableApiKeyValidityShouldReturnNull() {
        when(consumerAuthenticationCreatorMock.createConsumerAuthTokenIfApiKeyIsValid(apiKey.getKeyValue())).thenThrow(RuntimeException.class);
        assertNull(apiKeyAuthenticator.authenticate(apiKeyAuthToken));
    }
}
