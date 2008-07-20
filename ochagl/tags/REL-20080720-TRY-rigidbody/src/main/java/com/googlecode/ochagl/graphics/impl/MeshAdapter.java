package com.googlecode.ochagl.graphics.impl;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Material;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.ShadeMode;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.Vertex;



/**
 * メッシュの空実装クラス．
 */
public class MeshAdapter implements Mesh {

    /**
     * コンストラクタ
     */
    public MeshAdapter() {
    }

    /**
     * シェードモードを設定する．
     *
     * @param mode シェードモード
     */
    public void setShadeMode(final ShadeMode mode) {
    }

    /**
     * シェードモードを取得する．
     *
     * @return シェードモード
     */
    public ShadeMode getShadeMode() {

        return ShadeMode.FLAT;
    }

    /**
     * レンダリングステートを設定する．
     *
     * @param state シェードモード
     */
    public void setState(int state) {
    }

    /**
     * レンダリングステートを設定する．
     *
     * @param state シェードモード
     */
    public void unsetState(int state) {
    }

    /**
     * レンダリングステートを取得する．
     *
     * @return state シェードモード
     */
    public int getState() {
        return 0;
    }

    /**
     * テクスチャを設定する．
     *
     * @param tex テクスチャ
     */
    public void setTexture(final Texture tex) {
    }

    /**
     * マテリアルを設定する．
     *
     * @param mat マテリアル
     */
    public void setMaterial(final Material mat) {
    }

    /**
     * 頂点を設定する．
     *
     * @param v 頂点オブジェクト
     */
    public void setVertex(final Vertex v) {
    }

    /**
     * 頂点を取得する．
     *
     * @return 頂点オブジェクト
     */
    public Vertex getVertex() {

        return null;
    }

    /**
     * レンダリングを実行する．
     *
     * @param gcon 描画コンテキスト
     */
    public void render(final GraphicContext gcon) {
    }
}
