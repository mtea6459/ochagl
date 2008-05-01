package com.googlecode.ochagl.graphics;

import com.googlecode.ochagl.core.Tree;


/**
 * 画面上の全ての描画オブジェクトを表すインタフェイス.
 *
 */
public interface Drawable extends Tree {

    /**
     * 表示指定する.
     *
     * @param b true:表示 false:非表示
     */
    void show(boolean b);

    /**
     * 表示されているか検査する.
     *
     * @return true:表示 false:非表示
     */
    boolean isShow();

    /**
     * 描画ツリーに接続されているか検査する.
     *
     * @return true:接続されている false:接続されていない
     */
    boolean isAlive();

    /**
     * 描画ツリーから外す.
     */
    void kill();

    /**
     * レンダリングする
     *
     * @param gc 描画コンテキスト
     */
    void render(GraphicContext gc);
}
