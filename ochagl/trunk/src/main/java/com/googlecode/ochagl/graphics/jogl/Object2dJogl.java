package com.googlecode.ochagl.graphics.jogl;

import javax.media.opengl.GL;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.SemiMode;
import com.googlecode.ochagl.graphics.impl.Object2dImpl;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Rad;



/**
 * スプライトのJOGLによる実装.
 */
public class Object2dJogl extends Object2dImpl {

	public Object2dJogl() {
		this("");
	}

	public Object2dJogl(String name) {
		super(name);
	}

	/**
	 * プリミティブの描画
	 */
	public void render(GraphicContext gcon) {
		GraphicContextJogl gc = (GraphicContextJogl) gcon;
		GL gl = gc.getGL();

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
        gl.glColor4f(r_, g_, b_, a_);

		if (texture_ != null) {
			useTexture(gc, gl);
		} else {
			nouseTexture(gc, gl);
		}
	}

	private void useTexture(GraphicContextJogl gc, GL gl) {
		gl.glMatrixMode(GL.GL_MODELVIEW);

        ((TextureJogl) texture_).bind(gl);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

        gl.glPushMatrix();

		gl.glLoadIdentity();
		gl.glTranslatef(pos_.x, pos_.y, 0);

		gl.glRotatef(Rad.toDeg(getRadian()), 0, 0, 1);
		gl.glScalef(getScale(), getScale(), getScale());
		gl.glBegin(GL.GL_QUADS);
		{
			float w2 = getWidth() / 2.0f;
			float h2 = getHeight() / 2.0f;

			gl.glTexCoord2f(u_, v_);
			gl.glVertex2f(-w2, -h2);

			gl.glTexCoord2f(u_, v_ + vh_);
			gl.glVertex2f(-w2, h2);

			gl.glTexCoord2f(u_ + uw_, v_ + vh_);
			gl.glVertex2f(w2, h2);

			gl.glTexCoord2f(u_ + uw_, v_);
			gl.glVertex2f(w2, -h2);
		}
		gl.glEnd();

		gl.glPopMatrix();
	}

	private void nouseTexture(GraphicContextJogl gc, GL gl) {
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

		gl.glMatrixMode(GL.GL_MODELVIEW);

		gl.glPushMatrix();
		gl.glLoadMatrixf(gc.matrixToArray(new Mat4()));
		gl.glLoadIdentity();
		gl.glTranslatef(pos_.x, pos_.y, 0);
		gl.glRotatef(Rad.toDeg(getRadian()), 0, 0, 1);
		gl.glScalef(getScale(), getScale(), getScale());
		gl.glBegin(GL.GL_QUADS);
		{
			float w2 = getWidth() / 2.0f;
			float h2 = getHeight() / 2.0f;
			gl.glVertex2f(-w2, -h2);
			gl.glVertex2f(-w2, h2);
			gl.glVertex2f(w2, h2);
			gl.glVertex2f(w2, -h2);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}
}
