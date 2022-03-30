package com.ewolutionary.alg.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Chromosome {

    private byte[] binary;

    public Chromosome(int size) {
        binary = new byte[size];
        fillBinary();
    }

    public Chromosome(byte[] binary) {
        this.binary = binary;
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public int getDecimalValue() {
        int sum = 0;
        int pow = 1;
        for(int i=binary.length-1; i>=0; i--) {
            sum += binary[i] * pow;
            pow *= 2;
        }
        return sum;
    }

    private void fillBinary() {
        for(int i=0; i<binary.length; i++) {
            binary[i] = Math.random() >= 0.5 ? (byte)1 : (byte)0 ;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(binary);
    }

}
