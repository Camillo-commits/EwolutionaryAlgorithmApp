package com.ewolutionary.alg.impl;

import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
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
    private double mutationProbability;
    private double inversionProbability;
    private int xVariableCount;
    private SelectorConfiguration selectorConfiguration;
}
