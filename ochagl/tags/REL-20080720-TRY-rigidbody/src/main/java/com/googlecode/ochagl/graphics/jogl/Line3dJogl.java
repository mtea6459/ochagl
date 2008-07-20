package com.googlecode.ochagl.graphics.jogl;

import javax.media.opengl.GL;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Line3d;
import com.googlecode.ochagl.graphics.Vertex;
import com.googlecode.ochagl.graphics.impl.MeshAdapter;
import com.googlecode.ochagl.math.Vec3;



public class Line3dJogl extends MeshAdapter implements Line3d {

    private Vertex vdata_ = null;
    private boolean loop_ = false;
    private float r_ = 0.0f;
    private float g_ = 0.0f;
    private float b_ = 0.0f;

    /**
     * コンストラクタ
     */
    public Line3dJogl() {
    }

    public void setColor(float r, float g, float b) {
        r_ = r;
        g_ = g;
        b_ = b;
    }

    public void setLoop(boolean b) {
        loop_ = b;
    }

    public boolean isLoop() {
        return loop_;
    }

    /**
     * 頂点設定
     */
    public void setVertex(Vertex v)
    {
        vdata_ = v;
    }

    /**
     * 頂点取得
     */
    public Vertex getVertex()
    {
        return vdata_;
    }

    /**
     * 描画
     */
    public void render(GraphicContext gcon)
    {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();

        gl.glShadeModel(GL.GL_FLAT);

        gl.glDisable(GL.GL_TEXTURE_2D);
        gl.glColor4f(r_, g_, b_, 1.0f);

        gl.glDisable(GL.GL_LIGHTING);

        gl.glBegin(GL.GL_LINES);

        Vec3[] vs = vdata_.getVertexTop();
        if (isLoop()) {

            int max = vs.length;
            for (int i = 0; i < max; i ++) {
                int j = (i + 1) % max;
                gl.glVertex3f(vs[i].x, vs[i].y, vs[i].z);
                gl.glVertex3f(vs[j].x, vs[j].y, vs[j].z);
            }

        } else {

            for (int i = 0; i < vs.length-1; i ++) {
                int j = i+1;
                gl.glVertex3f(vs[i].x, vs[i].y, vs[i].z);
                gl.glVertex3f(vs[j].x, vs[j].y, vs[j].z);
            }
        }
        gl.glEnd();
        gl.glEnable(GL.GL_LIGHTING);
    }

}