package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

public class JarTests {

    @Test
    public void jarTest() throws InterruptedException {
        Runtime rt = Runtime.getRuntime();
        final Process[] pr = {null};
        Thread t = new Thread(() -> {
            try {
                pr[0] = rt.exec("java -jar C:/Users/baier.ATB/Desktop/test.jar");
                pr[0].waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        Thread.sleep(100);

        Thread t2 = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(pr[0].getInputStream()));
                while (true)
                    System.out.println(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        //TODO wait until 'Started GatewayApplication' is found.
        Thread.sleep(30000);

        System.out.println("Finished with the loop");
        pr[0].destroyForcibly();
    }
}
