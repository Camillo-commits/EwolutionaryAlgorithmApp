package com.ewolutionary.alg.impl.selectors;

import com.ewolutionary.alg.impl.Solution;

import java.util.List;

public interface Selector {
    public List<Solution> select(List<Solution> individuals, boolean isEliteStrategy);

    public Solution findBestSolution(List<Solution> individuals);
}
