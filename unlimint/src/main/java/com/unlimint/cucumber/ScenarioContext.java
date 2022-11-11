package com.unlimint.cucumber;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static Map<Context, Object> scenarioContext;

    public ScenarioContext() {
        scenarioContext = new HashMap<>();
    }

    public void setContext(Context key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getContext(Context key) {
        return scenarioContext.get(key);
    }
}
