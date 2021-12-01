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
import eu.coatrack.proxy.security.exceptions.ApiKeyFetchingFailedException;
import eu.coatrack.proxy.security.exceptions.ApiKeyNotFoundInLocalApiKeyListException;
import eu.coatrack.proxy.security.exceptions.ApiKeyValueWasNullException;
import eu.coatrack.proxy.security.exceptions.OfflineWorkingTimeExceedingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocalApiKeyManager_ApiKeyFindingInLocalApiKeyListTest extends LocalApiKeyManager_AbstractTestSetupProvider {

    @BeforeEach
    public void fillLocalApiKeyListWithListContainingValidApiKey() throws ApiKeyFetchingFailedException {
        super.setupLocalApiKeyManagerWithoutInitializingLocalApiKeyList();
        when(apiKeyFetcherMock.requestLatestHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    @Test
    public void nullApiKeyValueShouldCauseException() {
        assertThrows(ApiKeyValueWasNullException.class, () -> localApiKeyManager.getApiKeyEntityFromLocalCache(null));
    }

    @Test
    public void apiKeyIsFoundInLocalApiKeyListAndThereforeReturned() {
        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKey.getKeyValue());
        assertEquals(apiKey.getKeyValue(), apiKeyFoundInLocalList.getKeyValue());
    }

    @Test
    public void apiKeyIsNotFoundInLocalApiKeyListWhichCausesException() {
        List<HashedApiKey> apiKeyListNotContainingTheIncomingApiKey = createApiKeyListNotContainingTheIncomingApiKey();

        reset(apiKeyFetcherMock);
        when(apiKeyFetcherMock.requestLatestHashedApiKeyListFromAdmin()).thenReturn(apiKeyListNotContainingTheIncomingApiKey);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertThrows(ApiKeyNotFoundInLocalApiKeyListException.class, () -> localApiKeyManager.getApiKeyEntityFromLocalCache(apiKey.getKeyValue()));
    }

    private List<HashedApiKey> createApiKeyListNotContainingTheIncomingApiKey() {
        List<HashedApiKey> listWithoutTheIncomingApiKey = new ArrayList<>();

        HashedApiKey wrongApiKey1 = new HashedApiKey();
        wrongApiKey1.hashedKeyValue = "not matching value 1";
        listWithoutTheIncomingApiKey.add(wrongApiKey1);
        HashedApiKey wrongApiKey2 = new HashedApiKey();
        wrongApiKey2.hashedKeyValue = "not matching value 2";
        listWithoutTheIncomingApiKey.add(wrongApiKey2);

        return listWithoutTheIncomingApiKey;
    }

    @Test
    public void localApiKeyListShouldNotUpdateWhenApiKeyFetcherDeliversNull() {
        reset(apiKeyFetcherMock);
        when(apiKeyFetcherMock.requestLatestHashedApiKeyListFromAdmin()).thenReturn(null);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKey.getKeyValue());
        assertEquals(apiKey.getKeyValue(), apiKeyFoundInLocalList.getKeyValue());
    }

    @Test
    public void offlineWorkingTimeIsNotExceeded() {
        long deadlineIsOneMinuteAfterNow = 1;

        LocalApiKeyManager localApiKeyManager = new LocalApiKeyManager(apiKeyFetcherMock, deadlineIsOneMinuteAfterNow);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKey.getKeyValue());
        assertEquals(apiKey.getKeyValue(), apiKeyFoundInLocalList.getKeyValue());
    }

    @Test
    public void offlineWorkingTimeIsExceededWhichCausesException() {
        long deadlineIsOneMinuteBeforeNow = -1;

        LocalApiKeyManager localApiKeyManager = new LocalApiKeyManager(apiKeyFetcherMock, deadlineIsOneMinuteBeforeNow);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertThrows(OfflineWorkingTimeExceedingException.class,
                () -> localApiKeyManager.getApiKeyEntityFromLocalCache(apiKey.getKeyValue()));
    }
}
