package com.googlecode.ochagl.math;


/**
 * ’¼ü‚ğ‚ ‚ç‚í‚·.
 * P(t) = Q + tV
 */
public class Line {

    public Vec3 Q = new Vec3();
    public Vec3 V = new Vec3();

    public Line() {
    }
    public Line(Line line) {
        set(line);
    }
    public Line set(Line line) {
        return set(line.Q, line.V);
    }
    public Line set(Vec3 q, Vec3 v) {
        Q.set(q);
        V.set(v);
        return this;
    }
    public Vec3 getP(float t) {
        return new Vec3(Q).add( new Vec3().scale(t, V) );
    }

}
