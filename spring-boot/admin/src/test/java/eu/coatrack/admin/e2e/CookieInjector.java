package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileReadingError;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileWritingError;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.*;

public class CookieInjector {

    public static Cookie sessionCookie;

    public static void injectAuthenticationCookieToDriver(WebDriver driver){
        if (sessionCookie != null){
            safelyInjectAuthenticationCookie(driver);
        } else {
            injectNewlyCreatedCookie(driver);
        }
    }

    private static void injectNewlyCreatedCookie(WebDriver driver) {
        File cookieSaveFile = new File("./sessionCookieSaveFile.txt");

        if (cookieSaveFile.exists() && wasCookieCreatedMoreThanTwoHoursAgo(cookieSaveFile))
            cookieSaveFile.delete();

        if (cookieSaveFile.exists()){
            sessionCookie = readCookieFromFile(cookieSaveFile);
            safelyInjectAuthenticationCookie(driver);
        } else {
            sessionCookie = createCookieViaGitHubLogin(driver);
            storeCookieToLocalFile(cookieSaveFile);
        }
    }

    private static boolean wasCookieCreatedMoreThanTwoHoursAgo(File cookieSaveFile) {
        try {
            BasicFileAttributes attr = Files.readAttributes(cookieSaveFile.toPath(), BasicFileAttributes.class);
            long oneHourInMillis = 1000 * 60 * 60;
            return System.currentTimeMillis() - attr.creationTime().toMillis() > oneHourInMillis * 2;
        } catch (IOException e){
            throw new RuntimeException("An error happened during reading the cookie save file.", e);
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

    private static void safelyInjectAuthenticationCookie(WebDriver driver) {
        driver.get(getDefaultPage());
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(sessionCookie);
    }

    private static Cookie createCookieViaGitHubLogin(WebDriver driver) {
        new LoginPage(driver).loginToGithub(getUsername(), getPassword());
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
