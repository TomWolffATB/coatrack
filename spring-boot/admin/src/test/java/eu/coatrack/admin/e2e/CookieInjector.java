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
    private static Cookie authenticationCookie;

    public static void injectAuthenticationCookieToDriver(WebDriver driver){
        File file = new File("./githubSessionCookie.txt");
        if (authenticationCookie != null){
            safelyInjectAuthenticationCookie(driver);
        } else if (file.exists()){
            authenticationCookie = readCookieFromFile(file);
            safelyInjectAuthenticationCookie(driver);
        } else {
            authenticationCookie = createAndSetAuthenticationCookie(file, driver);
            storeCookieToLocalFile(file, driver);
        }
    }

    private static void storeCookieToLocalFile(File file, WebDriver driver) {
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
        } catch (Exception ignored){}
    }

    private static void safelyInjectAuthenticationCookie(WebDriver driver) {
        driver.get(pathPrefix + "/");
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(authenticationCookie);
    }

    private static Cookie createAndSetAuthenticationCookie(File file, WebDriver driver) {
        new LoginPage(driver).loginToGithub(username, password);
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
        } catch (Exception ignored){}
        return cookie;
    }

}
