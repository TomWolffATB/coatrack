package eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager;

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
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.apiKeyFetcher.ApiKeyFetcher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@EnableAsync
@AllArgsConstructor
public class LoggingApiKeyFetcherProxy {

    public final static String switchingToOfflineModeMessage = "Gateway is switching to offline mode.";
    public final static String switchingToOnlineModeMessage = "Gateway is switching to online mode.";

    private static GatewayMode currentGatewayMode = GatewayMode.OFFLINE;

    private final ApiKeyFetcher apiKeyFetcher;

    public List<HashedApiKey> obtainHashedApiKeyListFromAdmin() {
        log.debug("Trying to update the local API key list by contacting CoatRack admin.");

        try {
            List<HashedApiKey> fetchedHashedApiKeyList = apiKeyFetcher.requestLatestHashedApiKeyListFromAdmin();
            Assert.notNull(fetchedHashedApiKeyList, "The local API key list will not be " +
                    "updated since the fetched API key list was null.");
            updateGatewayMode(GatewayMode.ONLINE);
            return fetchedHashedApiKeyList;
        } catch (Exception e) {
            updateGatewayMode(GatewayMode.OFFLINE);
            throw e;
        }
    }

    public void updateGatewayMode(GatewayMode newGatewayMode) {
        if (currentGatewayMode != newGatewayMode) {
            log.info(newGatewayMode == GatewayMode.ONLINE ? switchingToOnlineModeMessage : switchingToOfflineModeMessage);
            currentGatewayMode = newGatewayMode;
        }
    }

    public GatewayMode getCurrentGatewayMode() {
        return currentGatewayMode;
    }

}
