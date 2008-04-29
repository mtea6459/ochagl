package com.googlecode.ochagl.graphics.jogl;


import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import com.googlecode.ochagl.core.SysUtils;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Material;



public class MaterialJogl implements Material {

	protected FloatBuffer diff = SysUtils.createFloatBuffer(4);

	protected FloatBuffer ambi = SysUtils.createFloatBuffer(4);

	protected FloatBuffer spec = SysUtils.createFloatBuffer(4);

	protected FloatBuffer emit = SysUtils.createFloatBuffer(4);

	protected FloatBuffer shin = SysUtils.createFloatBuffer(4); // ‚È‚º‚©‚S‚¶‚á‚È‚¢‚ÆNG‚É‚È‚é

    public MaterialJogl() {
        setAmbinet(1.0f, 1.0f, 1.0f);
        setDiffuse(1.0f, 1.0f, 1.0f);
        setSpecular(1.0f, 1.0f, 1.0f);
        setEmissive(1.0f, 1.0f, 1.0f);
        setShininess(0.0f);
    }

    public void init() {
//        setDiffuse(0.8f, 0.8f, 0.8f);
        setDiffuse(1.0f, 1.0f, 1.0f);
        setAmbinet(0.2f, 0.2f, 0.2f);
        setSpecular(0.5f, 0.5f, 0.5f);
        setEmissive(0.0f, 0.0f, 0.0f);
        setShininess(100);
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

    public void applyMaterial(GraphicContext gcon)
    {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();
        
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambi);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, diff);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, spec);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, shin);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, emit);
    }
}
