package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.selectors.configuration.RouletteSelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.BEST_PERCENTAGE;

public class RouletteSelector implements Selector {

    @Override
    public List<Entity> select(Population population, boolean isEliteStrategy, int percentOfBestToNextCentury, SelectorConfiguration configuration) {
        if (!(configuration instanceof RouletteSelectorConfiguration)) {
            throw new IllegalArgumentException("WrongConfiguration");
        }
        int amount2Select = (int) (population.getEntities().size() * (double) configuration.getConfigurations().get(BEST_PERCENTAGE));
        int elite2Select = (int) (population.getEntities().size() * (double) percentOfBestToNextCentury);
        List<Entity> selectedEntities = new ArrayList<>();
        List<Entity> entities = population
                .getEntities()
                .stream()
                .sorted(Comparator.comparingDouble(Entity::getFitness))
                .collect(Collectors.toList());
        if(isEliteStrategy) {
            selectedEntities.addAll(entities.subList(0, elite2Select));
            entities.removeAll(selectedEntities);
            amount2Select -= elite2Select;
        }

        double fitnessCount = entities.stream().mapToDouble(Entity::getFitness).sum();
        Map<Entity, Double> mapEntitiesProbability = new LinkedHashMap<>();

        AtomicReference<Double> entitiesFitness = new AtomicReference<>((double) 0);
        entities.forEach(e -> mapEntitiesProbability.put(e, entitiesFitness.updateAndGet(v -> v + e.getFitness() / fitnessCount)));

        while(amount2Select > 0) {
            double r = Math.random();
            Optional<Entity> selected = mapEntitiesProbability.entrySet().stream().filter(d -> (double) d.getValue() <= r).map(Map.Entry::getKey).findFirst();
            if(selected.isPresent()) {
                selectedEntities.add(selected.get());
                amount2Select--;
            }
        }

        return selectedEntities;
    }
}
