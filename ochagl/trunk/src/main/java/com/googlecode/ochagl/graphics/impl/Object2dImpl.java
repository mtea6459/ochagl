package com.googlecode.ochagl.graphics.impl;

import com.googlecode.ochagl.core.TreeImpl;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.math.Vec2;

/**
 * 汎用2D描画物クラス.
 */
public class Object2dImpl extends TreeImpl implements Object2d {

    /**
     * 位置．
     */
    protected Vec2 pos_ = new Vec2();

    /**
     * 矩形の幅．
     */
    protected float width_ = 0;

    /**
     * 矩形の高さ．
     */
    protected float height_ = 0;

    /**
     * 輝度の赤．
     */
    protected float r_ = 1;

    /**
     * 輝度の緑．
     */
    protected float g_ = 1;

    /**
     * 輝度の青．
     */
    protected float b_ = 1;

    /**
     * 輝度のアルファ．
     */
    protected float a_ = 1;

    /**
     * 表示フラグ．true:表示 false:非表示
     */
    private boolean isShow_ = false;

    /**
     * 描画フラグ．true:描画 false:非描画
     */
    private boolean isAlive_ = true;

    /**
     * ラジアン(ラジアン)．
     */
    protected float radian_ = 0;

    /**
     * スケール値
     */
    protected float scale_ = 1.0f;

    /**
     * 半透明モード．
     */
    protected int semiMode_ = 0;

    /**
     * テクスチャ．
     */
    protected Texture texture_ = null;

    /**
     * テクスチャＵ座標．
     */
    protected float u_ = 0.0f;

    /**
     * テクスチャＶ座標．
     */
    protected float v_ = 0.0f;

    /**
     * テクスチャ幅．
     */
    protected float uw_ = 0.0f;

    /**
     * テクスチャ高さ．
     */
    protected float vh_ = 0.0f;

    /**
     * デフォルトコンストラクタ．
     */
    public Object2dImpl() {
        this("");
    }

    /**
     * コンストラクタ．
     * 
     * @param name
     *            このオブジェクトの名前
     */
    public Object2dImpl(String name) {
        super(name);
        isShow_ = false;
        isAlive_ = true;
        reset();
    }

    /**
     * 再初期化する．
     */
    public void reset() {
        setPosition(Vec2.zero);
    }

    /**
     * 位置を取得する．
     * 
     * @return 位置ベクトル
     */
    public Vec2 getPosition() {
        return pos_;
    }

    /**
     * 位置を設定する．
     * 
     * @param v
     *            位置ベクトル
     */
    public void setPosition(final Vec2 v) {
        setPosition(v.x, v.y);
    }

    /**
     * 位置を設定する．
     * 
     * @param x
     *            Ｘ座標
     * @param y
     *            Ｙ座標
     * @param z
     *            Ｚ座標
     */
    public void setPosition(final float x, final float y) {
        pos_.set(x, y);
    }

    /**
     * 左上を原点として位置を設定する．
     * 
     * @param v
     *            位置ベクトル
     */
    public void setLeftopPosition(final Vec2 v) {
        setLeftopPosition(v.x, v.y);
    }

    /**
     * 左上を原点として位置を設定する．
     * 
     * @param x
     *            Ｘ座標
     * @param y
     *            Ｙ座標
     */
    public void setLeftopPosition(final float x, final float y) {
        float lx = x + (getWidth() / 2);
        float ly = y + (getHeight() / 2);
        setPosition(lx, ly);
    }

    /**
     * 矩形の幅を設定する.
     * 
     * @param width
     *            矩形の幅
     */
    public void setWidth(final float width) {
        width_ = width;
    }

    /**
     * 矩形の高さを設定する.
     * 
     * @param height
     *            矩形の高さ
     */
    public void setHeight(final float height) {
        height_ = height;
    }

    /**
     * RGBAを設定する.
     * 
     * @param r
     *            赤
     * @param g
     *            緑
     * @param b
     *            青
     * @param a
     *            アルファ
     */
    public void setRGBA(final float r,
                        final float g,
                        final float b,
                        final float a) {
        r_ = r;
        g_ = g;
        b_ = b;
        a_ = a;
    }

