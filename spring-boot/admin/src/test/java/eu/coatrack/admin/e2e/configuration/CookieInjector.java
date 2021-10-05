package eu.coatrack.admin.e2e.configuration;

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

import eu.coatrack.admin.e2e.api.pages.LoginPage;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileReadingError;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileWritingError;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.*;

public class CookieInjector {

    public static Cookie sessionCookie;

    public static void injectAuthenticationCookieToDriver(WebDriver driver){
        if (sessionCookie != null){
            replaceDriversCurrentSessionCookieByAuthorizedOne(driver);
        } else {
            injectNewlyCreatedCookie(driver);
        }

        driver.get(adminDashboardUrl);
        if (driver.getCurrentUrl().contains("github")){
            sessionCookie = createCookieViaGitHubLogin(driver);
            File cookieSaveFile = new File("./sessionCookieSaveFile.txt");
            if (cookieSaveFile.exists())
                cookieSaveFile.delete();
            storeCookieToLocalFile(cookieSaveFile);
        }
    }

    private static void replaceDriversCurrentSessionCookieByAuthorizedOne(WebDriver driver) {
        driver.get(startpageUrl);
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(sessionCookie);
    }

    private static void injectNewlyCreatedCookie(WebDriver driver) {
        File cookieSaveFile = new File("./sessionCookieSaveFile.txt");

        if (cookieSaveFile.exists()){
            sessionCookie = readCookieFromFile(cookieSaveFile);
            replaceDriversCurrentSessionCookieByAuthorizedOne(driver);
        } else {
            sessionCookie = createCookieViaGitHubLogin(driver);
            storeCookieToLocalFile(cookieSaveFile);
        }
    }

    private static void storeCookieToLocalFile(File cookieSaveFile) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(cookieSaveFile));
            writer.write(sessionCookie.getValue());
            writer.close();
            cookieSaveFile.createNewFile();
        } catch (Exception e){
            throw new CookieSaveFileWritingError("An error occurred while creating the cookie save file.", e);
        }
    }

    private static Cookie createCookieViaGitHubLogin(WebDriver driver) {
        new LoginPage(driver).loginToGithub(username, password);
        return driver.manage().getCookies().stream().filter(cookie -> cookie.getName().equals("SESSION")).findFirst().get();
    }

    private static Cookie readCookieFromFile(File file) {
        Cookie cookie;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String sessionCookieValue = reader.readLine();
            cookie = new Cookie.Builder("SESSION", sessionCookieValue).domain(null).path("/")
                    .expiresOn(null).isSecure(true).isHttpOnly(true).build();
            file.delete();
        } catch (Exception e){
            throw new CookieSaveFileReadingError("An error occurred while reading the cookie save file.", e);
        }
        return cookie;
    }

}
