/*
 * Created on 2005/01/03
 *
 */
package com.googlecode.ochagl.graphics;

/**
 * ライトのインタフェイス.
 */
public interface Light extends Object3d {

    /**
     * 環境光を設定する．
     *
     * @param r 輝度（赤:0.0～1.0)
     * @param g 輝度（緑:0.0～1.0)
     * @param b 輝度（青:0.0～1.0)
     */
    void setAmbinet(
        float r,
        float g,
        float b);

    /**
     * 基本色を設定する．
     *
     * @param r 輝度（赤:0.0～1.0)
     * @param g 輝度（緑:0.0～1.0)
     * @param b 輝度（青:0.0～1.0)
     */
    void setDiffuse(
        float r,
        float g,
        float b);

    /**
     * 鏡面反射光を設定する．
     *
     * @param r 輝度（赤:0.0～1.0)
     * @param g 輝度（緑:0.0～1.0)
     * @param b 輝度（青:0.0～1.0)
     */
    void setSpecular(
        float r,
        float g,
        float b);

    /**
     * 発散光（自分から光る成分）を設定する．
     *
     * @param r 輝度（赤:0.0～1.0)
     * @param g 輝度（緑:0.0～1.0)
     * @param b 輝度（青:0.0～1.0)
     */
    void setEmissive(
        float r,
        float g,
        float b);

    /**
     * 鏡面反射光の鋭さを設定する．
     *
     * @param s D鏡面反射光の鋭さ（0.0～1.0）
     */
    void setShininess(float s);
}
