package com.ewolutionary.alg.impl.selectors.configuration;

import java.util.HashMap;

public class TournamentSelectorConfiguration extends SelectorConfiguration {
    public TournamentSelectorConfiguration(int entitiesInTournament, int amountOfTournaments) {
        this.configurations = new HashMap<>();
        this.configurations.put(SelectorKeys.ENTITIES_IN_ONE_TOURNAMENT, entitiesInTournament);
        this.configurations.put(SelectorKeys.AMOUNT_OF_TOURNAMENTS, amountOfTournaments);
    }
}
