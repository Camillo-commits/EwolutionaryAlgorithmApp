package com.ewolutionary.alg.impl.selectors.configuration;

import java.util.Map;

public abstract class SelectorConfiguration {
    protected Map<SelectorKeys, Object> configurations;

    public Map<SelectorKeys, Object> getConfigurations() {
        return configurations;
    }
}
