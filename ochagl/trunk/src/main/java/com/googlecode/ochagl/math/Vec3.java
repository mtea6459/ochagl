package com.googlecode.ochagl.math;

import java.text.DecimalFormat;

public class Vec3 {

	public static final Vec3 up = new Vec3(0, 1, 0);

	public static final Vec3 down = new Vec3(0, -1, 0);

	public static final Vec3 zero = new Vec3(0, 0, 0);

	public static final DecimalFormat df = new DecimalFormat("0.0####");

	public float x, y, z;

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3(Vec3 v1) {
		x = v1.x;
		y = v1.y;
		z = v1.z;
	}

	public Vec3() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	public final void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final void set(Vec3 v1) {
		x = v1.x;
		y = v1.y;
		z = v1.z;
	}

	public final void get(Vec3 t) {
		t.x = x;
		t.y = y;
		t.z = z;
	}

	public final Vec3 add(Vec3 v1, Vec3 v2) {
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		z = v1.z + v2.z;
		return this;
	}

	public final Vec3 add(Vec3 v1) {
		x += v1.x;
		y += v1.y;
		z += v1.z;
		return this;
	}

	public final Vec3 sub(Vec3 v1, Vec3 v2) {
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		z = v1.z - v2.z;
		return this;
	}

	public final void sub(Vec3 v1) {
		x -= v1.x;
		y -= v1.y;
		z -= v1.z;
	}

	public final void negate(Vec3 v1) {
		x = -v1.x;
		y = -v1.y;
		z = -v1.z;
	}

	public final void negate() {
		x = -x;
		y = -y;
		z = -z;
	}

	public final Vec3 scale(float s, Vec3 v1) {
		x = s * v1.x;
		y = s * v1.y;
		z = s * v1.z;
		return this;
	}

	public final Vec3 scale(float s) {
		x *= s;
		y *= s;
		z *= s;
		return this;
	}

	public boolean equals(Vec3 v1) {
		return v1 != null && x == v1.x && y == v1.y && z == v1.z;
	}

	public final void abs(Vec3 t) {
		set(t);
		abs();
	}

	public final void abs() {
		if (x < 0.0)
			x = -x;
		if (y < 0.0)
			y = -y;
		if (z < 0.0)
			z = -z;
	}

	public final float len2() {
		return x * x + y * y + z * z;
	}

	public final float len() {
		return (float) Math.sqrt(len2());
	}

	public final Vec3 cross(Vec3 v1, Vec3 v2) {
		// store in tmp once for aliasing-safty
		// i.e. safe when a.cross(a, b)
		set(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y
				- v1.y * v2.x);
		return this;
	}

	public final float dot(Vec3 v1) {
		return x * v1.x + y * v1.y + z * v1.z;
	}

	public final float angle(Vec3 v1) {
		// return (double)Math.acos(dot(v1)/v1.length()/v.length());
		// Numerically, near 0 and PI are very bad condition for acos.
		// In 3-space, |atan2(sin,cos)| is much stable.

		double xx = y * v1.z - z * v1.y;
		double yy = z * v1.x - x * v1.z;
		double zz = x * v1.y - y * v1.x;
		double cross = Math.sqrt(xx * xx + yy * yy + zz * zz);

		return (float) Math.abs(Math.atan2(cross, dot(v1)));
	}

	public float normalize(Vec3 v) {
		set(v);
		return normalize();
	}

	public float normalize() {
		float sz = len2();
		if (sz <= 0.0000001f) {
			return 0;
		}
		float len = (float) Math.sqrt(sz);
		sz = 1.0f / len;
		scale(sz);
		return len;
	}

	// ノルム
	public float norm() {
		float sz = len2();
		if (sz <= 0.0000001f) {
			return 0;
		}
		return (float) Math.sqrt(sz);
	}

	// 大きさベクトルの飽和処理
	// TODO テンプベクトルをどうにかする
	public void turncate(float max) {
		Vec3 tmp = new Vec3();
		tmp.set(this);
		float dist = tmp.normalize();
		if (dist == 0)
			return;

		if (dist > max) {
			tmp.scale(max);
			set(tmp);
		}
	}

	public String toString() {
		return df.format(x) + ", " + df.format(y) + ", " + df.format(z);
	}
}