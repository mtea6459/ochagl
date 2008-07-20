/*
 * Created on 2004/11/28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.googlecode.ochagl.graphics.jogl;

import java.awt.Graphics;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


import com.googlecode.ochagl.core.SysUtils;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.math.Mat3;
import com.googlecode.ochagl.math.Mat4;
import com.sun.opengl.util.GLUT;

/**
 * @author ocha
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GraphicContextJogl implements GraphicContext {

	protected FloatBuffer tmpMat_ = SysUtils.createFloatBuffer(16);
	protected FloatBuffer tmpMat2_ = SysUtils.createFloatBuffer(9);
    private Mat4 viewMatrix_ = null;
    private GLAutoDrawable drawable_ = null;
    private GLUT glut_ = null;
    private int width_;
    private int height_;
    
    public GraphicContextJogl(GLAutoDrawable drawable, int width, int height) {
        drawable_ = drawable;
        glut_ = new GLUT();
        width_ = width;
        height_ = height;
    }

    public GL getGL() {
        return drawable_.getGL();
    }
    
    public GLUT getGLUT() {
        return glut_;
    }
    
    public Graphics getGraphics() {
    	return null;
    }
    
    public void setViewMatrix(Mat4 matrix) {
        viewMatrix_ = matrix;
    }

    public Mat4 getViewMatrix() {
        return viewMatrix_;
    }

	/**
	 * çsóÒÇîzóÒÇ…äiî[Ç∑ÇÈ
	 * 
	 * @param matrix
	 * @return TODO
	 */
	public FloatBuffer matrixToArray(Mat4 matrix) {
		tmpMat_.put(0, matrix.m00);
		tmpMat_.put(1, matrix.m10);
		tmpMat_.put(2, matrix.m20);
		tmpMat_.put(3, matrix.m30);
		tmpMat_.put(4, matrix.m01);
		tmpMat_.put(5, matrix.m11);
		tmpMat_.put(6, matrix.m21);
		tmpMat_.put(7, matrix.m31);
		tmpMat_.put(8, matrix.m02);
		tmpMat_.put(9, matrix.m12);
		tmpMat_.put(10, matrix.m22);
		tmpMat_.put(11, matrix.m32);
		tmpMat_.put(12, matrix.m03);
		tmpMat_.put(13, matrix.m13);
		tmpMat_.put(14, matrix.m23);
		tmpMat_.put(15, matrix.m33);
		return tmpMat_;
	}

	/**
	 * çsóÒÇîzóÒÇ…äiî[Ç∑ÇÈ
	 * 
	 * @param matrix
	 * @return TODO
	 */
	public FloatBuffer matrixToArray(Mat3 matrix) {
		tmpMat2_.put(0, matrix.m00);
		tmpMat2_.put(1, matrix.m10);
		tmpMat2_.put(2, matrix.m20);
		tmpMat2_.put(3, matrix.m01);
		tmpMat2_.put(4, matrix.m11);
		tmpMat2_.put(5, matrix.m21);
		tmpMat2_.put(6, matrix.m02);
		tmpMat2_.put(7, matrix.m12);
		tmpMat2_.put(8, matrix.m22);
		return tmpMat2_;
	}

    public int getWidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }
}
