package com.googlecode.ochagl.math;

/**
 * @author ocha
 *
 * 2Dの回転行列、3Dの姿勢制御等に使用。
 */
public class Mat3 {
	public static final Mat3 imat = new Mat3();

	public float m00;

	public float m01;

	public float m02;

	public float m10;

	public float m11;

	public float m12;

	public float m20;

	public float m21;

	public float m22;

	public Mat3() {
		setIdentity();
	}

	public Mat3(float m00, float m01, float m02, float m10, float m11,
			float m12, float m20, float m21, float m22) {
		set(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public Mat3(Mat3 m1) {
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

	public Mat3(Mat4 mat) {
		super();
		setIdentity();
		set(mat);
	}

	public String toString() {
		String nl = System.getProperty("line.separator");
		return "[" + nl + "  [" + m00 + "\t" + m01 + "\t" + m02 + "]" + nl
				+ "  [" + m10 + "\t" + m11 + "\t" + m12 + "]" + nl + "  ["
				+ m20 + "\t" + m21 + "\t" + m22 + "] ]";
	}

	public void set(float m00, float m01, float m02, float m10, float m11,
			float m12, float m20, float m21, float m22) {
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}

	public final void set(Mat3 m1) {
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

	public void set(Mat4 mat) {
		m00 = mat.m00;
		m10 = mat.m10;
		m20 = mat.m20;

		m01 = mat.m01;
		m11 = mat.m11;
		m21 = mat.m21;

		m02 = mat.m02;
		m12 = mat.m12;
		m22 = mat.m22;
	}

	public final void set(Quat q1) {
		setFromQuat(q1.x, q1.y, q1.z, q1.q);
	}

	public final void set(float scale) {
		m00 = scale;
		m01 = 0.0f;
		m02 = 0.0f;
		m10 = 0.0f;
		m11 = scale;
		m12 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = scale;
	}

	public final void setIdentity() {
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
	}

	public final void add(Mat3 m1, Mat3 m2) {
		// note this is alias safe.
		set(m1.m00 + m2.m00, m1.m01 + m2.m01, m1.m02 + m2.m02, m1.m10 + m2.m10,
				m1.m11 + m2.m11, m1.m12 + m2.m12, m1.m20 + m2.m20, m1.m21
						+ m2.m21, m1.m22 + m2.m22);
	}

	public final void add(Mat3 m1) {
		m00 += m1.m00;
		m01 += m1.m01;
		m02 += m1.m02;
		m10 += m1.m10;
		m11 += m1.m11;
		m12 += m1.m12;
		m20 += m1.m20;
		m21 += m1.m21;
		m22 += m1.m22;
	}

	public final void sub(Mat3 m1, Mat3 m2) {
		set(m1.m00 - m2.m00, m1.m01 - m2.m01, m1.m02 - m2.m02, m1.m10 - m2.m10,
				m1.m11 - m2.m11, m1.m12 - m2.m12, m1.m20 - m2.m20, m1.m21
						- m2.m21, m1.m22 - m2.m22);
	}

	public final void sub(Mat3 m1) {
		m00 -= m1.m00;
		m01 -= m1.m01;
		m02 -= m1.m02;
		m10 -= m1.m10;
		m11 -= m1.m11;
		m12 -= m1.m12;
		m20 -= m1.m20;
		m21 -= m1.m21;
		m22 -= m1.m22;
	}

	public final void mul(float scalar) {
		m00 *= scalar;
		m01 *= scalar;
		m02 *= scalar;
		m10 *= scalar;
		m11 *= scalar;
		m12 *= scalar;
		m20 *= scalar;
		m21 *= scalar;
		m22 *= scalar;
	}

	public final void mul(Mat3 m1) {
		mul(this, m1);
	}

	public final void mul(Mat3 m1, Mat3 m2) {
		// alias-safe way.
		set(m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20, m1.m00
				* m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21, m1.m00 * m2.m02
				+ m1.m01 * m2.m12 + m1.m02 * m2.m22,

		m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20, m1.m10 * m2.m01
				+ m1.m11 * m2.m11 + m1.m12 * m2.m21, m1.m10 * m2.m02 + m1.m11
				* m2.m12 + m1.m12 * m2.m22,

		m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20, m1.m20 * m2.m01
				+ m1.m21 * m2.m11 + m1.m22 * m2.m21, m1.m20 * m2.m02 + m1.m21
				* m2.m12 + m1.m22 * m2.m22);
	}

	public final void transpose() {
		float tmp = m01;
		m01 = m10;
		m10 = tmp;

		tmp = m02;
		m02 = m20;
		m20 = tmp;

		tmp = m12;
		m12 = m21;
		m21 = tmp;

	}

	public final void invert(Mat3 m1) {
		set(m1);
		invert();
	}

	public final void invert() {
		float s = determinant();
		if (s == 0.0)
			return;
		s = 1 / s;
		// alias-safe way.
		set(m11 * m22 - m12 * m21, m02 * m21 - m01 * m22,
				m01 * m12 - m02 * m11, m12 * m20 - m10 * m22, m00 * m22 - m02
						* m20, m02 * m10 - m00 * m12, m10 * m21 - m11 * m20,
				m01 * m20 - m00 * m21, m00 * m11 - m01 * m10);
		mul((float) s);
	}

	public final float determinant() {
		// less *,+,- calculation than expanded expression.
		return m00 * (m11 * m22 - m21 * m12) - m01 * (m10 * m22 - m20 * m12)
				+ m02 * (m10 * m21 - m20 * m11);
	}

	public final void rotX(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = 1.0f;
		m01 = 0.0f;
		m02 = 0.0f;
		m10 = 0.0f;
		m11 = (float) c;
		m12 = (float) -s;
		m20 = 0.0f;
		m21 = (float) s;
		m22 = (float) c;
	}

	public final void rotY(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = (float) c;
		m01 = 0.0f;
		m02 = (float) s;
		m10 = 0.0f;
		m11 = 1.0f;
		m12 = 0.0f;
		m20 = (float) -s;
		m21 = 0.0f;
		m22 = (float) c;
	}

	public final void rotZ(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		m00 = (float) c;
		m01 = (float) -s;
		m02 = 0.0f;
		m10 = (float) s;
		m11 = (float) c;
		m12 = 0.0f;
		m20 = 0.0f;
		m21 = 0.0f;
		m22 = 1.0f;
	}

	public final void normalize() {
		SVD(this);
	}

	public final void normalize(Mat3 m1) {
		set(m1);
		SVD(this);
	}

	public boolean equals(Mat3 m1) {
		return m1 != null && m00 == m1.m00 && m01 == m1.m01 && m02 == m1.m02
				&& m10 == m1.m10 && m11 == m1.m11 && m12 == m1.m12
				&& m20 == m1.m20 && m21 == m1.m21 && m22 == m1.m22;
	}

	public final void transform(Vec3 t) {
		// alias-safe
		transform(t, t);
	}

	public final void transform(Vec3 t, Vec3 result) {
		// alias-safe
		result.set(m00 * t.x + m01 * t.y + m02 * t.z, m10 * t.x + m11 * t.y
				+ m12 * t.z, m20 * t.x + m21 * t.y + m22 * t.z);
	}

	/**
	 * Performs SVD on this matrix and gets scale and rotation. Rotation is
	 * placed into rot.
	 * 
	 * @param rot the rotation factor.
	 * @return scale factor
	 */
	private float SVD(Mat3 rot) {
		// this is a simple svd.
		// Not complete but fast and reasonable.

		/*
		 * SVD scale factors(squared) are the 3 roots of
		 *  | xI - M*MT | = 0.
		 * 
		 * This will be expanded as follows
		 * 
		 * x^3 - A x^2 + B x - C = 0
		 * 
		 * where A, B, C can be denoted by 3 roots x0, x1, x2.
		 * 
		 * A = (x0+x1+x2), B = (x0x1+x1x2+x2x0), C = x0x1x2.
		 * 
		 * An avarage of x0,x1,x2 is needed here. C^(1/3) is a cross product
		 * normalization factor. So here, I use A/3. Note that x should be
		 * sqrt'ed for the actual factor.
		 */

		float s = (float) Math.sqrt((m00 * m00 + m10 * m10 + m20 * m20 + m01
				* m01 + m11 * m11 + m21 * m21 + m02 * m02 + m12 * m12 + m22
				* m22) / 3.0);

		// zero-div may occur.
		float t = (s == 0.0f ? 0.0f : 1.0f / s);

		if (rot != null) {
			if (rot != this)
				rot.set(this);
			rot.mul(t);
		}

		return s;
	}

	private void setFromQuat(float x, float y, float z, float w) {
		float n = x * x + y * y + z * z + w * w;
		float s = (n > 0.0f) ? (2.0f / n) : 0.0f;

		float xs = x * s, ys = y * s, zs = z * s;
		float wx = w * xs, wy = w * ys, wz = w * zs;
		float xx = x * xs, xy = x * ys, xz = x * zs;
		float yy = y * ys, yz = y * zs, zz = z * zs;

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

	/**
	 * 歪対象行列を設定する
	 * @param omega 角速度ベクトル
	 */
	public void skew(Vec3 omega) {
		m00 = 0;
		m01 = -omega.z;
		m02 = omega.y;
		m10 = omega.z;
		m11 = 0;
		m12 = -omega.x;
		m20 = -omega.y;
		m21 = omega.x;
		m22 = 0;
		//        m00 =        0; m01 =  omega.z; m02 = -omega.y;
		//        m10 = -omega.z; m11 =        0; m12 =  omega.x;
		//        m20 =  omega.y; m21 = -omega.x; m22 =  0;
	}
}
