package eu.coatrack.system_integration_testing.api.pages;

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

import eu.coatrack.system_integration_testing.exceptions.LoginViaGitHubFailedException;
import eu.coatrack.system_integration_testing.exceptions.NoRedirectionToGitHubLoginPage;
import eu.coatrack.system_integration_testing.exceptions.UnsupportedTwoFactorAuthenticationException;
import eu.coatrack.system_integration_testing.exceptions.WrongGitHubCredentialsException;
import org.openqa.selenium.By;

import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;
import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.serviceProviderDashboardUrl;

public class LoginPage {

    public void loginToGithub(String username, String password){
        getRedirectedToGitHubLoginPage();
        fillInGitHubLoginForm(username, password);
        createNewCoatRackAccountIfRequired();
    }

    private void getRedirectedToGitHubLoginPage() {
        driver.get(serviceProviderDashboardUrl);
        if (!driver.getCurrentUrl().contains("github"))
            throw new NoRedirectionToGitHubLoginPage("The website didn't redirect the unauthenticated " +
                    "user to the GitHub login form.");
    }

    private void fillInGitHubLoginForm(String username, String password) {
        insertCredentialsAndLogin(username, password);

        int checkAttemptsThatGitHubWasLeft = 0;
        while (driver.getCurrentUrl().contains("github")){
            if (checkAttemptsThatGitHubWasLeft > 10)
                throwAccordingException();

            checkAttemptsThatGitHubWasLeft++;
            sleepMillis(1000);
        }
    }

    private void insertCredentialsAndLogin(String username, String password) {
        driver.findElement(By.id("login_field")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
    }

    private void throwAccordingException() {
        if (driver.getCurrentUrl().contains("verified-device")) {
            throw new UnsupportedTwoFactorAuthenticationException("GitHub requires a second form of " +
                    "authentication which is not supported.");
        } else if (driver.getCurrentUrl().contains("Incorrect username or password.")) {
            throw new WrongGitHubCredentialsException("Wrong credentials were being used for the " +
                    "authentication via GitHub login.");
        } else {
            throw new LoginViaGitHubFailedException("An error happened during the GitHub login. The user was " +
                    "not correctly redirected to the CoatRack Web Application. Current URL is: " + driver.getCurrentUrl());
        }
    }

    private void createNewCoatRackAccountIfRequired() {
        if (driver.getPageSource().contains("Register New")) {
            driver.findElement(By.id("firstname")).sendKeys("John");
            driver.findElement(By.name("lastname")).sendKeys("Connor");
            driver.findElement(By.name("company")).sendKeys("Skynet");
            driver.findElement(By.name("submit")).click();
            sleepMillis(2000);
        }
    }

}
