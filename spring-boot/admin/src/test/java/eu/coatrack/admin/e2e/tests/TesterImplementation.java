package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TesterImplementation extends AbstractTestSetup {

    @Test
    public void amazonTest(){
        LoginPage loginPage = pageProvider.getLoginPage();
        String text = loginPage.someTest();
        System.out.println(text);
        assertTrue(text.contains("Amazon"));
    }

    @Test
    public void wikiTest(){
        LoginPage loginPage = pageProvider.getLoginPage();
        String text = loginPage.someTest();
        System.out.println(text);
        assertTrue(text.contains("Wiki"));
    }

}
