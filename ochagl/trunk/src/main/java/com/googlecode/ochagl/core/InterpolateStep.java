package com.googlecode.ochagl.core;



/**
 * 複数のデータ集合から現在時刻を基に該当データを返すクラス。
 */
public class InterpolateStep extends Interpolate {
    public float calcValue(int bindex, int eindex, float fraction) {
        return value(bindex);
    }
}
