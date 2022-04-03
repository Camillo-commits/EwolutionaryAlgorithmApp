package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Entity;
import com.ewolutionary.alg.impl.Population;
import com.ewolutionary.alg.impl.selectors.configuration.SelectorConfiguration;
import com.ewolutionary.alg.impl.selectors.configuration.TournamentSelectorConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.AMOUNT_OF_TOURNAMENTS;
import static com.ewolutionary.alg.impl.selectors.configuration.SelectorKeys.ENTITIES_IN_ONE_TOURNAMENT;

public class TournamentSelector implements Selector {

    private Random rnd;

    @Override
    public List<Entity> select(Population population, boolean isEliteStrategy, int percentOfBestToNextCentury, SelectorConfiguration configuration) {
        if (!(configuration instanceof TournamentSelectorConfiguration)) {
            throw new IllegalArgumentException("WrongConfiguration");
        }
        int entitiesInOneTournament = (int) configuration.getConfigurations().get(ENTITIES_IN_ONE_TOURNAMENT);
        int amountOfTournaments = (int) configuration.getConfigurations().get(AMOUNT_OF_TOURNAMENTS);

        List<Entity> pool = population.getEntities();
        for (int i = 0; i < amountOfTournaments; i++) {
            int numberOfTournaments = (int) Math.floor(pool.size() / entitiesInOneTournament);
            List<Entity> selected = new ArrayList<>();
            for (int j = 0; j < numberOfTournaments; j++)
                selected.add(tournament(pool, entitiesInOneTournament));
            pool = selected;
            if(selected.size() == 1) break;
        }
        population.setEntities(pool);
        return pool;
    }

    private Entity tournament(List<Entity> pool, int entitiesInOneTournament) {
        List<Entity> selectedForTournament = new ArrayList<>();
        for (int i = 0; i < entitiesInOneTournament; i++) {
            int index = rnd.nextInt(pool.size());
            selectedForTournament.add(pool.get(index));
            pool.remove(index);
        }
        Entity selectedEntity = selectedForTournament
                .stream()
                .sorted(Comparator.comparingDouble(Entity::getFitness))
                .collect(Collectors.toList()).get(0);
        return selectedEntity;
    }

    public TournamentSelector() {
        this.rnd = new Random();
    }
}
