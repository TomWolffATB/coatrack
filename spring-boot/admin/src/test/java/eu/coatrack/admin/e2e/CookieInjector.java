package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.StringJoiner;

import static eu.coatrack.admin.e2e.PageFactory.pathPrefix;

public class CookieInjector {

    private static final String username = "user";
    private static final String password = "password";
    public static Cookie authenticationCookie;
    private static boolean wasThereAGitHubLoginAttempt = false; //TODO using false credential should result in only one login attempt

    public static void injectAuthenticationCookieToDriver(WebDriver driver){
        if (authenticationCookie != null){
            safelyInjectAuthenticationCookie(driver);
        } else {
            injectNewlyCreatedCookie(driver);
        }
    }

    private static void injectNewlyCreatedCookie(WebDriver driver) {
        File file = new File("./githubSessionCookie.txt");
        //TODO cookie storage files created yesterday should be deleted

        if (file.exists()){
            authenticationCookie = readCookieFromFile(file);
            safelyInjectAuthenticationCookie(driver);
        } else {
            authenticationCookie = createCookieViaGitHubLogin(driver);
            storeCookieToLocalFile(file);
        }
    }

    private static void storeCookieToLocalFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            StringJoiner joiner = new StringJoiner(";");
            joiner
                    .add(authenticationCookie.getName())
                    .add(authenticationCookie.getValue())
                    .add(authenticationCookie.getDomain())
                    .add(authenticationCookie.getPath());
            writer.write(joiner.toString());
            writer.close();
            file.createNewFile();
        } catch (Exception ignored){} //TODO
    }

    private static void safelyInjectAuthenticationCookie(WebDriver driver) {
        driver.get(pathPrefix + "/");
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(authenticationCookie);
    }

    private static Cookie createCookieViaGitHubLogin(WebDriver driver) {
        new LoginPage(driver).loginToGithub(username, password);
        wasThereAGitHubLoginAttempt = true;
        Cookie cookie = driver.manage().getCookies().stream().filter(c -> c.getName().equals("SESSION")).findFirst().get();
        return cookie;
    }

    private static Cookie readCookieFromFile(File file) {
        Cookie cookie = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String[] parts = reader.readLine().split(";");
            cookie = new Cookie.Builder(parts[0], parts[1]).domain(parts[2]).path(parts[3])
                    .expiresOn(null).isSecure(true).isHttpOnly(true).build();
            file.delete();
        } catch (Exception ignored){} //TODO
        return cookie;
    }

}
