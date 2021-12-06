package eu.coatrack.proxy.security.consumerAuthenticationProvider;

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
import eu.coatrack.proxy.security.ApiKeyAuthToken;
import eu.coatrack.proxy.security.ServiceApiAccessRightsVoter;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.ApiKeyProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Service
@Slf4j
@AllArgsConstructor
public class ConsumerAuthenticationCreator {

    private final ApiKeyProvider apiKeyProvider;
    private final ApiKeyValidator apiKeyValidator;

    public Authentication createConsumerAuthTokenIfApiKeyIsValid(String apiKeyValue) {
        log.debug("Verifying the API with the hashed value {} from consumer.", sha256Hex(apiKeyValue));

        ApiKey apiKey = apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue);
        if (apiKeyValidator.isApiKeyValid(apiKey))
            return createAuthTokenGrantingAccessToServiceApi(apiKey);
        else
            throw new BadCredentialsException("The API key with the hashed value " + sha256Hex(apiKeyValue) + " is not valid.");
    }

    private ApiKeyAuthToken createAuthTokenGrantingAccessToServiceApi(ApiKey apiKey) {
        log.debug("Create consumers authentication token using API key with the hash value {}.", sha256Hex(apiKey.getKeyValue()));

        String serviceUriIdentifier = apiKey.getServiceApi().getUriIdentifier();
        ApiKeyAuthToken apiKeyAuthToken = new ApiKeyAuthToken(apiKey.getKeyValue(), Collections.singleton(
                new SimpleGrantedAuthority(ServiceApiAccessRightsVoter.ACCESS_SERVICE_AUTHORITY_PREFIX
                        + serviceUriIdentifier)));
        apiKeyAuthToken.setAuthenticated(true);

        return apiKeyAuthToken;
    }

}
