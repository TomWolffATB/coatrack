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
import eu.coatrack.api.HashedApiKey;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.mockito.Mockito.mock;

public abstract class LocalApiKeyManager_AbstractTestSetupProvider {

    protected ApiKey apiKey;
    protected List<HashedApiKey> hashedApiKeyList;
    protected ApiKeyFetcher apiKeyFetcherMock;
    protected LocalApiKeyManager localApiKeyManager;

    public void setupLocalApiKeyManagerWithoutInitializingLocalApiKeyList() {
        apiKey = new ApiKey();
        apiKey.setKeyValue("ee11ee22-ee33-ee44-ee55-ee66ee77ee88");

        hashedApiKeyList = new ArrayList<>();
        HashedApiKey hashedApiKey = apiKey.convertToHashedApiKey();
        hashedApiKeyList.add(hashedApiKey);

        apiKeyFetcherMock = mock(ApiKeyFetcher.class);
        long timeInMinutesTheGatewayWorksWithoutConnectionToAdmin = 60;
        localApiKeyManager = new LocalApiKeyManager(apiKeyFetcherMock, timeInMinutesTheGatewayWorksWithoutConnectionToAdmin);
    }

}
