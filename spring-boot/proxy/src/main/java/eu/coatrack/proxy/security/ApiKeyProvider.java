package eu.coatrack.proxy.security;

import eu.coatrack.api.ApiKey;
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
