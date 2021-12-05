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

import eu.coatrack.api.HashedApiKey;
import eu.coatrack.proxy.security.exceptions.ApiKeyFetchingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static eu.coatrack.proxy.security.GatewayMode.OFFLINE;
import static eu.coatrack.proxy.security.GatewayMode.ONLINE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LocalApiKeyUpdaterTest {

    @Mock private ApiKeyFetcher apiKeyFetcher;
    @Mock private LocalApiKeyManager localApiKeyManager;
    @InjectMocks private LocalApiKeyManagerUpdater localApiKeyManagerUpdater;

    private List<HashedApiKey> hashedApiKeyList;

    @BeforeEach
    public void init() {
        hashedApiKeyList = new ArrayList<>();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void dontPerformAnyGatewayModeSwitchesAndExpectNoSwitchingLogMessages() {
        assertEquals(OFFLINE, localApiKeyManagerUpdater.getCurrentGatewayMode());
    }

    @Test
    public void switchingToOfflineFromOnlineMode() {
        localApiKeyManagerUpdater.updateGatewayMode(ONLINE);
        localApiKeyManagerUpdater.updateGatewayMode(OFFLINE);
        assertEquals(OFFLINE, localApiKeyManagerUpdater.getCurrentGatewayMode());
    }

    @Test
    public void switchingToOfflineTwoTimes() {
        localApiKeyManagerUpdater.updateGatewayMode(OFFLINE);
        localApiKeyManagerUpdater.updateGatewayMode(OFFLINE);
        assertEquals(OFFLINE, localApiKeyManagerUpdater.getCurrentGatewayMode());
    }

    @Test
    public void fetchingApiKeyListShouldCauseSwitchToOnlineMode() {
        when(apiKeyFetcher.requestLatestHashedApiKeyListFromAdmin()).thenReturn(hashedApiKeyList);

        localApiKeyManagerUpdater.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertEquals(ONLINE, localApiKeyManagerUpdater.getCurrentGatewayMode());
        verify(localApiKeyManager).updateLocalHashedApiKeyListWith(hashedApiKeyList);
    }

    @Test
    public void apiKeyListFetchingFailedExceptionShouldCauseSwitchingToOfflineMode() {
        localApiKeyManagerUpdater.updateGatewayMode(ONLINE);
        when(apiKeyFetcher.requestLatestHashedApiKeyListFromAdmin()).thenThrow(ApiKeyFetchingFailedException.class);

        localApiKeyManagerUpdater.refreshLocalApiKeyCacheWithApiKeysFromAdmin();

        assertEquals(OFFLINE, localApiKeyManagerUpdater.getCurrentGatewayMode());
        verify(localApiKeyManager, times(0)).updateLocalHashedApiKeyListWith(hashedApiKeyList);
    }

}
