package com.unlimint.core;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.firefox.FirefoxDriver;

@Log4j
public class FirefoxDriverInstance {

    private FirefoxDriverInstance() {
    }

    public static FirefoxDriver createDriverUsingFirefox() {
        log.info("Opening the browser : Firefox");
        return new FirefoxDriver();
    }
}
