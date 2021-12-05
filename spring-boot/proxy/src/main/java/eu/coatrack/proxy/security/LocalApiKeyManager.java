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
import eu.coatrack.proxy.security.exceptions.ApiKeyNotFoundInLocalApiKeyListException;
import eu.coatrack.proxy.security.exceptions.ApiKeyValueWasNullException;
import eu.coatrack.proxy.security.exceptions.LocalApiKeyListWasNotInitializedException;
import eu.coatrack.proxy.security.exceptions.OfflineWorkingTimeExceedingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 * Provides a local cache for API keys, allowing the gateway to validate API keys without connection to CoatRack
 * admin application. Caches (and periodically refreshes) a list of all API keys assigned to all services offered
 * by the gateway.
 *
 * @author Christoph Baier
 */

@Slf4j
@Service
public class LocalApiKeyManager {

    private List<HashedApiKey> localHashedApiKeyList;
    private LocalDateTime deadlineWhenOfflineModeShallStopWorking;

    private final long numberOfMinutesTheGatewayShallWorkInOfflineMode;
    private boolean isLocalApiKeyListInitialized = false;

    public LocalApiKeyManager(@Value("${number-of-minutes-the-gateway-shall-work-in-offline-mode}") long minutesInOfflineMode) {
        this.numberOfMinutesTheGatewayShallWorkInOfflineMode = minutesInOfflineMode;
    }

    public ApiKey getApiKeyEntityFromLocalCache(String apiKeyValue) {
        //This is only a fallback solution if connection to admin does not work. Therefore offline mode is triggered.
        //TODO updateGatewayMode(GatewayMode.OFFLINE); some class should implement this logic.

        if (!isLocalApiKeyListInitialized) {
            throw new LocalApiKeyListWasNotInitializedException("The gateway is currently not able to validate " +
                    "API keys in offline mode, as the local API cache was not yet initialized. This probably " +
                    "means that a network connection to CoatRack admin could not yet be established.");
        } else if (isOfflineWorkingTimeExceeded()) {
            throw new OfflineWorkingTimeExceedingException("The predefined time for working in offline mode is exceeded. The " +
                    "gateway will reject every request until a connection to CoatRack admin could be re-established.");
        } else {
            return extractApiKeyFromLocalApiKeyList(apiKeyValue);
        }
    }

    private boolean isOfflineWorkingTimeExceeded() {
        return LocalDateTime.now().isAfter(deadlineWhenOfflineModeShallStopWorking);
    }

    private ApiKey extractApiKeyFromLocalApiKeyList(String apiKeyValue) {
        if (apiKeyValue == null)
            throw new ApiKeyValueWasNullException("The API key could not be found in the local API key list " +
                    "because its value was null.");

        log.debug("Trying to extract the API key with the hashed value {} from the local list.", sha256Hex(apiKeyValue));

        String hashedApiKeyValue = sha256Hex(apiKeyValue);
        Optional<HashedApiKey> optionalApiKey = localHashedApiKeyList.stream().filter(
                apiKeyFromLocalList -> apiKeyFromLocalList.hashedKeyValue.equals(hashedApiKeyValue)
        ).findFirst();

        if (optionalApiKey.isPresent()) {
            return recreateApiKey(optionalApiKey.get(), apiKeyValue);
        } else {
            throw new ApiKeyNotFoundInLocalApiKeyListException("The API key with the hashed value "
                    + sha256Hex(apiKeyValue) + " could not be found in the local API key list.");
        }
    }

    private ApiKey recreateApiKey(HashedApiKey hashedApiKey, String apiKeyValue) {;
        ApiKey apiKey = new ApiKey();
        apiKey.setKeyValue(apiKeyValue);
        apiKey.setServiceApi(hashedApiKey.serviceApi);
        apiKey.setValidUntil(hashedApiKey.validUntil);
        apiKey.setDeletedWhen(hashedApiKey.deletedWhen);
        return apiKey;
    }

    public void updateLocalHashedApiKeyListWith(List<HashedApiKey> newLocalHashedApiKeyList) {
        localHashedApiKeyList = newLocalHashedApiKeyList;
        deadlineWhenOfflineModeShallStopWorking = LocalDateTime.now()
                .plusMinutes(numberOfMinutesTheGatewayShallWorkInOfflineMode);
        if(!isLocalApiKeyListInitialized)
            isLocalApiKeyListInitialized = true;
    }
}
