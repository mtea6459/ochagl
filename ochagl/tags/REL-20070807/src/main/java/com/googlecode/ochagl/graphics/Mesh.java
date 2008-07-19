/*
 * Created on 2005/01/03
 */
package com.googlecode.ochagl.graphics;




/**
 * メッシュのインタフェイス．
 */
public interface Mesh {

    static final int CULL_FACE = 0x01;
    static final int LIGHTING = 0x02;

    /**
     * レンダリングステートを設定する．
     *
     * @param state レンダリングステート
     */
    void setState(int state);

    /**
     * レンダリングステートを外す
     *
     * @param state レンダリングステート
     */
    void unsetState(int state);

    /**
     * レンダリングステートを取得する．
     *
     * @return state レンダリングステート
     */
    int getState();

    /**
     * シェードモードを設定する．
     *
     * @param mode シェードモード
     */
    void setShadeMode(ShadeMode mode);

    /**
     * シェードモードを取得する．
     *
     * @return シェードモード
     */
    ShadeMode getShadeMode();

    /**
     * テクスチャを設定する．
     *
     * @param tex テクスチャ
     */
    void setTexture(Texture tex);

    /**
     * マテリアルを設定する．
     *
     * @param mat マテリアル
     */
    void setMaterial(Material mat);

    /**
     * 頂点を設定する．
     *
     * @param v 頂点オブジェクト
     */
    void setVertex(Vertex v);

    /**
     * 頂点を取得する．
     *
     * @return 頂点オブジェクト
     */
    Vertex getVertex();

    /**
     * レンダリングを実行する．
     *
     * @param gcon 描画コンテキスト
     */
    void render(GraphicContext gcon);
}
