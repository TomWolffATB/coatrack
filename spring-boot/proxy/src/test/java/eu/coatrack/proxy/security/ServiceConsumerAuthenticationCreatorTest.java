package eu.coatrack.proxy.security;

/*-
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
import eu.coatrack.proxy.security.authenticator.ApiKeyProvider;
import eu.coatrack.proxy.security.authenticator.ApiKeyValidator;
import eu.coatrack.proxy.security.authenticator.ServiceConsumerAuthenticationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ServiceConsumerAuthenticationCreatorTest {

    @Mock
    ApiKeyProvider apiKeyProvider;
    @Mock
    ApiKeyValidator apiKeyValidator;
    @InjectMocks
    ServiceConsumerAuthenticationCreator serviceConsumerAuthenticationCreator;

    private final String apiKeyValue = "some API key value";
    private final String serviceUriIdentifier = "some uriIdentifier";
    private ApiKey apiKey;

    @BeforeEach
    public void init() {
        ServiceApi serviceApi = new ServiceApi();
        serviceApi.setUriIdentifier(serviceUriIdentifier);
        apiKey = new ApiKey();
        apiKey.setKeyValue(apiKeyValue);
        apiKey.setServiceApi(serviceApi);

        MockitoAnnotations.openMocks(this);
        when(apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue)).thenReturn(apiKey);
    }

    @Test
    public void validKeyShouldReturnedValidAuthentication() {
        when(apiKeyValidator.isApiKeyValid(apiKey)).thenReturn(true);
        Authentication consumerAuthentication = serviceConsumerAuthenticationCreator.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue);

        assertTrue(consumerAuthentication.isAuthenticated());
        assertEquals(consumerAuthentication.getCredentials(), apiKeyValue);

        String expectedAuthority = ServiceApiAccessRightsVoter.ACCESS_SERVICE_AUTHORITY_PREFIX + serviceUriIdentifier;
        String actualAuthority = consumerAuthentication.getAuthorities().stream().findFirst().get().getAuthority();
        assertEquals(expectedAuthority, actualAuthority);
    }

    @Test
    public void invalidKeyShouldCauseBadCredentialsException() {
        when(apiKeyValidator.isApiKeyValid(apiKey)).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> serviceConsumerAuthenticationCreator.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue));
    }

}
