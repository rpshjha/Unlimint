package com.unlimint.pages;

import com.unlimint.core.BrowserActions;
import com.unlimint.core.ScreenActions;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.unlimint.utils.PropertyReader.get;

@Log4j
public abstract class Page {

    final WebDriver driver;
    final WebDriverWait wait;
    final BrowserActions browser;
    final ScreenActions element;

    protected Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(get("default.timeout"))));
        this.browser = new BrowserActions(driver);
        this.element = new ScreenActions(driver);
    }

    public boolean isError(String errorMsg) {
        log.info("checking for error..");
        try {
            String errorTitle = element.getText(By.cssSelector("h1.title"), 5);
            String errorDesc = element.getText(By.cssSelector("p.error"), 5);
            return errorTitle.contains("Error!") && errorDesc.contains(errorMsg);
        } catch (org.openqa.selenium.TimeoutException timeoutException) {
            return false;
        }

    }

    public String getPageHeading() {
        return this.element.getText(By.cssSelector("div#leftPanel h2"));
    }

    public Page navigateTo(String url) {
        log.info("navigating to " + url);
        driver.get(url);
        return this;
    }

    public abstract boolean isAt();
}
