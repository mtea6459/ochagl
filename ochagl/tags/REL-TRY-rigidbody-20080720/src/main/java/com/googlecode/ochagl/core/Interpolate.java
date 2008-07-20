package com.googlecode.ochagl.core;

import java.util.ArrayList;
import java.util.List;

public class Interpolate {
    private List values_ = new ArrayList();

    private long duration_;

    private long startTime_;

    private boolean isLoop_;

    private float fraction_;

    public void clear() {
        values_.clear();
        fraction_ = 0.0f;
    }

    public void setValue(float key, float value) {
        for (int i = 0; i < values_.size(); i++) {
            KeyValue keyvalue = (KeyValue) values_.get(i);
            if (key == keyvalue.key) {
                keyvalue.value = value;
                return;
            }
        }
        values_.add(new KeyValue(key, value));
    }

    public void setDuration(long duration) {
        duration_ = duration;
    }

    public void setStartTime(long time) {
        startTime_ = time;
    }

    public void setLoop(boolean b) {
        isLoop_ = b;
    }

    public float getValue(long time) {
        long diffTime = time - startTime_;
        if (isLoop_) {
            diffTime = diffTime % duration_;
        }
        fraction_ = (float) diffTime / (float) duration_;
        if (fraction_ > 1.0) {
            return value(values_.size() - 1);
        }
        int eindex = getNextIndex(fraction_);
        int bindex = eindex - 1;
        if (bindex < 0) {
            return value(0);
        }
        //if (eindex == values_.size() - 1) {
        if (eindex == values_.size()) {
            bindex = eindex = eindex - 1;
        }
        return calcValue(bindex, eindex, fraction_);
    }

    public float getFraction() {
        return fraction_;
    }

    public float calcValue(int bindex, int eindex, float fraction) {
        return 0;
    }

    // fraction‚ª“–‚Ä‚Í‚Ü‚éŽŸindex‚ð•Ô‚·
    protected int getNextIndex(float fraction) {
        if (fraction < key(0)) {
            return 1;
        }
        for (int index = 0; index < values_.size(); index++) {
            if (fraction < key(index)) { 
                return index;
            }
        }
        return values_.size();
    }

    protected float key(int index) {
        float val = ((KeyValue) values_.get(index)).key;
        return ((KeyValue) values_.get(index)).key;
    }

    protected float value(int index) {
        return ((KeyValue) values_.get(index)).value;
    }

    static class KeyValue {
        public KeyValue(float key, float value) {
            this.key = key;
            this.value = value;
        }

        float key;

        float value;
    }

}
