package eu.coatrack.admin.e2e.tests;

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

import eu.coatrack.admin.e2e.api.serviceProvider.ItemDto;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static eu.coatrack.admin.e2e.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.host;
import static org.junit.jupiter.api.Assertions.*;

public class TutorialTests extends AbstractTestSetup {

    private static final Logger logger = LoggerFactory.getLogger(TutorialTests.class);

    @Test
    public void tutorialTest() throws IOException, InterruptedException {
        GatewayRunner gatewayRunner = pageFactory.getGatewayRunner();
        //Check Dashboard stats
        gatewayRunner.makeValidCall();
        //Check if Dashboard stats changed
        gatewayRunner.stopGatewayAndCleanup();
    }

}
