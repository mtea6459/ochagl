/*
 * Created on 2005/01/03
 */
package com.googlecode.ochagl.graphics;

/**
 * ３Ｄラインのインタフェイス．
 *
 */
public interface Line3d extends Mesh {

    /**
     * ライン描画の始点と終点をつなげるかの設定する．
     *
     * @param b true:つなげる false:つなげない
     */
    void setLoop(boolean b);

    /**
     * 始点と終点がつながっているか検査する.
     *
     * @return true:つなげる false:つなげない
     */
    boolean isLoop();

    /**
     * 輝度設定．
     *
     * @param r 輝度（赤:0.0～1.0)
     * @param g 輝度（緑:0.0～1.0)
     * @param b 輝度（青:0.0～1.0)
     */
    void setColor(
        float r,
        float g,
        float b);
}
