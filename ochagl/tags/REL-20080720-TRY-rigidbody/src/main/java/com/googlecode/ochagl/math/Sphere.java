/*
 * Created on 2005/01/08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.googlecode.ochagl.math;


/**
 * @author ocha
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Sphere {
    public Vec3 pos = new Vec3();
    public float r = 0;

    public Sphere() {
        this(0,0,0,1);
    }
    public Sphere(Vec3 v, float r) {
        this(v.x,v.y,v.z,r);
    }
    public Sphere(float x, float y, float z, float r) {
        pos.x = x;
        pos.y = y;
        pos.z = z;
        this.r = r;
    }
}
