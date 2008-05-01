package com.googlecode.ochagl.graphics.jogl;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;


/**
 * テクスチャオブジェクトのOpenGL実装。
 * todo 比率はどこで使うのか？
 */
public class TextureJogl implements com.googlecode.ochagl.graphics.Texture {
    private Texture jtex_;
    /**
     * コンストラクタ。
     *
     * @param target ターゲットタイプ
     * @param textureID テクスチャＩＤ
     */
    public TextureJogl(Texture texture) 
    {
        jtex_ = texture;
    }

    /**
     * OpenGLにテクスチャをバインドする。
     *
     * @param gl The GL context to bind to
     */
    public void bind(final GL gl) 
    {
        jtex_.enable();
        jtex_.bind();
        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    }

    /**
     * オリジナルの高さを取得する。
     *
     * @return オリジナルの高さ
     */
    public int getImageHeight() 
    {
        return jtex_.getImageHeight();
    }

    /**
     * オリジナルの幅を取得する。
     *
     * @return オリジナルの幅
     */
    public int getImageWidth()
    {
        return jtex_.getImageWidth();
    }

    /**
     * テクスチャの高さを取得する。
     *
     * @return テクスチャ高さ
     */
    public float getHeight() 
    {
        return jtex_.getHeight();
    }

    /**
     * テクスチャの幅を取得する。
     *
     * @return テクスチャ幅
     */
    public float getWidth()
    {
        return jtex_.getWidth();
    }

}
