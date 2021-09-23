package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.PageFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = YggAdminApplication.class)
public abstract class AbstractTestSetup {

    protected PageFactory pageFactory;

    @BeforeEach
    protected void setup() {
        //System.setProperty("webdriver.gecko.driver", "c:/Program Files/GeckoDriver/geckodriver.exe");
        pageFactory = new PageFactory(new FirefoxDriver());
    }

    //TODO should be executed even when an error is thrown during the test.
    @AfterEach
    private void tearDown(){
        pageFactory.closeDriver();
    }

}
