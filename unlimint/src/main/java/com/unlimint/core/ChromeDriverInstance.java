package com.unlimint.core;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.chrome.ChromeDriver;

@Log4j
public class ChromeDriverInstance {

    private ChromeDriverInstance() {
    }

    public static ChromeDriver createDriverUsingChrome() {
        log.info("Opening the browser : Chrome");
        return new ChromeDriver();
    }
}
