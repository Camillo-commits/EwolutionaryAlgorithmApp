package com.ewolutionary.alg.impl;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Configuration {
    private int sizeOfPopulation;
    private long maxIterations;
    private boolean isEliteStrategy;
    private boolean isInverter;
}
