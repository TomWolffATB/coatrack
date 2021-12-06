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

/*
    If the gateway successfully receives the latest list of API keys from CoatRack admin, it goes to online mode.
    If a connection attempt to CoatRack admin server failed, it goes to the time-limited functioning offline mode.
 */

public enum GatewayMode {
    ONLINE, OFFLINE
}
