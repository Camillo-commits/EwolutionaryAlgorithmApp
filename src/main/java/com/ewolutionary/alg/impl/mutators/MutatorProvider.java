package com.ewolutionary.alg.impl.mutators;

public class MutatorProvider {
    public static final MutatorOption defaultOption = MutatorOption.ONE_POINT;

    public static Mutator getMutator(MutatorOption option) {
        Mutator mutator;
        switch (option) {
            case BOUNDARY:
                mutator = new BoundaryMutator();
                break;
            case ONE_POINT:
                mutator = new OnePointMutator();
                break;
            case TWO_POINT:
                mutator = new TwoPointMutator();
                break;
            default: mutator = getMutator(defaultOption);
        }
        return mutator;
    }
}
