package eu.coatrack.admin.e2e;

import eu.coatrack.admin.YggAdminApplication;
import eu.coatrack.admin.e2e.api.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = YggAdminApplication.class)
public abstract class AbstractTestSetup {

    protected PageProvider pageProvider;

    @BeforeEach
    protected void setup() {
        //System.setProperty("webdriver.gecko.driver", "c:/Program Files/GeckoDriver/geckodriver.exe");
        pageProvider = new PageProvider(new FirefoxDriver());
    }

    @AfterEach
    private void tearDown(){
        pageProvider.close();
    }

}
