package com.unlimint.core;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirefoxDriverInstance {

    private FirefoxDriverInstance() {
    }

    private static Logger logger = LoggerFactory.getLogger(FirefoxDriverInstance.class);

    public static FirefoxDriver createDriverUsingFirefox() {
        logger.info("Opening the browser : Firefox");
        return new FirefoxDriver();
    }
}
