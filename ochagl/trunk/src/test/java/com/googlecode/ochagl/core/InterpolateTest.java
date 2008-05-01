package com.googlecode.ochagl.core;

import com.googlecode.ochagl.core.Interpolate;
import com.googlecode.ochagl.core.InterpolateStep;

import junit.framework.TestCase;

/**
 * 複数のデータの線形補間を行うクラスのテスト
 */
public class InterpolateTest extends TestCase {


    public void setUp() {
    }

    public void testGetNextIndex() {
        Interpolate a = new Interpolate();
        a.setValue(0.0f, 1.0f);
        a.setValue(0.5f, 2.0f);
        a.setValue(1.0f, 3.0f);
        a.setDuration(60);
        a.setLoop(false);
        assertEquals(1, a.getNextIndex(-1.0f));
        assertEquals(1, a.getNextIndex(0.0f));
        assertEquals(1, a.getNextIndex(0.49f));
        assertEquals(2, a.getNextIndex(0.5f));
        assertEquals(2, a.getNextIndex(0.6f));
        assertEquals(2, a.getNextIndex(0.99f));
        assertEquals(3, a.getNextIndex(1.0f));
        assertEquals(3, a.getNextIndex(1.11f));
    }

    public void testStep() {
        Interpolate a = new InterpolateStep();
        a.setValue(0.0f, 1.0f);
        a.setValue(0.5f, 2.0f);
        a.setValue(1.0f, 3.0f);
        a.setDuration(60);
        a.setLoop(false);
        assertEquals(1.0f, a.getValue(time(-1.0f)));
        assertEquals(1.0f, a.getValue(time(0.0f)));
        assertEquals(1.0f, a.getValue(time(0.2f)));
        assertEquals(2.0f, a.getValue(time(0.5f)));
        assertEquals(2.0f, a.getValue(time(0.6f)));
        assertEquals(3.0f, a.getValue(time(1.0f)));
        assertEquals(3.0f, a.getValue(time(1.1f)));
    }

    private int time(float ratio) {
        return (int) (60 * ratio);
    }
}
