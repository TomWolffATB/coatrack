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
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager.LocalApiKeyManager;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager.LoggingApiKeyFetcherProxy;
import eu.coatrack.proxy.security.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager.GatewayMode.OFFLINE;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocalApiKeyManagerTest {

    private final String apiKeyValue = "ee11ee22-ee33-ee44-ee55-ee66ee77ee88";
    private HashedApiKey hashedApiKey;
    private List<HashedApiKey> hashedApiKeyList;
    private LocalApiKeyManager localApiKeyManager;
    private LoggingApiKeyFetcherProxy loggingApiKeyFetcherProxyMock;

    @BeforeEach
    public void setupLocalApiKeyManagerWithoutInitializingLocalApiKeyList() {
        ApiKey apiKey = new ApiKey();
        apiKey.setKeyValue(apiKeyValue);

        hashedApiKeyList = new ArrayList<>();
        hashedApiKey = apiKey.convertToHashedApiKey();
        hashedApiKeyList.add(hashedApiKey);

        loggingApiKeyFetcherProxyMock = mock(LoggingApiKeyFetcherProxy.class);
        long timeInMinutesTheGatewayWorksWithoutConnectionToAdmin = 60;
        localApiKeyManager = new LocalApiKeyManager(loggingApiKeyFetcherProxyMock,
                timeInMinutesTheGatewayWorksWithoutConnectionToAdmin);
    }

    @Test
    public void nonInitializedLocalApiKeyManagerShouldThrowSuchException() {
        assertThrows(LocalApiKeyListWasNotInitializedException.class, () -> localApiKeyManager.getApiKeyEntityFromLocalCache(null));
    }

    @Test
    public void nullApiKeyValueShouldCauseException() {
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
        assertThrows(ApiKeyValueWasNullException.class, () -> localApiKeyManager.getApiKeyEntityFromLocalCache(null));
    }

    @Test
    public void apiKeyIsFoundInLocalApiKeyListAndThereforeReturned() {
        initializeGatewayWithHashedApiKeyList();
        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue);

        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
        assertSame(hashedApiKey.serviceApi, apiKeyFoundInLocalList.getServiceApi());
        assertSame(hashedApiKey.validUntil, apiKeyFoundInLocalList.getValidUntil());
        assertSame(hashedApiKey.deletedWhen, apiKeyFoundInLocalList.getDeletedWhen());
    }

    private void initializeGatewayWithHashedApiKeyList() {
        when(loggingApiKeyFetcherProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    @Test
    public void apiKeyIsNotFoundInLocalApiKeyListWhichCausesException() {
        hashedApiKeyList.clear();
        HashedApiKey wrongHashedApiKey = new HashedApiKey();
        wrongHashedApiKey.hashedKeyValue = "not apiKeyValue";
        hashedApiKeyList.add(wrongHashedApiKey);

        initializeGatewayWithHashedApiKeyList();
        assertThrows(ApiKeyNotFoundInLocalApiKeyListException.class, () -> localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

    @Test
    public void offlineWorkingTimeIsNotExceeded() {
        long deadlineIsOneMinuteAfterNow = 1;

        LocalApiKeyManager localApiKeyManager = new LocalApiKeyManager(loggingApiKeyFetcherProxyMock, deadlineIsOneMinuteAfterNow);
        when(loggingApiKeyFetcherProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue);
        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
    }

    @Test
    public void offlineWorkingTimeIsExceededWhichCausesException() {
        long deadlineIsOneMinuteBeforeNow = -1;

        LocalApiKeyManager localApiKeyManager = new LocalApiKeyManager(loggingApiKeyFetcherProxyMock, deadlineIsOneMinuteBeforeNow);
        when(loggingApiKeyFetcherProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertThrows(OfflineWorkingTimeExceedingException.class,
                () -> localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

    @Test
    public void apiKeyFetchingFailedExceptionShouldNotAffectLatestHashedApiKeyList() {
        initializedGatewayLosesConnectionToAdmin();
        ApiKey apiKeyFoundInLocalList = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue);
        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
    }

    private void initializedGatewayLosesConnectionToAdmin() {
        when(loggingApiKeyFetcherProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        reset(loggingApiKeyFetcherProxyMock);
        when(loggingApiKeyFetcherProxyMock.obtainHashedApiKeyListFromAdmin()).thenThrow(ApiKeyFetchingFailedException.class);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    @Test
    public void apiKeyFetchingFailedExceptionShouldCauseSwitchToOfflineMode() {
        initializedGatewayLosesConnectionToAdmin();
        localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue);
        verify(loggingApiKeyFetcherProxyMock).updateGatewayMode(OFFLINE);
    }

}
