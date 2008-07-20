package com.googlecode.ochagl.graphics;

import com.googlecode.ochagl.math.Vec2;

/**
 * 2D描画物のインタフェイス.
 */
public interface Object2d extends Drawable, SemiMode {

	/**
	 * 再初期化する．
	 */
	void reset();

	/**
	 * ベクトル指定で座標を更新する.
	 *
	 * @param pos ベクトル座標
	 */
	void setPosition(Vec2 pos);

	/**
	 * 座標を更新する.
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	void setPosition(final float x, final float y);

	/**
	 * 左上を原点として位置を設定する．
	 *
	 * @param v 位置ベクトル
	 */
	void setLeftopPosition(final Vec2 v);

	/**
	 * 左上を原点として位置を設定する．
	 *
	 * @param x Ｘ座標
	 * @param y Ｙ座標
	 */
	void setLeftopPosition(final float x, final float y);

	/**
	 * 位置を取得する．
	 *
	 * @return 位置ベクトル
	 */
	Vec2 getPosition();

	/**
	 * 矩形の幅を設定する.
	 *
	 * @param width 矩形の幅
	 */
	void setWidth(float width);

	/**
	 * 矩形の高さを設定する.
	 *
	 * @param height 矩形の高さ
	 */
	void setHeight(float height);

	/**
	 * RGBAを設定する.
	 *
	 * @param r 赤
	 * @param g 緑
	 * @param b 青
	 * @param a アルファ
	 */
	void setRGBA(float r, float g, float b, float a);

	/**
	 * RGBAの現在値に加算する.
	 *
	 * @param r 赤
	 * @param g 緑
	 * @param b 青
	 * @param a アルファ
	 */
	void addRGBA(float r, float g, float b, float a);

	/**
	 * 半透明モードを設定する.
	 *
	 * @param mode 半透明モードの定数
	 */
	void setSemiMode(int mode);

	/**
	 * 矩形の幅を取得する.
	 *
	 * @return 矩形の幅
	 */
	float getWidth();

	/**
	 * 矩形の高さを取得する.
	 *
	 * @return 矩形の高さ
	 */
	float getHeight();

	/**
	 * 半透明モードを取得する.
	 *
	 * @return DOCUMENT ME!
	 */
	int getSemiMode();

    /**
     * ラジアン(回転角)を設定する.
     *
     * @param radian ラジアン(回転角)
     */
    void setRadian(float radian);

    /**
     * ラジアン(回転角)を取得する.
     *
     * @return ラジアン(回転角)
     */
    float getRadian();

    /**
     * ラジアン(回転角)の現在値を加算する.
     *
     * @param radian 加算するラジアン(回転角)
     */
    void addRadian(float radian);

    /**
     * スケールを設定する.
     *
     * @param scale スケール値
     */
    void setScale(float scale);

    /**
     * スケールを取得する.
     *
     * @return scale スケール値
     */
    float getScale();

    /**
     * テクスチャを設定する．
     *
     * @param texture DOCUMENT ME!
     */
    void setTexture(Texture texture);

    /**
     * テクスチャＵＶを設定する．
     *
     * @param u U座標
     * @param v V座標
     * @param w テクスチャ幅
     * @param h テクスチャ高さ
     */
    void setUv(
        int u,
        int v,
        int w,
        int h);

	/**
	 * レンダリングする．
	 *
	 * @param gcon 描画コンテキスト
	 */
	void render(GraphicContext gcon);
}
