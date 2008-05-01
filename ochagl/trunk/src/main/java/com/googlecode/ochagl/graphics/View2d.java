package com.googlecode.ochagl.graphics;

/**
 * ２Ｄ用のビュー．
 */
public interface View2d extends View {

    int CLEAR = 0x01;

    /**
     * 描画オブジェクトを登録する
     *
     * @param priority 描画優先順位
     * @param object 描画対象のオブジェクト
     */
    void addRenderObject(int priority, Object2d object);

}
