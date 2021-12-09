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
import eu.coatrack.proxy.security.authenticator.RemoteApiKeyProvider;
import eu.coatrack.proxy.security.authenticator.ApiKeyProvider;
import eu.coatrack.proxy.security.authenticator.LocalApiKeyProvider;
import eu.coatrack.proxy.security.authenticator.exceptions.ApiKeyFetchingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ApiKeyProviderTests {

    @Mock private RemoteApiKeyProvider remoteApiKeyProviderMock;
    @Mock private LocalApiKeyProvider localApiKeyProviderMock;
    @InjectMocks private ApiKeyProvider apiKeyProvider;

    private final String apiKeyValue = "some API key value";
    private ApiKey apiKeyToBeReturned;

    @BeforeEach
    public void init() {
        apiKeyToBeReturned = new ApiKey();
        apiKeyToBeReturned.setKeyValue(apiKeyValue);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void apiFetchedFromAdminShouldBeReturned() {
        when(remoteApiKeyProviderMock.requestApiKeyFromAdmin(apiKeyValue)).thenReturn(apiKeyToBeReturned);
        assertSame(apiKeyToBeReturned, apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue));
    }

    @Test
    public void whenFetchingFromAdminFails_LocalApiKeyShouldBeProvided() {
        when(remoteApiKeyProviderMock.requestApiKeyFromAdmin(apiKeyValue)).thenThrow(ApiKeyFetchingFailedException.class);
        when(localApiKeyProviderMock.getApiKeyEntityFromLocalCache(apiKeyValue)).thenReturn(apiKeyToBeReturned);

        assertSame(apiKeyToBeReturned, apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue));
    }

    @Test
    public void whenAlsoLocalSearchFails_TheExceptionShouldBeRedirected() {
        when(remoteApiKeyProviderMock.requestApiKeyFromAdmin(apiKeyValue)).thenThrow(ApiKeyFetchingFailedException.class);
        when(localApiKeyProviderMock.getApiKeyEntityFromLocalCache(apiKeyValue)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> localApiKeyProviderMock.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

}
