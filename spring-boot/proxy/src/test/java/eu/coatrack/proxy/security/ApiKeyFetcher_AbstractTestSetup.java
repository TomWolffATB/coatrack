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
import eu.coatrack.api.HashedApiKey;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.apiKeyFetcher.ApiKeyFetcher;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.apiKeyFetcher.UrlResourcesProvider;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public abstract class ApiKeyFetcher_AbstractTestSetup {

    @Mock protected UrlResourcesProvider urlResourcesProviderMock;
    @Mock protected RestTemplate restTemplateMock;
    @InjectMocks protected ApiKeyFetcher apiKeyFetcher;

    protected static final String someApiKeyValue = "ee11ee22-ee33-ee44-ee55-ee66ee77ee88";

    protected ApiKey apiKey;
    protected HashedApiKey hashedApiKey;
    protected HashedApiKey[] hashedApiKeys;
    protected List<HashedApiKey> apiKeyList;

    @BeforeEach
    public void setup() {
        apiKey = new ApiKey();
        apiKey.setKeyValue(someApiKeyValue);

        hashedApiKey = apiKey.convertToHashedApiKey();

        hashedApiKeys = new HashedApiKey[1];
        hashedApiKeys[0] = hashedApiKey;
        apiKeyList = Arrays.asList(hashedApiKeys);

        MockitoAnnotations.openMocks(this);
        when(urlResourcesProviderMock.getHashedApiKeyListRequestUrl()).thenReturn("test");
        when(urlResourcesProviderMock.getApiKeyRequestUrl(anyString())).thenReturn("test");
    }

}