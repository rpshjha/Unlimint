package com.unlimint.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.unlimint.utils.PropertyReader.get;

@Log4j
public abstract class Page {

    final WebDriver driver;
    final WebDriverWait wait;

    protected Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(get("default.timeout"))));
    }

    public Page navigateTo(String url) {
        log.info("navigating to " + url);
        driver.get(url);
        return this;
    }

    public boolean isError(String errorMsg) {
        log.info("checking for error..");
        WebElement errorTitle = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.title")));
        WebElement errorDesc = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.error")));

        return errorTitle.getText().contains("Error!") && errorDesc.getText().contains(errorMsg);
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

    public String getPageHeading() {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#leftPanel h2"))).getText();
    }

    public abstract boolean isAt();
}
