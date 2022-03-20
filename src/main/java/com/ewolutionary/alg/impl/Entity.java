package com.ewolutionary.alg.impl;

import java.util.Arrays;

public class Entity {

    private int size;
    private Chromosome chromosome;
    private double entityValue;
    private final int start;
    private final int stop;
    private double fitness;

    public Entity(int start, int stop, int precision) {
        this.start = start;
        this.stop = stop;
        this.size = calculateSize(precision);
        this.chromosome = new Chromosome(size);
        this.entityValue = calculateValue();
        this.fitness = calculateFitness();
    }

    public Entity(int start, int stop, byte[] chromosomeData) {
        this.start = start;
        this.stop = stop;
        this.size = chromosomeData.length;
        this.chromosome = new Chromosome(chromosomeData);
        this.entityValue = calculateValue();
        this.fitness = calculateFitness();
    }

    public int getSize() {
        return size;
    }

    public double getValue() {
        return entityValue;
    }

    public double getFitness() {
        return fitness;
    }

    public byte[] getChromosome() {
        return chromosome.getBinary();
    }

    public Chromosome getChromosome2() {
        return chromosome;
    }

    private double calculateValue() {
        // x = a + decimal(chromosome) * (b-a) / (2^m - 1)
        return start + chromosome.getDecimalValue() * (stop - start) / (Math.pow(2, size) - 1);
    }

    private int calculateSize(int precision) {
        //(b-a) * 10^precision <= 2^m - 1
        double left = log2((stop - start) * Math.pow(10, precision)) + log2(1);

        return (int) Math.ceil(left);
    }

    private double calculateFitness() {
        //TODO think how to represent function and how calculate fitness from it
        return 0;
    }

    private double log2(double n) {
        return Math.log(n) / Math.log(2);
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    @Override
    public String toString() {
        return "Entity: " + Arrays.toString(chromosome.getBinary()) + " value: " + getValue();
    }

}
