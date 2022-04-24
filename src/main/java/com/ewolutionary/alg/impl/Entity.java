package com.ewolutionary.alg.impl;

import com.ewolutionary.alg.function.Functions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Entity {

    private int size;
    private List<Chromosome> chromosomes;
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
    }

    public Entity() {
    }

    private List<Chromosome> generateChromosomes(int start, int stop, int count) {
        return IntStream.range(0, count).mapToObj(i -> new Chromosome(start, stop)).collect(Collectors.toList());
    }

    public List<Double> getValues() {
        return getChromosomesValues();
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
    }

    public int getSize() {
        return xVariableCount;
    }

    public Double getFitness() {
        fitness = calculateFitness();
        return fitness;
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

    @Override
    public String toString() {
        return "Entity " + chromosomes.toString();
    }

}
