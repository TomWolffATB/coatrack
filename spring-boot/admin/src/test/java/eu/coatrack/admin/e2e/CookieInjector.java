package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileReadingError;
import eu.coatrack.admin.e2e.exceptions.CookieSaveFileWritingError;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.StringJoiner;

import static eu.coatrack.admin.e2e.PageFactory.pathPrefix;

public class CookieInjector {

    private static final String username = "user";
    private static final String password = "password";
    public static Cookie authenticationCookie;

    public static void injectAuthenticationCookieToDriver(WebDriver driver){
        if (authenticationCookie != null){
            safelyInjectAuthenticationCookie(driver);
        } else {
            injectNewlyCreatedCookie(driver);
        }
    }

    private static void injectNewlyCreatedCookie(WebDriver driver) {
        File cookieSaveFile = new File("./githubSessionCookieValue.txt");

        if (cookieSaveFile.exists() && wasCookieCreatedMoreThanTwoHoursAgo(cookieSaveFile))
            cookieSaveFile.delete();

        if (cookieSaveFile.exists()){
            authenticationCookie = readCookieFromFile(cookieSaveFile);
            safelyInjectAuthenticationCookie(driver);
        } else {
            authenticationCookie = createCookieViaGitHubLogin(driver);
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
            StringJoiner joiner = new StringJoiner(";");
            joiner
                    .add(authenticationCookie.getName())
                    .add(authenticationCookie.getValue())
                    .add(authenticationCookie.getDomain())
                    .add(authenticationCookie.getPath());
            writer.write(joiner.toString());
            writer.close();
            cookieSaveFile.createNewFile();
        } catch (Exception e){
            throw new CookieSaveFileWritingError("An error occurred while writing into the cookie save file.", e);
        }
    }

    private static void safelyInjectAuthenticationCookie(WebDriver driver) {
        driver.get(pathPrefix + "/");
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(authenticationCookie);
    }

    private static Cookie createCookieViaGitHubLogin(WebDriver driver) {
        new LoginPage(driver).loginToGithub(username, password);
        return driver.manage().getCookies().stream().filter(cookie -> cookie.getName().equals("SESSION")).findFirst().get();
    }

    private static Cookie readCookieFromFile(File file) {
        Cookie cookie;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] parts = reader.readLine().split(";");
            cookie = new Cookie.Builder(parts[0], parts[1]).domain(parts[2]).path(parts[3])
                    .expiresOn(null).isSecure(true).isHttpOnly(true).build();
            file.delete();
        } catch (Exception e){
            throw new CookieSaveFileReadingError("An error occurred while reading the cookie save file.", e);
        }
        return cookie;
    }

}
