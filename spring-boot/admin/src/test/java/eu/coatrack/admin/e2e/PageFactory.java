package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Set;
import java.util.StringJoiner;

public class PageFactory {

    public static final String pathPrefix = "https://coatrack.eu";
    private static Cookie authenticationCookie;

    private static String username = "user";
    private static String password = "password";

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;

        File file = new File("./githubSessionCookie.txt");
        if (authenticationCookie != null){
            driver.get(pathPrefix + "/");
            driver.manage().deleteAllCookies();
            driver.manage().addCookie(authenticationCookie);
        }
        else if (file.exists()){
            authenticationCookie = readCookieFromFile(file);
            driver.get(pathPrefix + "/");
            driver.manage().deleteAllCookies();
            driver.manage().addCookie(authenticationCookie);
        } else {
            authenticationCookie = createAndSetAuthenticationCookie(file);
        }

    }

    private Cookie createAndSetAuthenticationCookie(File file) {
        new LoginPage(driver).loginToGithub(username, password);

        Cookie cookie = driver.manage().getCookies().stream().filter(c -> c.getName().equals("SESSION")).findFirst().get();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            StringJoiner joiner = new StringJoiner(";");
            joiner
                    .add(cookie.getName())
                    .add(cookie.getValue())
                    .add(cookie.getDomain())
                    .add(cookie.getPath());
            writer.write(joiner.toString());
            writer.close();
            file.createNewFile();
        } catch (Exception ignored){}

        return cookie;
    }

    private Cookie readCookieFromFile(File file) {
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

    public Tutorial getTutorial(){
        return new Tutorial(driver);
    }

    public ServiceOfferings getServiceOfferings(){
        return new ServiceOfferings(driver);
    }

    public void close(){
        driver.close();
    }

    public void loginWithCookieAndClickTurorial() {
        driver.get(pathPrefix + "/");
        driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
        driver.get(pathPrefix + "/admin");
        driver.findElement(By.linkText("Tutorial")).click();
    }
}
