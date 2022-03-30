package com.ewolutionary.alg.impl.selectors.configuration;

import java.util.HashMap;

public class RouletteSelectorConfiguration extends SelectorConfiguration {
    public RouletteSelectorConfiguration(double bestPercentage) {
        this.configurations = new HashMap<>();
        this.configurations.put(SelectorKeys.BEST_PERCENTAGE, bestPercentage);
    }
}
