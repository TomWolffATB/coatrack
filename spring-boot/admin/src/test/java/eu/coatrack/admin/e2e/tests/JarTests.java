package eu.coatrack.admin.e2e.tests;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

import static java.lang.Thread.sleep;

public class JarTests {

    private static final Logger logger = LoggerFactory.getLogger(JarTests.class);

    @Test
    public void jarTest() throws InterruptedException {

        Thread jarExecutionThread = new Thread(() -> {
            String line = "java -jar C:/Users/baier.ATB/Desktop/test.jar";
            CommandLine cmdLine = CommandLine.parse(line);
            DefaultExecutor executor = new DefaultExecutor();
            ExecuteWatchdog watchdog = new ExecuteWatchdog(120000);
            executor.setWatchdog(watchdog);
            try {
                executor.execute(cmdLine);
            } catch (IOException e) {
                logger.info("The execution of the jar file was interrupted.");
            }
        });
        jarExecutionThread.start();

        System.out.println("Going to sleep");
        sleep(30000); //Wait and do stuff
        System.out.println("Finished");
        jarExecutionThread.interrupt();
    }
}
