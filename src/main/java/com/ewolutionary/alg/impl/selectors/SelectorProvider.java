package com.ewolutionary.alg.impl.selectors;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SelectorProvider {
    public static SelectorOption defaultOption = SelectorOption.BEST;

    public static Selector getSelector(SelectorOption option) {
        Selector selector;
        switch (option){
            case BEST:
                selector = new BestSelector();
                break;
            case ROULETTE:
                selector = new RouletteSelector();
                break;
            case TOURNAMENT:
                selector = new TournamentSelector();
                break;
            default: selector = getSelector(defaultOption);
        }
        return selector;
    }
}
