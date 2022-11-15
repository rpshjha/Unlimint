package com.unlimint.core;

import com.unlimint.pages.Page;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@Log4j
public class BrowserActions {

    private WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public <T> T captureScreenshot(OutputType<T> outputType){
        log.info("capturing screenshot .. as " + outputType.toString());
        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        return takesScreenshot.getScreenshotAs(outputType);
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

}
