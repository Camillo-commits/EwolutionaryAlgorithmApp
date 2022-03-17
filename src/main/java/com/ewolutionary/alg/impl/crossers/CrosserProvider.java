package com.ewolutionary.alg.impl.crossers;

public class CrosserProvider {
    public static final CrosserOption defaultOption = CrosserOption.ONE_POINT;

    public static Crosser getCrosser(CrosserOption option) {
        Crosser crosser;
        switch (option) {
            case ONE_POINT:
                crosser = new OnePointCrosser();
                break;
            case TWO_POINT:
                crosser = new TwoPointCrosser();
                break;
            case THREE_POINT:
                crosser = new ThreePointCrosser();
                break;
            case HOMOGENEOUS:
                crosser = new HomogeneousCrosser();
                break;
            default: crosser = getCrosser(defaultOption);
        }
        return crosser;
    }
}