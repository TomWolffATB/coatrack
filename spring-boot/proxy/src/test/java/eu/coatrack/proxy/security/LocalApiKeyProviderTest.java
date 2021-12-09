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
import eu.coatrack.api.ServiceApi;
import eu.coatrack.proxy.security.authenticator.LocalApiKeyProvider;
import eu.coatrack.proxy.security.authenticator.LoggingRemoteApiKeyProviderProxy;
import eu.coatrack.proxy.security.authenticator.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static eu.coatrack.proxy.security.authenticator.GatewayMode.OFFLINE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocalApiKeyProviderTest {

    private final String apiKeyValue = "ee11ee22-ee33-ee44-ee55-ee66ee77ee88";
    private HashedApiKey hashedApiKey;
    private List<HashedApiKey> hashedApiKeyList;
    private LocalApiKeyProvider localApiKeyProvider;
    private LoggingRemoteApiKeyProviderProxy loggingRemoteApiKeyProviderProxyMock;

    @BeforeEach
    public void setupLocalApiKeyManagerWithoutInitializingLocalApiKeyList() {
        ApiKey apiKey = new ApiKey();
        apiKey.setKeyValue(apiKeyValue);
        apiKey.setServiceApi(new ServiceApi());
        apiKey.setValidUntil(new Date());
        apiKey.setDeletedWhen(new Date());

        hashedApiKeyList = new ArrayList<>();
        hashedApiKey = apiKey.convertToHashedApiKey();
        hashedApiKeyList.add(hashedApiKey);

        loggingRemoteApiKeyProviderProxyMock = mock(LoggingRemoteApiKeyProviderProxy.class);
        long timeInMinutesTheGatewayWorksWithoutConnectionToAdmin = 60;
        localApiKeyProvider = new LocalApiKeyProvider(loggingRemoteApiKeyProviderProxyMock,
                timeInMinutesTheGatewayWorksWithoutConnectionToAdmin);
    }

    @Test
    public void nonInitializedLocalApiKeyManagerShouldThrowSuchException() {
        assertThrows(LocalApiKeyListWasNotInitializedException.class, () -> localApiKeyProvider.getApiKeyEntityFromLocalCache(null));
    }

    @Test
    public void nullApiKeyValueShouldCauseException() {
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
        assertThrows(ApiKeyValueWasNullException.class, () -> localApiKeyProvider.getApiKeyEntityFromLocalCache(null));
    }

    @Test
    public void apiKeyIsFoundInLocalApiKeyListAndThereforeReturned() {
        initializeGatewayWithHashedApiKeyList();
        ApiKey apiKeyFoundInLocalList = localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue);

        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
        assertSame(hashedApiKey.serviceApi, apiKeyFoundInLocalList.getServiceApi());
        assertSame(hashedApiKey.validUntil, apiKeyFoundInLocalList.getValidUntil());
        assertSame(hashedApiKey.deletedWhen, apiKeyFoundInLocalList.getDeletedWhen());
    }

    private void initializeGatewayWithHashedApiKeyList() {
        when(loggingRemoteApiKeyProviderProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    @Test
    public void apiKeyIsNotFoundInLocalApiKeyListWhichCausesException() {
        hashedApiKeyList.clear();
        HashedApiKey wrongHashedApiKey = new HashedApiKey();
        wrongHashedApiKey.hashedKeyValue = "not apiKeyValue";
        hashedApiKeyList.add(wrongHashedApiKey);

        initializeGatewayWithHashedApiKeyList();
        assertThrows(ApiKeyNotFoundInLocalApiKeyListException.class, () -> localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

    @Test
    public void offlineWorkingTimeIsNotExceeded() {
        long deadlineIsOneMinuteAfterNow = 1;

        LocalApiKeyProvider localApiKeyProvider = new LocalApiKeyProvider(loggingRemoteApiKeyProviderProxyMock, deadlineIsOneMinuteAfterNow);
        when(loggingRemoteApiKeyProviderProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        ApiKey apiKeyFoundInLocalList = localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue);
        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
    }

    @Test
    public void offlineWorkingTimeIsExceededWhichCausesException() {
        long deadlineIsOneMinuteBeforeNow = -1;

        LocalApiKeyProvider localApiKeyProvider = new LocalApiKeyProvider(loggingRemoteApiKeyProviderProxyMock, deadlineIsOneMinuteBeforeNow);
        when(loggingRemoteApiKeyProviderProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertThrows(OfflineWorkingTimeExceedingException.class,
                () -> localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

    @Test
    public void apiKeyFetchingFailedExceptionShouldNotAffectLatestHashedApiKeyList() {
        initializedGatewayLosesConnectionToAdmin();
        ApiKey apiKeyFoundInLocalList = localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue);
        assertEquals(apiKeyValue, apiKeyFoundInLocalList.getKeyValue());
    }

    private void initializedGatewayLosesConnectionToAdmin() {
        when(loggingRemoteApiKeyProviderProxyMock.obtainHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        reset(loggingRemoteApiKeyProviderProxyMock);
        when(loggingRemoteApiKeyProviderProxyMock.obtainHashedApiKeyListFromAdmin()).thenThrow(ApiKeyFetchingFailedException.class);
        localApiKeyProvider.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    @Test
    public void apiKeyFetchingFailedExceptionShouldCauseSwitchToOfflineMode() {
        initializedGatewayLosesConnectionToAdmin();
        localApiKeyProvider.getApiKeyEntityFromLocalCache(apiKeyValue);
        verify(loggingRemoteApiKeyProviderProxyMock).updateGatewayMode(OFFLINE);
    }

}
