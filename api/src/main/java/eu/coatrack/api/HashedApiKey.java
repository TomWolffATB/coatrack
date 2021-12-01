package eu.coatrack.api;

/*-
 * #%L
 * coatrack-api
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

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import java.util.Date;

public class HashedApiKey {

    public final String hashedApiKeyValue;
    public Date validUntil;
    public ServiceApi serviceApi;
    public Date deletedWhen;

    public HashedApiKey(String hashedApiKeyValue) {
        this.hashedApiKeyValue = hashedApiKeyValue;
    }
}
