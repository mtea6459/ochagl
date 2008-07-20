/**
 *	座標系クラス
 *
 *	 採用座標系など
 *	・右手座標系
 *	・右手回転系
 *	・縦書きベクトル
 *	・点は右から掛ける
 *
 */
package com.googlecode.ochagl.graphics.jogl;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import com.googlecode.ochagl.core.SysUtils;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Light;
import com.googlecode.ochagl.graphics.impl.Object3dImpl;
import com.googlecode.ochagl.math.Mat4;



public class LightJogl extends Object3dImpl implements Light {

	protected float fovx_ = 0;

	protected float aspect_ = 0;

	protected float near_ = 0;

	protected float far_ = 0;

	protected FloatBuffer pos = SysUtils.createFloatBuffer(4);

	protected FloatBuffer diff = SysUtils.createFloatBuffer(4);

	protected FloatBuffer ambi = SysUtils.createFloatBuffer(4);

	protected FloatBuffer spec = SysUtils.createFloatBuffer(4);

	protected FloatBuffer emit = SysUtils.createFloatBuffer(4);

	protected FloatBuffer shin = SysUtils.createFloatBuffer(4);

	protected Mat4 wvm_ = new Mat4();

	public LightJogl() {
		super();
	}

	public void setAmbinet(float r, float g, float b) {
		ambi.put(0, r);
		ambi.put(1, g);
		ambi.put(2, b);
		ambi.put(3, 1.0f);
	}

	public void setDiffuse(float r, float g, float b) {
		diff.put(0, r);
		diff.put(1, g);
		diff.put(2, b);
		diff.put(3, 1.0f);
	}

	public void setSpecular(float r, float g, float b) {
		spec.put(0, r);
		spec.put(1, g);
		spec.put(2, b);
		spec.put(3, 1.0f);
	}

	public void setEmissive(float r, float g, float b) {
		emit.put(0, r);
		emit.put(1, g);
		emit.put(2, b);
		emit.put(3, 1.0f);
	}

	public void setShininess(float s) {
		shin.put(0, s);
	}

	/**
	 * 行列を配列に格納する
	 * @param matrix
	 * @param array
	 */
	protected void matrixToArray(Mat4 matrix, float[] array) {
		array[0] = matrix.m00;
		array[1] = matrix.m10;
		array[2] = matrix.m20;
		array[3] = matrix.m30;
		array[4] = matrix.m01;
		array[5] = matrix.m11;
		array[6] = matrix.m21;
		array[7] = matrix.m31;
		array[8] = matrix.m02;
		array[9] = matrix.m12;
		array[10] = matrix.m22;
		array[11] = matrix.m32;
		array[12] = matrix.m03;
		array[13] = matrix.m13;
		array[14] = matrix.m23;
		array[15] = matrix.m33;
	}

	/**
	 *  ライトの設定 
	 */
	public void render(GraphicContext gcon) {
		GraphicContextJogl gc = (GraphicContextJogl) gcon;
		GL gl = gc.getGL();
		wvm_.mul(gc.getViewMatrix(), worldMatrix_);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadMatrixf(gc.matrixToArray(wvm_));
		{
			pos.put(0, 0.0f);
			pos.put(1, 0.0f);
			pos.put(2, 0.0f);
			pos.put(3, 1.0f);

			gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos);
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambi);
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diff);

			gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, spec);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, shin);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, emit);

			gl.glEnable(GL.GL_LIGHT0);
			gl.glEnable(GL.GL_LIGHTING);
		}
		gl.glPopMatrix();

	}
}
