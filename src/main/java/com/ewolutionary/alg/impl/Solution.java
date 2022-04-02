package com.ewolutionary.alg.impl;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Solution {
    Entity bestEntity;
    List<Entity> bestEntityInEachIteration;
    Integer numberOfIterations;
    long timeMilis;
}
