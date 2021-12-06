package eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider;

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
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.apiKeyFetcher.ApiKeyFetcher;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager.LocalApiKeyManager;
import eu.coatrack.proxy.security.exceptions.ApiKeyFetchingFailedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
@AllArgsConstructor
@Slf4j
public class ApiKeyProvider {

    private final ApiKeyFetcher apiKeyFetcher;
    private final LocalApiKeyManager localApiKeyManager;

    public ApiKey getApiKeyEntityByApiKeyValue(String apiKeyValue) {
        ApiKey apiKey;
        try {
            apiKey = apiKeyFetcher.requestApiKeyFromAdmin(apiKeyValue);
        } catch (ApiKeyFetchingFailedException e) {
            log.debug("Trying to verify consumers API key with the hash value {}, the connection to admin failed. " +
                    "Therefore checking the local API key list as fallback solution.", sha256Hex(apiKeyValue));
            apiKey = localApiKeyManager.getApiKeyEntityFromLocalCache(apiKeyValue);
        }
        return apiKey;
    }

}
