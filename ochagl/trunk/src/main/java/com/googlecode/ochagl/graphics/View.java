package com.googlecode.ochagl.graphics;

/**
 * ビューのインタフェイス.
 */
public interface View {

    /**
     * クリアカラーを設定する.
     *
     * @param r 赤
     * @param g 緑
     * @param b 青
     */
    void setClearColor(
        float r,
        float g,
        float b);

    /**
     * Viewステートの設定
     * @param state 設定値フラグORで組み合わせる
     */
    void setState(int state);

    /**
     * Viewステートのを外す
     * @param state 設定値フラグORで組み合わせる
     */
    void unsetState(int state);

    /**
     * レンダリング環境のセットアップ.
     *
     * @param gcon 描画コンテキスト
     */
    void begin(GraphicContext gcon);

    /**
     * レンダリングの後始末.
     *
     * @param gcon 描画コンテキスト
     */
    void end(GraphicContext gcon);

    /**
     * レンダリング 何もしない.
     *
     * @param gcon 描画コンテキスト
     */
    void render(GraphicContext gcon);

    /**
     * オブジェクトを描画ツリーから削除
     *
     */
    void remove();

    /**
     * 表示・非表示.
     *
     * @param b true：表示 false：非表示
     */
    void show(boolean b);

    /**
     * 表示チェック.
     *
     * @return true：表示 false：非表示
     */
    boolean isShow();
}
