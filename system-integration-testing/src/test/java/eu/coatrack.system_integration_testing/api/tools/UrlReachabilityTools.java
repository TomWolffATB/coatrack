package eu.coatrack.system_integration_testing.api.tools;

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

import eu.coatrack.system_integration_testing.exceptions.UnexpectedErrorPageReceivedException;
import eu.coatrack.system_integration_testing.exceptions.UrlCouldNotBeReachedException;

import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;

public class UrlReachabilityTools {

    public void fastVisit(String urlToBeVisited){
        if(!driver.getCurrentUrl().equals(urlToBeVisited))
            driver.get(urlToBeVisited);
    }

    public void throwExceptionIfUrlIsNotReachable(String url){
        driver.get(url);
        throwExceptionIfErrorPageWasReceived();
        if (!url.equals(driver.getCurrentUrl()))
            throw new UrlCouldNotBeReachedException("The URL " + url + " could not be reached.");
    }

    public void throwExceptionIfErrorPageWasReceived(){
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Sorry, an error occurred."))
            throw new UnexpectedErrorPageReceivedException("The CoatRack error page appeared.");
        else if (pageSource.contains("Whitelabel Error Page"))
            throw new UnexpectedErrorPageReceivedException("A Whitelabel Error Page appeared.");
    }
}
