package eu.coatrack.proxy.security;

import eu.coatrack.api.ApiKey;
import eu.coatrack.api.ServiceApi;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.apiKeyProvider.ApiKeyProvider;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.ApiKeyValidator;
import eu.coatrack.proxy.security.consumerAuthenticationProvider.ConsumerAuthenticationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ConsumerAuthenticationCreatorTest {

    @Mock
    ApiKeyProvider apiKeyProvider;
    @Mock
    ApiKeyValidator apiKeyValidator;
    @InjectMocks
    ConsumerAuthenticationCreator consumerAuthenticationCreator;

    private final String apiKeyValue = "some API key value";
    private final String serviceUriIdentifier = "some uriIdentifier";
    private ApiKey apiKey;

    @BeforeEach
    public void init() {
        ServiceApi serviceApi = new ServiceApi();
        serviceApi.setUriIdentifier(serviceUriIdentifier);
        apiKey = new ApiKey();
        apiKey.setKeyValue(apiKeyValue);
        apiKey.setServiceApi(serviceApi);

        MockitoAnnotations.openMocks(this);
        when(apiKeyProvider.getApiKeyEntityByApiKeyValue(apiKeyValue)).thenReturn(apiKey);
    }

    @Test
    public void validKeyShouldReturnedValidAuthentication() {
        when(apiKeyValidator.isApiKeyValid(apiKey)).thenReturn(true);
        Authentication consumerAuthentication = consumerAuthenticationCreator.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue);

        assertTrue(consumerAuthentication.isAuthenticated());
        assertEquals(consumerAuthentication.getCredentials(), apiKeyValue);

        String expectedAuthority = ServiceApiAccessRightsVoter.ACCESS_SERVICE_AUTHORITY_PREFIX + serviceUriIdentifier;
        String actualAuthority = consumerAuthentication.getAuthorities().stream().findFirst().get().getAuthority();
        assertEquals(expectedAuthority, actualAuthority);
    }

    @Test
    public void invalidKeyShouldCauseBadCredentialsException() {
        when(apiKeyValidator.isApiKeyValid(apiKey)).thenReturn(false);
        assertThrows(BadCredentialsException.class, () -> consumerAuthenticationCreator.createConsumerAuthTokenIfApiKeyIsValid(apiKeyValue));
    }

}
