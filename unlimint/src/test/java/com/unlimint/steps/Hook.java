package com.unlimint.steps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.unlimint.utils.PropertyReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static com.unlimint.core.DriverInstance.getDriver;
import static com.unlimint.core.DriverInstance.initializeDriver;

@Log4j
public class Hook {

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("executing scenario " + scenario.getName());
        initializeDriver(PropertyReader.get("browser"));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    }

    @After
    public void afterScenario(Scenario scenario) {
//        DriverInstance.killDriver();

        log.info(scenario.getName().toUpperCase() + " got " + scenario.getStatus());
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
            captureScreenshot(scenario.getName());
        } catch (java.lang.NullPointerException ignore) {
        }
    }


    private static void captureScreenshot(String scenarioName) {
        String getScreenshotPath = String.valueOf(PropertyReader.get("screenshot.dir"));
        String fileName = getScreenshotPath + File.separator + scenarioName + ".png";

        TakesScreenshot takesScreenshot = ((TakesScreenshot) getDriver());
        File screenshotAsFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        final String screenshotInBase64 = takesScreenshot.getScreenshotAs(OutputType.BASE64);

        File dest = new File(System.getProperty("user.dir") + File.separator + fileName);
        try {
            FileUtils.copyFile(screenshotAsFile, dest);
            log.info("saving screenshot for " + scenarioName);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        ExtentCucumberAdapter.getCurrentStep().addScreenCaptureFromBase64String(screenshotInBase64, scenarioName);
    }
}
