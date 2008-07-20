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
public class Vec2 {

	public static final Vec2 zero = new Vec2(0, 0);

	/**
	 * X軸
	 */
	public float x;

	/**
	 * Y軸
	 */
	public float y;

	/**
	 * デフォルトコンストラクタ
	 */
	public Vec2() {
		x = 0.0f;
		y = 0.0f;
	}

	/**
	 * コンストラクタ(スカラ)
	 * 
	 * @param x x値
	 * @param y y値
	 */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * コンストラクタ(ベクトル指定)
	 * 
	 * @param v ベクトル値
	 */
	public Vec2(Vec2 v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * スカラ設定
	 * 
	 * @param x x値
	 * @param y y値
	 */
	public final void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * ベクトル設定
	 * 
	 * @param v ベクトル値
	 */
	public final void set(Vec2 v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * 加算。ふたつベクトルの加算結果を格納する
	 * 
	 * @param v1 ベクトル１ 
	 * @param v2 ベクトル２
	 * @return 自身
	 */
	public final Vec2 add(Vec2 v1, Vec2 v2) {
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		return this;
	}

	/**
	 * 加算。自身に足しこむ
	 * 
	 * @param v1 ベクトル１ 
	 * @param v2 ベクトル２
	 * @return 自身
	 */
	public final Vec2 add(Vec2 v1) {
		x += v1.x;
		y += v1.y;
		return this;
	}

	/**
	 * 減算。ふたつベクトルの減算結果を格納する
	 * 
	 * @param v1 ベクトル１ 
	 * @param v2 ベクトル２
	 * @return 自身
	 */
	public final Vec2 sub(Vec2 v1, Vec2 v2) {
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		return this;
	}

	/**
	 * 減算。自身から差し引く
	 * 
	 * @param v1 ベクトル１ 
	 * @param v2 ベクトル２
	 * @return 自身
	 */
	public final Vec2 sub(Vec2 v1) {
		x -= v1.x;
		y -= v1.y;
		return this;
	}

	/**
	 * 乗算。パラメータの値で自身を上書きすることに注意
	 * 
	 * @param s スケール値
	 * @param v1 被乗数ベクトル
	 */
	public final Vec2 scale(float s, Vec2 v1) {
		x = s * v1.x;
		y = s * v1.y;
		return this;
	}

	/**
	 * 乗算。自身をスケールする
	 * 
	 * @param s スケール値
	 */
	public final Vec2 scale(float s) {
		x *= s;
		y *= s;
		return this;
	}

	/**
	 * 等値チェック
	 * 
	 * @param v1 ベクトル１
	 */
	public boolean equals(Vec2 v1) {
		return v1 != null && x == v1.x && y == v1.y;
	}

	public boolean epsilonEquals(Vec2 v1, float epsilon) {
		return (Math.abs(v1.x - this.x) <= epsilon)
				&& (Math.abs(v1.y - this.y) <= epsilon);
	}

	/**
	 * 文字列化
	 * 
	 * @return 文字列 (x, y)
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * 絶対値の取得。
	 * 自身を引数で初期化する
	 * @param v1 ベクトル１
	 */
	public final void abs(Vec2 v1) {
		set(v1);
		abs();
	}

	/**
	 * 絶対値の取得
	 */
	public final void abs() {
		if (x < 0.0)
			x = -x;
		if (y < 0.0)
			y = -y;
	}

	/**
	 * 内積 
	 * 
	 * @param v1 ベクトル１
	 */
	public final float dot(Vec2 v1) {
		return x * v1.x + y * v1.y;
	}

	public final float len() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public final float len2() {
		return x * x + y * y;
	}

	public final float normalize() {
		float d = len();
		x /= d;
		y /= d;
        return d;
	}

	public final float normalize(Vec2 v1) {
		set(v1);
		return normalize();
	}

	public final float angle(Vec2 v1) {
		return (float) Math.abs(Math.atan2(x * v1.y - y * v1.x, dot(v1)));
	}

    // 大きさベクトルの飽和処理
    // TODO テンプベクトルをどうにかする
    public void turncate(float max) {
        Vec2 tmp = new Vec2();
        tmp.set(this);
        float dist = tmp.normalize();
        if (dist == 0)
            return;

        if (dist > max) {
            tmp.scale(max);
            set(tmp);
        }
    }

}
