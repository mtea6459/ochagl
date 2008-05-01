/*
 * Created on 2004/11/28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.googlecode.ochagl.graphics;

import java.io.IOException;


/**
 * テクスチャローダのインタフェイス。
 *
 */
public interface TextureLoader {

    /**
     * テクスチャを読み込む。
     *
     * @param gc グラフィックコンテキスト
     * @param resourceName リソース名
     *
     * @return テクスチャオブジェクト
     *
     * @throws IOException リソースが見つからない時に発生。
     */
    Texture getTexture(
        GraphicContext gc,
        String resourceName) throws IOException;

}
