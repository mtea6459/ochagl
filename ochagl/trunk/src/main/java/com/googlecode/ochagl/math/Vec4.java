package com.googlecode.ochagl.math;

public class Vec4 {
	public float x;

	public float y;

	public float z;

	public float w;

	public Vec4() {
		this(0, 0, 0, 0);
	}

	public Vec4(float x, float y, float z, float w) {
		set(x, y, z, w);
	}

	public Vec4(Vec4 v1) {
		set(v1);
	}

	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void set(Vec4 v1) {
		x = v1.x;
		y = v1.y;
		z = v1.z;
		w = v1.w;
	}

	public final void set(Vec3 v1) {
		set(v1.x, v1.y, v1.z, 0);
	}

	public void add(Vec4 v1, Vec4 v2) {
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		z = v1.z + v2.z;
		w = v1.w + v2.w;
	}

	public void add(Vec4 v1) {
		x += v1.x;
		y += v1.y;
		z += v1.z;
		w += v1.w;
	}

	public void sub(Vec4 v1, Vec4 v2) {
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		z = v1.z - v2.z;
		w = v1.w - v2.w;
	}

	public void sub(Vec4 v1) {
		x -= v1.x;
		y -= v1.y;
		z -= v1.z;
		w -= v1.w;
	}

	public void scale(float s, Vec4 v1) {
		x = s * v1.x;
		y = s * v1.y;
		z = s * v1.z;
		w = s * v1.w;
	}

	public void scale(float s) {
		x *= s;
		y *= s;
		z *= s;
		w *= s;
	}

	public boolean equals(Vec4 v1) {
		return v1 != null && x == v1.x && y == v1.y && z == v1.z && w == v1.w;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}

	public final void abs(Vec4 v) {
		set(v);
		abs();
	}

	public final void abs() {
		if (x < 0.0)
			x = -x;
		if (y < 0.0)
			y = -y;
		if (z < 0.0)
			z = -z;
		if (w < 0.0)
			w = -w;
	}

	public final float len2() {
		return x * x + y * y + z * z + w * w;
	}

	public final float len() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public final float dot(Vec4 v1) {
		return x * v1.x + y * v1.y + z * v1.z + w * v1.w;
	}

	public void normalize() {
		double d = len();
		x /= d;
		y /= d;
		z /= d;
		w /= d;
	}

	public final float angle(Vec4 v1) {
		double d = dot(v1);
		double v1_length = v1.len();
		double v_length = len();
		return (float) Math.acos(d / v1_length / v_length);
	}

	public float magnitude() {
		return x * x + y * y + z * z;
	}

	public float normalize2() {
		float sz = magnitude();
		if (sz <= 0.0000001f) {
			return 0;
		}
		float len = (float) Math.sqrt(sz);
		sz = 1.0f / len;
		scale(sz);
		return len;
	}
}