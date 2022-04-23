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
    private int start;
    private int stop;
    private boolean isElite;
    private Double fitness;
    private int xVariableCount;

    public Entity(int start, int stop, int xVariablesCount) {
        this.start = start;
        this.stop = stop;
        this.xVariableCount = xVariablesCount;
        this.chromosomes = generateChromosomes(start, stop, xVariablesCount);
        this.entityValue = calculateValue();
    }

    public Entity() {
    }

    private List<Chromosome> generateChromosomes(int start, int stop, int count) {
        return IntStream.range(0, count).mapToObj(i -> new Chromosome(start, stop)).collect(Collectors.toList());
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public List<Double> getChromosomesValues() {
        return chromosomes.stream().map(Chromosome::getValue).collect(Collectors.toList());
    }

    private void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public void setChromosomesValues(List<Double> values) {
        if (values.size() == chromosomes.size()) {
            for (int i = 0; i < values.size(); i++) {
                chromosomes.get(i).setValue(values.get(i));
            }
        }
        this.entityValue = calculateValue();
    }

    public int getSize() {
        return xVariableCount;
    }

    public Double getFitness() {
        fitness = calculateFitness();
        return fitness;
    }

    private List<Double> calculateValue() {
        // x = a + decimal(chromosome) * (b-a) / (2^m - 1)
        return chromosomes.stream().map(c -> start + c.getValue() * (stop - start) / (Math.pow(2, size) - 1)).collect(Collectors.toList());
    }

    private int calculateSize(int precision) {
        //(b-a) * 10^precision <= 2^m - 1
        double left = log2((stop - start) * Math.pow(10, precision)) + log2(1);

        return (int) Math.ceil(left);
    }

    public double calculateFitness() {
        return Functions.function(Solver.functionToSolve(), getChromosomesValues());
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

    public int getXVariableCount() {
        return xVariableCount;
    }

    @VisibleForTesting
    private void setEntityValue(List<Double> list) {
        this.entityValue = list;
    }

    @Override
    public String toString() {
        return "Entity " + chromosomes.toString();
    }

}
