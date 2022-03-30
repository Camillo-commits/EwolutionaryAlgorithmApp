package com.ewolutionary.alg.impl.selectors.configuration;

import java.util.HashMap;

public class BestSelectorConfiguration extends SelectorConfiguration{
    public BestSelectorConfiguration(double bestPercantage) {
        this.configurations = new HashMap<>();
        this.configurations.put(SelectorKeys.BEST_PERCENTAGE, bestPercantage);
    }
}