    /**
     * RGBAの現在値に加算する.
     * 
     * @param r
     *            赤
     * @param g
     *            緑
     * @param b
     *            青
     * @param a
     *            アルファ
     */
    public void addRGBA(final float r,
                        final float g,
                        final float b,
                        final float a) {
        r_ += r;
        g_ += g;
        b_ += b;
        a_ += a;

        if (r_ < 0.0f) {
            r_ = 0.0f;
        }

        if (r_ > 1.0f) {
            r_ = 1.0f;
        }

        if (g_ < 0.0f) {
            g_ = 0.0f;
        }

        if (g_ > 1.0f) {
            g_ = 1.0f;
        }

        if (b_ < 0.0f) {
            b_ = 0.0f;
        }

        if (b_ > 1.0f) {
            b_ = 1.0f;
        }

        if (a_ < 0.0f) {
            a_ = 0.0f;
        }

        if (a_ > 1.0f) {
            a_ = 1.0f;
        }
    }

    /**
     * テクスチャを設定する．
     * 
     * @param texture
     *            テクスチャ
     */
    public void setTexture(final Texture texture) {
        texture_ = texture;
        if (texture == null) {
            return;
        }
        width_ = texture_.getImageWidth();
        height_ = texture_.getImageHeight();
        u_ = 0.0f;
        v_ = 0.0f;
        uw_ = 1.0f;
        vh_ = 1.0f;
    }

    /**
     * テクスチャＵＶを設定する．
     * 
     * @param u
     *            U座標
     * @param v
     *            V座標
     * @param w
     *            テクスチャ幅
     * @param h
     *            テクスチャ高さ
     */
    public void setUv(final int u, final int v, final int w, final int h) {
        if (texture_ != null) {
            u_ = (float) u / texture_.getWidth();
            v_ = (float) v / texture_.getHeight();
            uw_ = (float) w / texture_.getWidth();
            vh_ = (float) h / texture_.getHeight();
        }
    }

    /**
     * 半透明モードを設定する.
     * 
     * @param mode
     *            半透明モードの定数
     */
    public void setSemiMode(final int mode) {
        semiMode_ = mode;
    }

    /**
     * スケールを設定する.
     * 
     * @param scale
     *            スケール値
     */
    public void setScale(float scale) {
        scale_ = scale;
    }

    /**
     * スケールを取得する.
     * 
     * @return スケール値
     */
    public float getScale() {
        return scale_;
    }

    /**
     * 矩形の幅を取得する.
     * 
     * @return 矩形の幅
     */
    public float getWidth() {
        return width_;
    }

    /**
     * 矩形の高さを取得する.
     *
     * @return 矩形の高さ
     */
    public float getHeight() {
        return height_;
    }

    /**
     * 半透明モードを取得する.
     *
     * @return DOCUMENT ME!
     */
    public int getSemiMode() {
        return semiMode_;
    }

    /**
     * 表示指定する.
     *
     * @param b true:表示 false:非表示
     */
    public void show(final boolean b) {
        isShow_ = b;
    }

    /**
     * 表示されているか検査する.
     *
     * @return true:表示 false:非表示
     */
    public boolean isShow() {
        return isShow_;
    }

    /**
     * 描画ツリーに接続されているか検査する.
     *
     * @return true:接続されている false:接続されていない
     */
    public boolean isAlive() {
        return isAlive_;
    }

    /**
     * 描画ツリーから外す.
     */
    public void kill() {
        isAlive_ = false;
    }

    /**
     * ラジアン(回転角)を設定する.
     *
     * @param radian ラジアン(回転角)
     */
    public void setRadian(final float radian) {
        radian_ = radian;
    }

    /**
     * ラジアン(回転角)を取得する.
     *
     * @return ラジアン(回転角)
     */
    public float getRadian() {
        return radian_;
    }

    /**
     * ラジアン(回転角)の現在値を加算する.
     *
     * @param radian 加算するラジアン(回転角)
     */
    public void addRadian(final float radian) {
        radian_ += radian;
    }

    /**
     * レンダリングする．
     *
     * @param gcon 描画コンテキスト
     */
    public void render(final GraphicContext gcon) {
    }
}
