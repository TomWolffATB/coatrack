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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import eu.coatrack.proxy.security.exceptions.ApiKeyFetchingFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class LocalApiKeyManager_GatewayModeUpdateLoggingTest extends LocalApiKeyManager_AbstractTestSetup {

    private final static String switchingToOnlineModeMessage = LocalApiKeyManager.switchingToOnlineModeMessage;
    private final static String switchingToOfflineModeMessage = LocalApiKeyManager.switchingToOfflineModeMessage;

    private LogEventStorage logEventStorage;

    @BeforeEach
    public void setup() {
        super.setupLocalApiKeyManagerAndApiKeyList();

        Logger log = (Logger) LoggerFactory.getLogger(LocalApiKeyManager.class);
        logEventStorage = new LogEventStorage();
        logEventStorage.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        log.setLevel(Level.DEBUG);
        log.addAppender(logEventStorage);
        logEventStorage.start();
    }

    @Test
    public void NoAction_ShouldNotSwitchToAnyMode() {
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void switchingToOnlineModeShouldBeLogged() throws ApiKeyFetchingFailedException {
        switchToOnlineMode();

        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void stayingInOfflineModeShouldNotBeLogged() throws ApiKeyFetchingFailedException {
        //Gateway starts in offline mode and does therefore not switch modes.
        switchToOfflineMode();

        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void ON_OFF() throws ApiKeyFetchingFailedException {
        switchToOnlineMode();
        switchToOfflineMode();

        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void OFF_ON() throws ApiKeyFetchingFailedException {
        switchToOfflineMode();
        switchToOnlineMode();

        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void ON_ON() throws ApiKeyFetchingFailedException {
        switchToOnlineMode();
        switchToOnlineMode();

        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void OFF_OFF() throws ApiKeyFetchingFailedException {
        switchToOfflineMode();
        switchToOfflineMode();

        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void localApiKeySearchShouldNotTriggerSwitchToOnlineMode() {
        localApiKeyManager.getApiKeyEntityByApiKeyValue(apiKey.getKeyValue());

        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(0, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    @Test
    public void localApiKeySearchShouldTriggerSwitchFromOnlineToOfflineMode() throws ApiKeyFetchingFailedException {
        switchToOnlineMode();
        localApiKeyManager.getApiKeyEntityByApiKeyValue(apiKey.getKeyValue());

        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOnlineModeMessage));
        assertEquals(1, logEventStorage.getNumberOfLogEventsContaining(switchingToOfflineModeMessage));
    }

    public void switchToOnlineMode() throws ApiKeyFetchingFailedException {
        reset(apiKeyFetcherMock);
        when(apiKeyFetcherMock.requestLatestApiKeyListFromAdmin()).thenReturn(apiKeyList);
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    public void switchToOfflineMode() throws ApiKeyFetchingFailedException {
        reset(apiKeyFetcherMock);
        when(apiKeyFetcherMock.requestLatestApiKeyListFromAdmin()).thenThrow(new ApiKeyFetchingFailedException("test"));
        localApiKeyManager.refreshLocalApiKeyCacheWithApiKeysFromAdmin();
    }

    static class LogEventStorage extends ListAppender<ILoggingEvent> {
        public long getNumberOfLogEventsContaining(String phrase) {
            return list.stream().filter(logEntry -> logEntry.getMessage().contains(phrase)).count();
        }
    }

}
