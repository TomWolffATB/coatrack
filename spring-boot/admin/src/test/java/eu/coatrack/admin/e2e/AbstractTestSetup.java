package eu.coatrack.admin.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = YggAdminApplication.class)
public abstract class AbstractTestSetup {

    protected PageFactory pageProvider;

    @BeforeEach
    protected void setup() {
        //System.setProperty("webdriver.gecko.driver", "c:/Program Files/GeckoDriver/geckodriver.exe");
        pageProvider = new PageFactory(new FirefoxDriver());
    }

    @AfterEach
    private void tearDown(){
        pageProvider.close();
    }

}
