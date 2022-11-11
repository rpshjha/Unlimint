package com.unlimint.steps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.unlimint.core.DriverInstance;
import com.unlimint.utils.PropertyReader;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Hook {

    private static final Logger logger = LoggerFactory.getLogger(Hook.class);

    @Before()
    public void beforeScenario() {
        DriverInstance.initializeDriver(PropertyReader.get("browser"));
        DriverInstance.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    }

    @After
    public void afterScenario(Scenario scenario) {
        DriverInstance.killDriver();

        logger.info(scenario.getName().toUpperCase() + " got " + scenario.getStatus());
        switch (scenario.getStatus()) {
            case PASSED:
                System.out.println(",------.    ,---.    ,---.    ,---.   ,------. ,------.   \n" +
                        "|  .--. '  /  O  \\  '   .-'  '   .-'  |  .---' |  .-.  \\  \n" +
                        "|  '--' | |  .-.  | `.  `-.  `.  `-.  |  `--,  |  |  \\  : \n" +
                        "|  | --'  |  | |  | .-'    | .-'    | |  `---. |  '--'  / \n" +
                        "`--'      `--' `--' `-----'  `-----'  `------' `-------'  ");
                break;
            case FAILED:
                System.out.println(",------.   ,---.   ,--. ,--.    ,------. ,------.   \n" +
                        "|  .---'  /  O  \\  |  | |  |    |  .---' |  .-.  \\  \n" +
                        "|  `--,  |  .-.  | |  | |  |    |  `--,  |  |  \\  : \n" +
                        "|  |`    |  | |  | |  | |  '--. |  `---. |  '--'  / \n" +
                        "`--'     `--' `--' `--' `-----' `------' `-------'  ");
                break;
            case SKIPPED:
                System.out.println(",---.   ,--. ,--. ,--. ,------.  ,------.  ,------. ,------.   \n" +
                        "'   .-'  |  .'   / |  | |  .--. ' |  .--. ' |  .---' |  .-.  \\  \n" +
                        "`.  `-.  |  .   '  |  | |  '--' | |  '--' | |  `--,  |  |  \\  : \n" +
                        ".-'    | |  |\\   \\ |  | |  | --'  |  | --'  |  `---. |  '--'  / \n" +
                        "`-----'  `--' '--' `--' `--'      `--'      `------' `-------'  \n" +
                        "                                                                ");
                break;

            default:
                break;
        }
        try {
            ExtentCucumberAdapter.getCurrentScenario().assignAuthor(System.getProperty("user.name"));
        } catch (java.lang.NullPointerException ignore) {
        }
    }

    @BeforeStep
    public void beforeEachStep(io.cucumber.java.Scenario scenario) {
        logger.info("executing step " + scenario.getName());
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        logger.info("saving screenshot for " + scenario.getName());
        String fileName = getScreenshotPath() + File.separator + scenario.getName() + ".png";
        TakesScreenshot takesScreenshot = ((TakesScreenshot) DriverInstance.getDriver());
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + File.separator + fileName);
        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", fileName);
        ExtentCucumberAdapter.getCurrentStep().addScreenCaptureFromPath(dest.getPath());
    }

    private static String getScreenshotPath() {
        return String.valueOf(PropertyReader.get("screenshot.dir"));
    }
}
