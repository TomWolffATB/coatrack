package eu.coatrack.system_integration_testing.api.pages.serviceProvider;

/*-
 * #%L
 * coatrack-admin
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

public class ItemDetails {

    public final String serviceName;
    public final String serviceId;
    public final String gatewayDownloadLink;
    public final String apiKeyValue;
    public final String gatewayIdentifier;
    public final String gatewayName;

    public ItemDetails(String serviceName, String serviceId, String gatewayDownloadLink, String gatewayName, String gatewayIdentifier, String apiKeyValue) {
        this.serviceName = serviceName;
        this.serviceId = serviceId;
        this.gatewayDownloadLink = gatewayDownloadLink;
        this.apiKeyValue = apiKeyValue;
        this.gatewayIdentifier = gatewayIdentifier;
        this.gatewayName = gatewayName;
    }
}
