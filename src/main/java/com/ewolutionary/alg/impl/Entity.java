package com.ewolutionary.alg.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Entity {

    private int size;
    private List<Chromosome> chromosomes;
    private List<Double> entityValue;
    private final int start;
    private final int stop;
    private boolean isElite;
    private double fitness;

    public Entity(int start, int stop, int precision, int xVariablesCount) {
        this.start = start;
        this.stop = stop;
        this.size = calculateSize(precision);
        this.chromosomes = generateChromosomes(size, xVariablesCount);
        this.entityValue = calculateValue();
        this.fitness = calculateFitness();
    }

    public Entity(int start, int stop, List<byte[]> chromosomeData) {
        this.start = start;
        this.stop = stop;
        this.size = chromosomeData.get(0).length;
        this.chromosomes = chromosomeData.stream().map(Chromosome::new).collect(Collectors.toList());
        this.entityValue = calculateValue();
        this.fitness = calculateFitness();
    }

    private List<Chromosome> generateChromosomes(int size, int count) {
        return IntStream.range(0, count).mapToObj(i -> new Chromosome(size)).collect(Collectors.toList());
    }

    public int getSize() {
        return size;
    }

    public List<Double> getValue() {
        return entityValue;
    }

    public double getFitness() {
        return fitness;
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public List<byte[]> getChromosomesBytes() {
        return chromosomes.stream().map(c -> c.getBinary()).collect(Collectors.toList());
    }

    private List<Double> calculateValue() {
        // x = a + decimal(chromosome) * (b-a) / (2^m - 1)
//        return start + chromosome.getDecimalValue() * (stop - start) / (Math.pow(2, size) - 1);
        return chromosomes.stream().map(c -> start + c.getDecimalValue() * (stop - start) / (Math.pow(2, size) - 1)).collect(Collectors.toList());
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

    public boolean isElite() {
        return isElite;
    }

    public void setElite(boolean elite) {
        isElite = elite;
    }

    @Override
    public String toString() {
        return "Entity: " + chromosomes + " value: " + getValue();
    }

}
