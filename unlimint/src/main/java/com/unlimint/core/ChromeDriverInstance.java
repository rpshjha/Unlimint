package com.unlimint.core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChromeDriverInstance {

    private static Logger logger = LoggerFactory.getLogger(ChromeDriverInstance.class);

    public static ChromeDriver createDriverUsingChrome() {
        return new ChromeDriver();
    }
}
