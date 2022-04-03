package com.ewolutionary.alg.impl;

import com.ewolutionary.alg.function.Functions;
import com.helger.commons.annotation.VisibleForTesting;

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
    private Double fitness;

    public Entity(int start, int stop, int precision, int xVariablesCount) {
        this.start = start;
        this.stop = stop;
        this.size = calculateSize(precision);
        this.chromosomes = generateChromosomes(size, xVariablesCount);
        this.entityValue = calculateValue();
    }

    public Entity(int start, int stop, List<byte[]> chromosomeData) {
        this.start = start;
        this.stop = stop;
        this.size = chromosomeData.get(0).length;
        this.chromosomes = chromosomeData.stream().map(Chromosome::new).collect(Collectors.toList());
        this.entityValue = calculateValue();
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

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public List<byte[]> getChromosomesBytes() {
        return chromosomes.stream().map(c -> c.getBinary()).collect(Collectors.toList());
    }

    private void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void setChromosomesBytes(List<byte[]> binaries) {
        if (binaries.size() == chromosomes.size()) {
            for (int i = 0; i < binaries.size(); i++) {
                chromosomes.get(i).setBinary(binaries.get(i));
            }
        }
        this.entityValue = calculateValue();
    }

    public Double getFitness() {
        fitness = calculateFitness();
        return fitness;
    }

    private List<Double> calculateValue() {
        // x = a + decimal(chromosome) * (b-a) / (2^m - 1)
        return chromosomes.stream().map(c -> start + c.getDecimalValue() * (stop - start) / (Math.pow(2, size) - 1)).collect(Collectors.toList());
    }

    private int calculateSize(int precision) {
        //(b-a) * 10^precision <= 2^m - 1
        double left = log2((stop - start) * Math.pow(10, precision)) + log2(1);

        return (int) Math.ceil(left);
    }

    public double calculateFitness() {
        return Functions.function(Solver.functionToSolve(), entityValue);
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

    @VisibleForTesting
    private void setEntityValue(List<Double> list) {
        this.entityValue = list;
    }

    @Override
    public String toString() {
        return "Entity " + chromosomes.toString() + "value: " + getValue();
    }

}
