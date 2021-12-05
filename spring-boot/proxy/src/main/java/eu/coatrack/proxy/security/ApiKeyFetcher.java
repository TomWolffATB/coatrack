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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 * Sends requests to the Coatrack admin server to receive single
 * API keys or a list of API keys for all services offered by this gateway.
 *
 * @author Christoph Baier
 */

@Service
@Slf4j
@AllArgsConstructor
public class ApiKeyFetcher {

    private final UrlResourcesProvider urlResourcesProvider;
    private final RestTemplate restTemplate;

    public List<HashedApiKey> requestLatestHashedApiKeyListFromAdmin() {
        log.debug("Requesting latest API key list from from " + urlResourcesProvider.getHashedApiKeyListRequestUrl());

        try {
            ResponseEntity<HashedApiKey[]> responseEntity = restTemplate.getForEntity(
                    urlResourcesProvider.getHashedApiKeyListRequestUrl(), HashedApiKey[].class);
            HashedApiKey[] hashedApiKeys = (HashedApiKey[]) extractBodyFromResponseEntity(responseEntity);
            return new ArrayList<>(Arrays.asList(hashedApiKeys));
        } catch (RestClientException e) {
            throw new ApiKeyFetchingFailedException("Trying to request the latest API key list from Admin, the " +
                    "connection failed.", e);
        }
    }

    private Object extractBodyFromResponseEntity(ResponseEntity<?> responseEntity) {
        Optional<String> errorMessage = validateResponseEntityAndCreateErrorMessageInCaseOfProblems(responseEntity);
        if (errorMessage.isPresent())
            throw new ApiKeyFetchingFailedException("A problem occurred referring to the ResponseEntity. "
                    + errorMessage.get());
        else
            return responseEntity.getBody();
    }

    private Optional<String> validateResponseEntityAndCreateErrorMessageInCaseOfProblems(ResponseEntity<?> responseEntity) {
        Optional<String> errorMessage = Optional.empty();
        if (responseEntity == null) {
            errorMessage = Optional.of("The ResponseEntity was null.");
        } else if (responseEntity.getBody() == null) {
            errorMessage = Optional.of("The body was null");
        } else if (responseEntity.getStatusCode() != HttpStatus.OK) {
            errorMessage = Optional.of("The HTTP status was not OK.");
        }
        return errorMessage;
    }

    public ApiKey requestApiKeyFromAdmin(String apiKeyValue) {
        log.debug("Requesting API key with the hashed value {} from CoatRack admin.", sha256Hex(apiKeyValue));

        try {
            ResponseEntity<ApiKey> responseEntity = restTemplate.getForEntity(
                    urlResourcesProvider.getApiKeyRequestUrl(apiKeyValue), ApiKey.class);
            return (ApiKey) extractBodyFromResponseEntity(responseEntity);
        } catch (RestClientException e) {
            throw new ApiKeyFetchingFailedException("Trying to request the API key with the hashed value " +
                    sha256Hex(apiKeyValue) + " from CoatRack admin, the connection failed.", e);
        }
    }
}
