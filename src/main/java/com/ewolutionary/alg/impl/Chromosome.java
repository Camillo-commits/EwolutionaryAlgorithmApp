package com.ewolutionary.alg.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Chromosome {

    private double value;
    private final int start;
    private final int stop;

    public Chromosome(int start, int stop) {
        this.start = start;
        this.stop = stop;
        this.value = getNextValue();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getNextValue() {
        return start + (stop-start) * new Random().nextDouble();
    }

    public void setNextValue() {
        this.value = getNextValue();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
