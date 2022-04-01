package com.ewolutionary.alg.impl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Solution {
    Entity bestEntity;
    Integer numberOfIterations;
    long timeMilis;
}
