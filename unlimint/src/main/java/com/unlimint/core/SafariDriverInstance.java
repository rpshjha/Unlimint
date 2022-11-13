package com.unlimint.core;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.safari.SafariDriver;

@Log4j
public class SafariDriverInstance {

    private SafariDriverInstance() {
    }

    public static SafariDriver createDriverUsingSafari() {
        log.info("Opening the browser : Safari");
        return new SafariDriver();
    }
}
