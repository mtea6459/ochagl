package com.googlecode.ochagl.core;

import com.googlecode.ochagl.math.Vec2;

/**
 * 矩形クラス。
 */
public class Rect {

    /**
     * 左上の座標。
     */
    private Vec2 pos1_ = null;

    /**
     * 右下の座標。
     */
    private Vec2 pos2_ = null;

    /**
     * 矩形の幅。
     */
    private float width_;

    /**
     * 矩形の高さ。
     */
    private float height_;

    /**
     * コンストラクタ。
     */
    public Rect() {
        this(0, 0, 0, 0);
    }

    /**
     * コンストラクタ。
     * 
     * @param x
     *            左上X
     * @param y
     *            左上Y
     * @param w
     *            幅
     * @param h
     *            高さ
     */
    public Rect(final float x, final float y, final float w, final float h) {

        pos1_ = new Vec2(x, y);
        pos2_ = new Vec2(x + w, y - h);
        width_ = w;
        height_ = h;
    }

    /**
     * 自身と指定した矩形が重なっているかを検査する。
     * 
     * @param tgt
     *            対象となる矩形
     * 
     * @return true:重なっている false:重なっていない
     */
    public boolean isHit(final Rect tgt) {

        return (getX1() < tgt.getX2()) && (tgt.getX1() < getX2())
                && (getY1() < tgt.getY2()) && (tgt.getY1() < getY2());
    }

    /**
     * 座標が矩形内かチェックする。
     * 
     * @param x
     * @param y
     * @return true:矩形の中 false:矩形の外
     */
    public boolean isIn(final float x, final float y) {
        return pos1_.x <= x && x < pos2_.x && pos2_.y <= y && y < pos1_.y;
    }

    /**
     * 幅を設定する。
     * 
     * @param w
     *            幅
     */
    public void setWidth(final float w) {

        width_ = w;
    }

    /**
     * DOCUMENT 高さを設定する。
     * 
     * @param h
     *            高さ
     */
    public void setHeight(final float h) {

        height_ = h;
    }

    /**
     * 左上のｘ座標を取得する。
     * 
     * @return 左上のｘ座標
     */
    public float getX1() {

        return pos1_.x;
    }

    /**
     * 左上のｙ座標を取得する。
     * 
     * @return 左上のｙ座標
     */
    public float getY1() {

        return pos1_.y;
    }

    /**
     * 右下のｘ座標を取得する。
     * 
     * @return 右下のｘ座標
     */
    public float getX2() {

        return pos2_.x;
    }

    /**
     * 右下のｙ座標を取得する。
     * 
     * @return 右下のｙ座標
     */
    public float getY2() {

        return pos2_.y;
    }

    /**
     * 矩形の幅を取得する。
     * 
     * @return 矩形の幅
     */
    public float getWidth() {

        return width_;
    }

    /**
     * 矩形の高さを取得する。
     * 
     * @return 矩形の高さ
     */
    public float getHeight() {

        return height_;
    }
}
