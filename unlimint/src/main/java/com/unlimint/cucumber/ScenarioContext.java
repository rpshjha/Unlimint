package com.unlimint.cucumber;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private Map<Context, Object> contextObjectMap;

    public ScenarioContext() {
        contextObjectMap = new HashMap<>();
    }

    public void setContext(Context key, Object value) {
        contextObjectMap.put(key, value);
    }

    public Object getContext(Context key) {
        return contextObjectMap.get(key);
    }
}
