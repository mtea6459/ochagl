package com.googlecode.ochagl.graphics.jogl;

import javax.media.opengl.GL;


import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.SemiMode;
import com.googlecode.ochagl.graphics.StringSprite;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Rad;
import com.sun.opengl.util.GLUT;

/**
 * スプライト文字列のJOGLによる実装クラス．
 */
public class StringSpriteJogl extends Object2dJogl implements StringSprite {

	/**
	 * フォントサイズ．
	 */
	private static final int FONT_SIZE = 8;

	/**
	 * 文字列．
	 */
	private String string_ = null;

	/**
	 * フォントのUテクスチャサイズ(実数）
	 */
	private float fusize_ = 0.0f;

	/**
	 * フォントのVテクスチャサイズ(実数)
	 */
	private float fvsize_ = 0.0f;

    /**
     * コンストラクタ．
     * @param name このオブジェクトの名前
     */
	public StringSpriteJogl() {
		string_ = "";
	}

	/**
	 * 文字列を設定する．
	 *
	 * @param string 文字列
	 */
	public void setString(final String string) {
		string_ = string;
	}

	/**
	 * プリミティブの描画
	 */
	public void render(GraphicContext gcon) {
		GraphicContextJogl gc = (GraphicContextJogl) gcon;
		GL gl = gc.getGL();

		gl.glColor4f(r_, g_, b_, a_);

		switch (getSemiMode()) {

		case SemiMode.SEMI_NONE: // 半透明なし
			gl.glDisable(GL.GL_BLEND);
			break;

		case SemiMode.SEMI_NORMAL: // 通常の半透明
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			break;

		case SEMI_ADD: // 加算半透明
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
			break;
		}

		if (texture_ != null) {
			useTexture(gc, gl);
		} else {
			nouseTexture(gc, gl);
		}
	}

	private void useTexture(GraphicContextJogl gc, GL gl) {

		((TextureJogl) texture_).bind(gl);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadMatrixf(gc.matrixToArray(new Mat4()));

		gl.glRotatef(Rad.toDeg(getRadian()), 0, 0, 1);
		gl.glScalef(getScale(), getScale(), getScale());

		for (int i = 0; i < string_.length(); i++) {
			float x = pos_.x;
			float y = pos_.y;
			char c = string_.charAt(i);
			drawChar(gl, x + (FONT_SIZE * i), y, c);
		}

		gl.glPopMatrix();
	}

	private void nouseTexture(GraphicContextJogl gc, GL gl) {
		GLUT glut = gc.getGLUT();
		gl.glPushMatrix();
		float x = pos_.x;
		float y = pos_.y;
        gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glRasterPos2f(x, y);
		//glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_10, string_);
        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, string_);
		gl.glPopMatrix();
	}

	/**
	 * １文字描画する．
	 *
	 * @param gl GLオブジェクト
	 * @param x 表示Ｘ座標
	 * @param y 表示Ｘ座標
	 * @param c 表示文字
	 */
	private void drawChar(final GL gl, final float x, final float y,
			final char c) {

		char a = (char) (c - '\u0020');

		float u = FONT_SIZE * (a % 32);
		float v = FONT_SIZE * (a / 32);
		u /= texture_.getImageWidth();
		v /= texture_.getImageHeight();

		// TODO フォントサイズを可変にする。１
		fusize_ = (float) FONT_SIZE / texture_.getImageWidth();
		fvsize_ = (float) FONT_SIZE / texture_.getImageHeight();

		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(u, v);
		gl.glVertex2f(x, y);

		gl.glTexCoord2f(u, v + fusize_);
		gl.glVertex2f(x, y + FONT_SIZE);

		gl.glTexCoord2f(u + fusize_, v + fvsize_);
		gl.glVertex2f(x + FONT_SIZE, y + FONT_SIZE);

		gl.glTexCoord2f(u + fusize_, v);
		gl.glVertex2f(x + FONT_SIZE, y);
		gl.glEnd();
	}
}
