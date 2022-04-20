package com.ewolutionary.alg.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Chromosome {

    private double value;

    public Chromosome(int start, int stop) {
        this.value = start + (stop-start) * new Random().nextDouble();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
