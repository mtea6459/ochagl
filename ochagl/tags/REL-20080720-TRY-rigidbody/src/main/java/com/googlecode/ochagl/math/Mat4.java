package com.googlecode.ochagl.math;

public class Mat4 {
	public static final Mat4 imat = new Mat4();

	public float m00, m01, m02, m03;

	public float m10, m11, m12, m13;

	public float m20, m21, m22, m23;

	public float m30, m31, m32, m33;

	public Mat4() {
		setIdentity();
	}

	public Mat4(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30,
				m31, m32, m33);
	}

	public Mat4(Mat4 m1) {
		set(m1);
	}

	public String toString() {
		String nl = System.getProperty("line.separator");
		return "[" + nl + "  [" + m00 + "\t" + m01 + "\t" + m02 + "\t" + m03
				+ "]" + nl + "  [" + m10 + "\t" + m11 + "\t" + m12 + "\t" + m13
				+ "]" + nl + "  [" + m20 + "\t" + m21 + "\t" + m22 + "\t" + m23
				+ "]" + nl + "  [" + m30 + "\t" + m31 + "\t" + m32 + "\t" + m33
				+ "] ]";
	}

	public void set(float m00, float m01, float m02, float m03, float m10,
			float m11, float m12, float m13, float m20, float m21, float m22,
			float m23, float m30, float m31, float m32, float m33) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}

	public final void set(Mat4 m1) {
		m00 = m1.m00;
		m01 = m1.m01;
		m02 = m1.m02;
		m03 = m1.m03;
		m10 = m1.m10;
		m11 = m1.m11;
		m12 = m1.m12;
		m13 = m1.m13;
		m20 = m1.m20;
		m21 = m1.m21;
		m22 = m1.m22;
		m23 = m1.m23;
		m30 = m1.m30;
		m31 = m1.m31;
		m32 = m1.m32;
		m33 = m1.m33;
	}

	public void set(Vec3 x, Vec3 y, Vec3 z, Vec3 t) {
		set(x.x, y.x, z.x, t.x, x.y, y.y, z.y, t.y, x.z, y.z, z.z, t.z, 0, 0,
				0, 1);
	}

	public void set(Vec3 x, Vec3 y, Vec3 z) {
		set(x, y, z, Vec3.zero);
	}

	public final void set(Quat q1) {
		setFromQuat(q1.x, q1.y, q1.z, q1.q);
	}

	public final void set(Mat3 m1) {
		m00 = m1.m00;
		m01 = m1.m01;
		m02 = m1.m02;
		m03 = 0.0f;
		m10 = m1.m10;
		m11 = m1.m11;
		m12 = m1.m12;
		m13 = 0.0f;
		m20 = m1.m20;
		m21 = m1.m21;
		m22 = m1.m22;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void set(float scale) {
		m00 = scale;
		m01 = 0.0f;
		m02 = 0.0f;
		m03 = 0.0f;
		m10 = 0.0f;
		m11 = scale;
		m12 = 0.0f;
		m13 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = scale;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void setIdentity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m03 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m13 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void setRotation(Mat3 m1) {
		setRotationScale(m1);
	}

	public final void setRotation(Quat q1) {
		float tx = m03;
		float ty = m13;
		float tz = m23;
		float w0 = m30;
		float w1 = m31;
		float w2 = m32;
		float w3 = m33;

		set(q1);

		m03 = tx;
		m13 = ty;
		m23 = tz;
		m30 = w0;
		m31 = w1;
		m32 = w2;
		m33 = w3;
	}

	public void setTranslation(Vec3 trans) {
		m03 = trans.x;
		m13 = trans.y;
		m23 = trans.z;
	}

	public final void setRotationScale(Mat3 m1) {
		m00 = m1.m00;
		m01 = m1.m01;
		m02 = m1.m02;
		m10 = m1.m10;
		m11 = m1.m11;
		m12 = m1.m12;
		m20 = m1.m20;
		m21 = m1.m21;
		m22 = m1.m22;
	}

	public final void rotX(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m03 = 0.0f;
		m10 = 0.0f;
		m11 = c;
		m12 = -s;
		m13 = 0.0f;
		m20 = 0.0f;
		m21 = s;
		m22 = c;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void rotY(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = c;
		m01 = 0.0f;
		m02 = s;
		m03 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m13 = 0.0f;
		m20 = -s;
		m21 = 0.0f;
		m22 = c;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void rotZ(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = c;
		m01 = -s;
		m02 = 0.0f;
		m03 = 0.0f;
		m10 = s;
		m11 = c;
		m12 = 0.0f;
		m13 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
		m23 = 0.0f;
		m30 = 0.0f;
		m31 = 0.0f;
		m32 = 0.0f;
		m33 = 1.0f;
	}

	public final void add(Mat4 m1, Mat4 m2) {
		set(m1);
		add(m2);
	}

	public final void add(Mat4 m1) {
		m00 += m1.m00;
		m01 += m1.m01;
		m02 += m1.m02;
		m03 += m1.m03;
		m10 += m1.m10;
		m11 += m1.m11;
		m12 += m1.m12;
		m13 += m1.m13;
		m20 += m1.m20;
		m21 += m1.m21;
		m22 += m1.m22;
		m23 += m1.m23;
		m30 += m1.m30;
		m31 += m1.m31;
		m32 += m1.m32;
		m33 += m1.m33;
	}

	public final void sub(Mat4 m1, Mat4 m2) {
		// note this is alias safe.
		set(m1.m00 - m2.m00, m1.m01 - m2.m01, m1.m02 - m2.m02, m1.m03 - m2.m03,
				m1.m10 - m2.m10, m1.m11 - m2.m11, m1.m12 - m2.m12, m1.m13
						- m2.m13, m1.m20 - m2.m20, m1.m21 - m2.m21, m1.m22
						- m2.m22, m1.m23 - m2.m23, m1.m30 - m2.m30, m1.m31
						- m2.m31, m1.m32 - m2.m32, m1.m33 - m2.m33);
	}

	public final void sub(Mat4 m1) {
		m00 -= m1.m00;
		m01 -= m1.m01;
		m02 -= m1.m02;
		m03 -= m1.m03;
		m10 -= m1.m10;
		m11 -= m1.m11;
		m12 -= m1.m12;
		m13 -= m1.m13;
		m20 -= m1.m20;
		m21 -= m1.m21;
		m22 -= m1.m22;
		m23 -= m1.m23;
		m30 -= m1.m30;
		m31 -= m1.m31;
		m32 -= m1.m32;
		m33 -= m1.m33;
	}

	public final void mul(float scalar) {
		m00 *= scalar;
		m01 *= scalar;
		m02 *= scalar;
		m03 *= scalar;
		m10 *= scalar;
		m11 *= scalar;
		m12 *= scalar;
		m13 *= scalar;
		m20 *= scalar;
		m21 *= scalar;
		m22 *= scalar;
		m23 *= scalar;
		m30 *= scalar;
		m31 *= scalar;
		m32 *= scalar;
		m33 *= scalar;
	}

	public final void mul(Mat4 m1) {
		mul(this, m1);
	}

	public final void mul(Mat4 m1, Mat4 m2) {
		// alias-safe way.
		set(m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03
				* m2.m30, m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21
				+ m1.m03 * m2.m31, m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02
				* m2.m22 + m1.m03 * m2.m32, m1.m00 * m2.m03 + m1.m01 * m2.m13
				+ m1.m02 * m2.m23 + m1.m03 * m2.m33,

		m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30,
				m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13
						* m2.m31, m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12
						* m2.m22 + m1.m13 * m2.m32, m1.m10 * m2.m03 + m1.m11
						* m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33,

				m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23
						* m2.m30, m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22
						* m2.m21 + m1.m23 * m2.m31, m1.m20 * m2.m02 + m1.m21
						* m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32, m1.m20
						* m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23
						* m2.m33,

				m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33
						* m2.m30, m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32
						* m2.m21 + m1.m33 * m2.m31, m1.m30 * m2.m02 + m1.m31
						* m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32, m1.m30
						* m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33
						* m2.m33);
	}

	public final void invert(Mat4 m1) {
		set(m1);
		invert();
	}

	public final void invert() {
		float s = determinant();
		if (s == 0.0)
			s = 1.0f;
		s = 1 / s;
		// alias-safe way.
		// less *,+,- calculation than expanded expression.
		set(m11 * (m22 * m33 - m23 * m32) + m12 * (m23 * m31 - m21 * m33) + m13
				* (m21 * m32 - m22 * m31), m21 * (m02 * m33 - m03 * m32) + m22
				* (m03 * m31 - m01 * m33) + m23 * (m01 * m32 - m02 * m31), m31
				* (m02 * m13 - m03 * m12) + m32 * (m03 * m11 - m01 * m13) + m33
				* (m01 * m12 - m02 * m11), m01 * (m13 * m22 - m12 * m23) + m02
				* (m11 * m23 - m13 * m21) + m03 * (m12 * m21 - m11 * m22),

		m12 * (m20 * m33 - m23 * m30) + m13 * (m22 * m30 - m20 * m32) + m10
				* (m23 * m32 - m22 * m33), m22 * (m00 * m33 - m03 * m30) + m23
				* (m02 * m30 - m00 * m32) + m20 * (m03 * m32 - m02 * m33), m32
				* (m00 * m13 - m03 * m10) + m33 * (m02 * m10 - m00 * m12) + m30
				* (m03 * m12 - m02 * m13), m02 * (m13 * m20 - m10 * m23) + m03
				* (m10 * m22 - m12 * m20) + m00 * (m12 * m23 - m13 * m22),

		m13 * (m20 * m31 - m21 * m30) + m10 * (m21 * m33 - m23 * m31) + m11
				* (m23 * m30 - m20 * m33), m23 * (m00 * m31 - m01 * m30) + m20
				* (m01 * m33 - m03 * m31) + m21 * (m03 * m30 - m00 * m33), m33
				* (m00 * m11 - m01 * m10) + m30 * (m01 * m13 - m03 * m11) + m31
				* (m03 * m10 - m00 * m13), m03 * (m11 * m20 - m10 * m21) + m00
				* (m13 * m21 - m11 * m23) + m01 * (m10 * m23 - m13 * m20),

		m10 * (m22 * m31 - m21 * m32) + m11 * (m20 * m32 - m22 * m30) + m12
				* (m21 * m30 - m20 * m31), m20 * (m02 * m31 - m01 * m32) + m21
				* (m00 * m32 - m02 * m30) + m22 * (m01 * m30 - m00 * m31), m30
				* (m02 * m11 - m01 * m12) + m31 * (m00 * m12 - m02 * m10) + m32
				* (m01 * m10 - m00 * m11), m00 * (m11 * m22 - m12 * m21) + m01
				* (m12 * m20 - m10 * m22) + m02 * (m10 * m21 - m11 * m20));

		mul(s);
	}

	public final float determinant() {
		// less *,+,- calculation than expanded expression.
		return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
				- (m00 * m12 - m02 * m10) * (m21 * m33 - m23 * m31)
				+ (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
				+ (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
				- (m01 * m13 - m03 * m11) * (m20 * m32 - m22 * m30)
				+ (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);

	}

	public final void transpose() {
		float tmp = m01;
		m01 = m10;
		m10 = tmp;

		tmp = m02;
		m02 = m20;
		m20 = tmp;

		tmp = m03;
		m03 = m30;
		m30 = tmp;

		tmp = m12;
		m12 = m21;
		m21 = tmp;

		tmp = m13;
		m13 = m31;
		m31 = tmp;

		tmp = m23;
		m23 = m32;
		m32 = tmp;
	}

	public void normalize() {
		float l = 0;
		l = m00 * m00 + m10 * m10 + m20 * m20;
		if (l != 0)
			l = (float) Math.sqrt(l);
		m00 /= l;
		m10 /= l;
		m20 /= l;

		l = m01 * m01 + m11 * m11 + m21 * m21;
		if (l != 0)
			l = (float) Math.sqrt(l);
		m01 /= l;
		m11 /= l;
		m21 /= l;

		l = m02 * m02 + m12 * m12 + m22 * m22;
		if (l != 0)
			l = (float) Math.sqrt(l);
		m02 /= l;
		m12 /= l;
		m22 /= l;
	}

	public final void transform(Vec3 v, Vec3 out) {
		out.set(m00 * v.x + m01 * v.y + m02 * v.z + m03, m10 * v.x + m11 * v.y
				+ m12 * v.z + m13, m20 * v.x + m21 * v.y + m22 * v.z + m23);
	}

	public final void transform(Vec3 v) {
		transform(v, v);
	}

	public final void transform2(Vec3 v, Vec3 out) {
		out.set(m00 * v.x + m01 * v.y + m02 * v.z, m10 * v.x + m11 * v.y + m12
				* v.z, m20 * v.x + m21 * v.y + m22 * v.z);
	}

	public final void transform2(Vec3 v) {
		transform2(v, v);
	}

	public void transform(Plane p) {
		transform(p.Q);
		transform2(p.N);
		p.recalc();
	}

	public void transform(Line l) {
		transform(l.Q);
		transform2(l.V);
	}

	public boolean equals(Mat4 m1) {
		return m1 != null && m00 == m1.m00 && m01 == m1.m01 && m02 == m1.m02
				&& m03 == m1.m03 && m10 == m1.m10 && m11 == m1.m11
				&& m12 == m1.m12 && m13 == m1.m13 && m20 == m1.m20
				&& m21 == m1.m21 && m22 == m1.m22 && m23 == m1.m23
				&& m30 == m1.m30 && m31 == m1.m31 && m32 == m1.m32
				&& m33 == m1.m33;
	}

	public boolean epsilonEquals(Mat4 m1, float epsilon) {
		// why epsilon is float ??
		return Math.abs(m00 - m1.m00) <= epsilon
				&& Math.abs(m01 - m1.m01) <= epsilon
				&& Math.abs(m02 - m1.m02) <= epsilon
				&& Math.abs(m03 - m1.m03) <= epsilon

				&& Math.abs(m10 - m1.m10) <= epsilon
				&& Math.abs(m11 - m1.m11) <= epsilon
				&& Math.abs(m12 - m1.m12) <= epsilon
				&& Math.abs(m13 - m1.m13) <= epsilon

				&& Math.abs(m20 - m1.m20) <= epsilon
				&& Math.abs(m21 - m1.m21) <= epsilon
				&& Math.abs(m22 - m1.m22) <= epsilon
				&& Math.abs(m23 - m1.m23) <= epsilon

				&& Math.abs(m30 - m1.m30) <= epsilon
				&& Math.abs(m31 - m1.m31) <= epsilon
				&& Math.abs(m32 - m1.m32) <= epsilon
				&& Math.abs(m33 - m1.m33) <= epsilon;
	}

	private void setFromQuat(float x, float y, float z, float w) {
		float n = x * x + y * y + z * z + w * w;
		float s = (n > 0.0f) ? (2.0f / n) : 0.0f;

		float xs = x * s, ys = y * s, zs = z * s;
		float wx = w * xs, wy = w * ys, wz = w * zs;
		float xx = x * xs, xy = x * ys, xz = x * zs;
		float yy = y * ys, yz = y * zs, zz = z * zs;

		setIdentity();
		m00 = (float) (1.0 - (yy + zz));
		m01 = (float) (xy - wz);
		m02 = (float) (xz + wy);
		m10 = (float) (xy + wz);
		m11 = (float) (1.0 - (xx + zz));
		m12 = (float) (yz - wx);
		m20 = (float) (xz - wy);
		m21 = (float) (yz + wx);
		m22 = (float) (1.0 - (xx + yy));
	}
}
