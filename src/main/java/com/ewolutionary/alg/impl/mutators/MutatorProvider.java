package com.ewolutionary.alg.impl.mutators;

public class MutatorProvider {
    public static final MutatorOption defaultOption = MutatorOption.GAUSS;

    public static Mutator getMutator(MutatorOption option) {
        Mutator mutator;
        switch (option) {
            case GAUSS:
                mutator = new GaussMutator();
                break;
            case EQUINOX:
                mutator = new EquinoxMutator();
                break;
            default: mutator = getMutator(defaultOption);
        }
        return mutator;
    }
}
