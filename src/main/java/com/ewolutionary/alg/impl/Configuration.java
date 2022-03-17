package com.ewolutionary.alg.impl;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Configuration {
    private int sizeOfPopulation;
    private int precision;
    private int start;
    private int stop;
    private int percentOfBestToNextCentury;
    private long maxIterations;
    private boolean isEliteStrategy;
    private boolean isInverter;
    private double crossingProbability;
}
