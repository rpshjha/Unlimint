package com.unlimint.steps;


import com.unlimint.core.BrowserActions;
import com.unlimint.utils.PropertyReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;

import java.time.Duration;

import static com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter.getCurrentScenario;
import static com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter.getCurrentStep;
import static com.unlimint.core.DriverInstance.*;

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
            getCurrentScenario().assignAuthor(System.getProperty("user.name"));
            killDriver();
        } catch (java.lang.NullPointerException exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        scenario.attach(new BrowserActions(getDriver()).captureScreenshot(OutputType.BYTES), "image/png", scenario.getName());
        getCurrentStep().addScreenCaptureFromBase64String(new BrowserActions(getDriver()).captureScreenshot(OutputType.BASE64), scenario.getName());
    }

}
