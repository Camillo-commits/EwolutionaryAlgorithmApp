package com.ewolutionary.alg.impl.crossers;

public class CrosserProvider {
    public static final CrosserOption defaultOption = CrosserOption.ARITHMETIC;

    public static Crosser getCrosser(CrosserOption option) {
        Crosser crosser;
        switch (option) {
            case ARITHMETIC:
                crosser = new ArithmeticCrosser();
                break;
            case LINEAR:
                crosser = new LinearCrosser();
                break;
            case BLX_A:
                crosser = new BlxACrosser();
                break;
            case BLX_A_B:
                crosser = new BlxABCrosser();
                break;
            case AVERAGE:
                crosser = new AverageCrosser();
                break;
            default: crosser = getCrosser(defaultOption);
        }
        return crosser;
    }
}