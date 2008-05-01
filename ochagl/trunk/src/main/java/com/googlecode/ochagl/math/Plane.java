package com.googlecode.ochagl.math;


/**
 * Ax + By + Cz + D = 0<br>
 * は、<br>
 * Nx * Qx + Ny * Qy + Nz * Qz + D = 0<br>
 * に対応する。<br>
 * D は、数学と同じ原点からの距離（マイナス符号付）を表す。<br>
 */
public class Plane {

    public Vec3 N = new Vec3(); // 法線
    public Vec3 Q = new Vec3(); // 平面上の点
    public float D = 0;         // 符号付距離

    public Plane() {
    }

    public Plane(Vec3[] vs) {
        set(vs);
    }

    public boolean set(Vec3[] vs) {

        Vec3 v1 = new Vec3();
        Vec3 v2 = new Vec3();

        Q.set(vs[0]);

        // 0
        // 1 2
        // 法線は手前にのびる
        v1.sub(vs[0], vs[1]);
        v2.sub(vs[2], vs[1]);
        N.cross(v2, v1);
        if (N.normalize() == 0)
            return false;
        D = - N.dot(vs[0]);

        return true;
    }

    public void set(Vec3 q, Vec3 n) {
        N.set(n);
        Q.set(q);
        D = - N.dot(Q);
    }

    public void recalc() {
        D = - N.dot(Q);
    }

    public boolean isFront(Vec3 v) {
        return v.dot(N) + D >= 0;
    }
}
