/*
 * Created on 2005/01/08
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.googlecode.ochagl.math;


public class Quat {

	public float x;

	public float y;

	public float z;

	public float q;

	public void set(float x, float y, float z, float q) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.q = q;
	}

	public void set(Quat q1) {
		x = q1.x;
		y = q1.y;
		z = q1.z;
		q = q1.q;
	}

	public final void set(Mat4 m1) {
		setFromMat(m1.m00, m1.m01, m1.m02, m1.m10, m1.m11, m1.m12, m1.m20,
				m1.m21, m1.m22);
	}

	public final void set(Mat3 m1) {
		setFromMat(m1.m00, m1.m01, m1.m02, m1.m10, m1.m11, m1.m12, m1.m20,
				m1.m21, m1.m22);
	}


	public void add(Quat q1) {
		x += q1.x;
		y += q1.y;
		z += q1.z;
		q += q1.q;
	}

	public void sub(Quat q1) {
		x -= q1.x;
		y -= q1.y;
		z -= q1.z;
		q -= q1.q;
	}

	public final void mul(Quat q1, Quat q2) {
		set(q1.x * q2.q + q1.q * q2.x + q1.y * q2.z - q1.z * q2.y, q1.y * q2.q
				+ q1.q * q2.y + q1.z * q2.x - q1.x * q2.z, q1.z * q2.q + q1.q
				* q2.z + q1.x * q2.y - q1.y * q2.x, q1.q * q2.q - q1.x * q2.x
				- q1.y * q2.y - q1.z * q2.z);
	}

	public final void mul(Quat q1) {
		set(x * q1.q + q * q1.x + y * q1.z - z * q1.y, y * q1.q + q * q1.y + z
				* q1.x - x * q1.z, z * q1.q + q * q1.z + x * q1.y - y * q1.x, q
				* q1.q - x * q1.x - y * q1.y - z * q1.z);
	}

	public void scale(float s) {
		x *= s;
		y *= s;
		z *= s;
		q *= s;
	}

	public final void conjugate(Quat q1) {
		x = -q1.x;
		y = -q1.y;
		z = -q1.z;
		q = q1.q;
	}

	public final void conjugate() {
		x = -x;
		y = -y;
		z = -z;
	}

	public final void normalize(Quat q1) {
		float n = (float) Math.sqrt(q1.norm());
		// zero-div may occur.
		x = (float) (q1.x / n);
		y = (float) (q1.y / n);
		z = (float) (q1.z / n);
		q = (float) (q1.q / n);
	}

	public void normalize() {
		float n = (float) Math.sqrt(norm());
		// zero-div may occur.
		x /= n;
		y /= n;
		z /= n;
		q /= n;
	}

	private void setFromMat(float m00, float m01, float m02, float m10,
			float m11, float m12, float m20, float m21, float m22) {
		// From Ken Shoemake
		// (ftp://ftp.cis.upenn.edu/pub/graphics/shoemake)

		float s;
		float tr = m00 + m11 + m22;
		if (tr >= 0.0) {
			s = (float) Math.sqrt(tr + 1.0);
			q = (float) (s * 0.5);
			s = 0.5f / s;
			x = (float) ((m21 - m12) * s);
			y = (float) ((m02 - m20) * s);
			z = (float) ((m10 - m01) * s);
		} else {
			float max = Math.max(Math.max(m00, m11), m22);
			if (max == m00) {
				s = (float) Math.sqrt(m00 - (m11 + m22) + 1.0);
				x = (float) (s * 0.5);
				s = 0.5f / s;
				y = (float) ((m01 + m10) * s);
				z = (float) ((m20 + m02) * s);
				q = (float) ((m21 - m12) * s);
			} else if (max == m11) {
				s = (float) Math.sqrt(m11 - (m22 + m00) + 1.0);
				y = (float) (s * 0.5);
				s = 0.5f / s;
				z = (float) ((m12 + m21) * s);
				x = (float) ((m01 + m10) * s);
				q = (float) ((m02 - m20) * s);
			} else {
				s = (float) Math.sqrt(m22 - (m00 + m11) + 1.0);
				z = (float) (s * 0.5);
				s = 0.5f / s;
				x = (float) ((m20 + m02) * s);
				y = (float) ((m12 + m21) * s);
				q = (float) ((m10 - m01) * s);
			}
		}
	}

	// Prot = [pp + cosÉ∆pt + sinÉ∆(nÅ~pt), q]
	public void rotateAxis(Vec3 n, float theta) {
		float r = theta / 2;
		float cs = (float) Math.cos(r);
		float sn = (float) Math.sin(r);
		set(sn * n.x, sn * n.y, sn * n.z, cs);
	}

	private final float norm() {
		return x * x + y * y + z * z + q * q;
	}
}
