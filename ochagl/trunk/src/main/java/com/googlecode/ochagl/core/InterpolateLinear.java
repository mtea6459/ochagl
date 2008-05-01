package com.googlecode.ochagl.core;


public class InterpolateLinear extends Interpolate {
    public float calcValue(int bindex, int eindex, float fraction) {
        float a = value(eindex) - value(bindex);
        float b = key(eindex) - key(bindex);
        if (b == 0.0f) return value(eindex); 
        float slope = a / b;
        float past = fraction - key(bindex);
        return slope * past + value(bindex);
    }
}
