package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

public class JarTests {

    @Test
    public void jarTest() throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        final Process[] pr = {null};
        Thread t = new Thread(){
            @lombok.SneakyThrows
            public void run(){
                pr[0] = rt.exec("java -jar C:/Users/baier.ATB/Desktop/test.jar");
                pr[0].waitFor();
            }
        };
        t.setDaemon(true);
        t.start();

        Thread.sleep(100);

        BufferedReader reader = new BufferedReader(new InputStreamReader(pr[0].getInputStream()));

        int seconds1 = LocalTime.now().toSecondOfDay();
        System.out.println("seconds1: " + seconds1);
        int seconds2 = LocalTime.now().toSecondOfDay();
        
        String line2 = reader.readLine();
        while (seconds2 - seconds1 < 30){
            System.out.println(line2);
            line2 = reader.readLine();
            seconds2 = LocalTime.now().toSecondOfDay();
            System.out.println("seconds2 - seconds1: " + (seconds2-seconds1));
        }

        System.out.println("Finished with the loop");
        pr[0].destroyForcibly();
    }


}
