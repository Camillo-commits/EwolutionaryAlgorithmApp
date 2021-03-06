package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.function.Functions;
import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.BEST_PERCENTAGE;

public class RouletteSelector implements Selector {
    //TODO add function minimization method path
    @Override
    public List<Entity> select(Population population, boolean isEliteStrategy, int percentOfBestToNextCentury, SelectorConfiguration configuration) {
        if (!(configuration instanceof RouletteSelectorConfiguration)) {
            throw new IllegalArgumentException("WrongConfiguration");
        }
        int amount2Select = (int) (population.getEntities().size() * (double) configuration.getConfigurations().get(BEST_PERCENTAGE));
        int elite2Select = (int) (population.getEntities().size() * (double) percentOfBestToNextCentury/100);
        List<Entity> selectedEntities = new ArrayList<>();
        List<Entity> entities = population
                .getEntities()
                .stream()
                .sorted(Comparator.comparingDouble(Entity::calculateFitness))
                .collect(Collectors.toList());
        if(isEliteStrategy) {
            if(Functions.MINIMUM) {
                selectedEntities.addAll(entities.subList(entities.size()-elite2Select, entities.size()));
            } else {
                selectedEntities.addAll(entities.subList(0, elite2Select));
            }
            entities.removeAll(selectedEntities);
            amount2Select -= elite2Select;
        }

        double fitnessCount;
        if(Functions.MINIMUM) {
            fitnessCount = entities.stream().mapToDouble(e -> (1./ e.getFitness())).sum();
        } else {
            fitnessCount = entities.stream().mapToDouble(Entity::getFitness).sum();
        }

        Map<Entity, Double> mapEntitiesProbability = new LinkedHashMap<>();

        double entitiesFitness = 0;
        double entityFitness = 0;
        for(Entity e: entities) {
            if(Functions.MINIMUM) {
                entityFitness = 1./e.getFitness() / fitnessCount;
            } else {
                entityFitness = e.getFitness() / fitnessCount;
            }
            entitiesFitness += entityFitness;
            mapEntitiesProbability.put(e, entitiesFitness);
        }

        while(amount2Select > 0) {
            double r = Math.random();
            List<Entity> selected = mapEntitiesProbability.entrySet().stream().filter(e -> (double) e.getValue() >= r).map(Map.Entry::getKey).collect(Collectors.toList());
            selectedEntities.add(selected.get(0));
            amount2Select--;
        }

        return selectedEntities;
    }
}
