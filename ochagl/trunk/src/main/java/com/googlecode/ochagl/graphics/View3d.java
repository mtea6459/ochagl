/*
 * Created on 2005/01/03
 *
 */
package com.googlecode.ochagl.graphics;



/**
 * ３Ｄ用のビューインタフィイス.
 */
public interface View3d extends View {

    int CLEAR = 0x01;

    /**
     * 画角から透視投影変換行列を設定する
     *
     * @param fovx 横方向の画角
     * @param aspect 縦/横
     * @param near 近平面位置
     * @param far 遠平面位置
     */
    void setPerspective(
        float fovx,
        float aspect,
        float near,
        float far);

    /**
     * このビューをオブジェクトにアタッチする。
     *
     * @param object レンダリング対象となるオブジェクト
     */
    void setRenderObject(Object3d object);

    /**
     * レンダリング対象となるオブジェクトを取得する
     *
     * @return レンダリング対象となるオブジェクト
     */
    Object3d getRenderObject();

    /**
     * ビューとして振舞うオブジェクト設定する。
     *
     * @param object ビューとして振舞うオブジェクト
     */
    void setViewObject(Object3d object);

    /**
     * ビューとして振舞うオブジェクト取得する。
     *
     * @retrun object ビューとして振舞うオブジェクト
     */
    Object3d getViewObject();
}
