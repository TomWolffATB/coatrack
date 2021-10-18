package eu.coatrack.system_integration_testing.tests;

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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static eu.coatrack.system_integration_testing.api.PageFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DashboardAndReportUpdateTests {

    private final int serviceProviderTotalApiCalls = serviceProviderDashboard.getTotalApiCalls();
    private final int serviceProviderApiUsageTrend = serviceProviderDashboard.getApiUsageTrend();
    private final int serviceProviderCallsOfLoggedInUser = serviceProviderDashboard.getCallsOfLoggedInUser();

    private final int serviceConsumerTotalApiCalls = serviceConsumerDashboard.getTotalApiCalls();
    private final int serviceConsumerNumberOfServicesCalled = serviceConsumerDashboard.getNumberOfServicesCalled();

    private final int serviceProviderReportedServiceCalls;
    private final int serviceConsumerReportedServiceCalls;

    public DashboardAndReportUpdateTests(){
        gatewayRunner.executeRunner();
        serviceProviderReportedServiceCalls = serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);
        serviceConsumerReportedServiceCalls = serviceConsumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();
    }

    @Test
    public void serviceProviderDashboardTotalApiCallsUpdateTest() {
        assertEquals(serviceProviderTotalApiCalls + 2, serviceProviderDashboard.getTotalApiCalls());
    }

    @Test
    public void serviceProviderDashboardApiUsageTrendUpdateTest() {
        assertEquals(serviceProviderApiUsageTrend + 2, serviceProviderDashboard.getApiUsageTrend());
    }

    @Test
    public void serviceProviderDashboardCallsOfLoggedInUserUpdateTest() {
        assertEquals(serviceProviderCallsOfLoggedInUser + 2, serviceProviderDashboard.getCallsOfLoggedInUser());
    }

    @Test
    public void consumerDashboardTotalApiCallsUpdateTest() {
        assertEquals(serviceConsumerTotalApiCalls + 2, serviceConsumerDashboard.getTotalApiCalls());
    }

    @Test
    public void consumerDashboardNumberOfServicesCalledUpdateTest() {
        assertEquals(serviceConsumerNumberOfServicesCalled + 1, serviceConsumerDashboard.getNumberOfServicesCalled());
    }

    @Test
    public void serviceProviderReportsUpdateTest() {
        assertEquals(serviceProviderReportedServiceCalls + 2, serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @Test
    public void consumerReportsUpdateTest() {
        assertEquals(serviceConsumerReportedServiceCalls + 2, serviceConsumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @AfterAll
    public static void tearDownGatewayRunner() {
        gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Error call tests are still missing. Are there other numbers not tested yet? usageTrend and totalApiCall should not be updated when error call is made.

}
