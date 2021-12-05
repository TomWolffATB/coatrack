package eu.coatrack.proxy.security;

import eu.coatrack.api.ApiKey;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.apiKeyFetcher.ApiKeyFetcher;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.ApiKeyProvider;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.localApiKeyManager.LocalApiKeyManager;
import eu.coatrack.proxy.security.exceptions.ApiKeyFetchingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ApiKeyProviderTests {

    @Mock private ApiKeyFetcher apiKeyFetcherMock;
    @Mock private LocalApiKeyManager localApiKeyManagerMock;
    @InjectMocks private ApiKeyProvider apiKeyProvider;

    private final String apiKeyValue = "some API key value";
    private ApiKey apiKeyToBeReturned;

    @BeforeEach
    public void init() {
        apiKeyToBeReturned = new ApiKey();
        apiKeyToBeReturned.setKeyValue(apiKeyValue);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void apiFetchedFromAdminShouldBeReturned() {
        when(apiKeyFetcherMock.requestApiKeyFromAdmin(apiKeyValue)).thenReturn(apiKeyToBeReturned);
        assertSame(apiKeyToBeReturned, apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue));
    }

    @Test
    public void whenFetchingFromAdminFails_LocalApiKeyShouldBeProvided() {
        when(apiKeyFetcherMock.requestApiKeyFromAdmin(apiKeyValue)).thenThrow(ApiKeyFetchingFailedException.class);
        when(localApiKeyManagerMock.getApiKeyEntityFromLocalCache(apiKeyValue)).thenReturn(apiKeyToBeReturned);

        assertSame(apiKeyToBeReturned, apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue));
    }

    @Test
    public void whenAlsoLocalSearchFails_TheExceptionShouldBeRedirected() {
        when(apiKeyFetcherMock.requestApiKeyFromAdmin(apiKeyValue)).thenThrow(ApiKeyFetchingFailedException.class);
        when(localApiKeyManagerMock.getApiKeyEntityFromLocalCache(apiKeyValue)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> localApiKeyManagerMock.getApiKeyEntityFromLocalCache(apiKeyValue));
    }

}
