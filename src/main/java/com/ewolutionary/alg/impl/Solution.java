package com.ewolutionary.alg.impl;

import lombok.Getter;

@Getter
public class Solution {
    private byte[] binary;
    private Double x;
    private Double y;

    public Solution(byte[] binary) {
        this.binary = binary;
    }

    public void calculateValue() {
        //TODO implement real function
        if (x == null) {
            convertBinaryToValue();
        }
        function();
    }

    public void convertBinaryToValue(){
        //TODO implement real conversion
        this.x = 10.0;
    }

    private void function(){
        this.y = 10 * x;
    }
}
