package com.ewolutionary.alg.impl.crossers;

import com.ewolutionary.alg.impl.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearCrosser extends Crosser {
    @Override
    protected List<Entity> cross(Entity first, Entity second) {
        Entity z = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());
        Entity v = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());
        Entity w = new Entity(first.getStart(), first.getStop(), first.getXVariableCount());

        for (int i = 0; i < first.getChromosomes().size(); ++i) {
            double value1 = first.getChromosomesValues().get(i);
            double value2 = second.getChromosomesValues().get(i);

            setGene(z, i, 0.5 * (value1 + value2));
            setGene(v, i, 1.5 * value1 - 0.5 * value2);
            setGene(w, i, 1.5 * value2 - 0.5 * value1);
        }

        double zFitness = z.calculateFitness();
        double vFitness = v.calculateFitness();
        double wFitness = w.calculateFitness();

        double min = Math.min(Math.min(zFitness, vFitness), wFitness);

        List<Entity> result = new ArrayList<>();

        if (zFitness == min) {
            result.addAll(Arrays.asList(v, w));
        } else if (vFitness == min) {
            result.addAll(Arrays.asList(z, w));
        } else if (wFitness == min) {
            result.addAll(Arrays.asList(z, v));
        }

        return result;
    }

    private void setGene(Entity entity, int chromosomeNum, double value) {
        entity.getChromosomes()
                .get(chromosomeNum).setValue(value);

    }
}
