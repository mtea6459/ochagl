package com.googlecode.ochagl.graphics.jogl;

import javax.media.opengl.GL;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Material;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.ShadeMode;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.Vertex;
import com.googlecode.ochagl.math.TexCoord2;
import com.googlecode.ochagl.math.Vec3;



public class MeshJogl implements Mesh {

    private ShadeMode shadeMode_ = ShadeMode.GOURAUD;

    private Vertex vdata_ = null;

    private TextureJogl texture_ = null;
    
    private MaterialJogl material_ = null;

    private int state_ = CULL_FACE|LIGHTING;

    /**
     * コンストラクタ
     */
    public MeshJogl() {
    }

    /**
     * テクスチャ設定
     */
    public void setTexture(Texture tex)
    {
        texture_ = (TextureJogl) tex;
    }

    /**
     * マテリアル設定
     */
    public void setMaterial(Material mat) {
        material_ = (MaterialJogl) mat;
    }

    /**
     * レンダリングステートを設定する．
     *
     * @param state シェードモード
     */
    public void setState(int state) {
        state_ |= state;
    }

    /**
     * レンダリングステートを設定する．
     *
     * @param state シェードモード
     */
    public void unsetState(int state) {
        state_ &= ~state;
    }

    /**
     * レンダリングステートを取得する．
     *
     * @return state シェードモード
     */
    public int getState() {
        return state_;
    }

    public void setShadeMode(ShadeMode mode)
    {
        shadeMode_ = mode;
    }

    public ShadeMode getShadeMode()
    {
        return shadeMode_;
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
    public Vertex getVertex() {
        return vdata_;
    }
    
    /**
     * 描画
     */
    public void render(GraphicContext gcon)
    {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();

        if (getShadeMode() == ShadeMode.GOURAUD) {
            gl.glShadeModel(GL.GL_SMOOTH);
        } else {
            gl.glShadeModel(GL.GL_FLAT);
        }

        //gl.glCullFace(GL.GL_CCW);   // 反時計回りが前面
        if ((state_ & CULL_FACE) > 0 )
            gl.glEnable(GL.GL_CULL_FACE);
        
        if (getShadeMode() == ShadeMode.WIRE) {
            gl.glDisable(GL.GL_TEXTURE_2D);
            gl.glDisable(GL.GL_LIGHTING);
        } else {
            if (texture_ != null) {
                texture_.bind(gl);
                gl.glEnable(GL.GL_TEXTURE_2D);
                gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
                gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
            } else {
                gl.glDisable(GL.GL_TEXTURE_2D);
            }
        }

        if (material_ != null) {
            gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
            material_.applyMaterial(gcon);
        }
        
        if ((state_ & LIGHTING) == 0) {
            gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            gl.glDisable(GL.GL_LIGHTING);
        }
        
        int[] is = vdata_.getGT3I();
        Vec3[] vs = vdata_.getVertexTop();
        Vec3[] ns = vdata_.getVnormalTop();
        TexCoord2[] ts = vdata_.getUvTop();
        for (int i = 0; i < is.length; i += 3) {

            int i0 = is[i + 0];
            int i1 = is[i + 1];
            int i2 = is[i + 2];

            if (getShadeMode() == ShadeMode.WIRE) {
                gl.glBegin(GL.GL_LINE_LOOP);
            } else {
                gl.glBegin(GL.GL_POLYGON);
            }
            if (texture_ != null) {

                gl.glTexCoord2f(ts[i0].x, ts[i0].y);
                gl.glNormal3f  (ns[i0].x, ns[i0].y, ns[i0].z);
                gl.glVertex3f  (vs[i0].x, vs[i0].y, vs[i0].z);

                gl.glTexCoord2f(ts[i1].x, ts[i1].y);
                gl.glNormal3f  (ns[i1].x, ns[i1].y, ns[i1].z);
                gl.glVertex3f  (vs[i1].x, vs[i1].y, vs[i1].z);

                gl.glTexCoord2f(ts[i2].x, ts[i2].y);
                gl.glNormal3f  (ns[i2].x, ns[i2].y, ns[i2].z);
                gl.glVertex3f  (vs[i2].x, vs[i2].y, vs[i2].z);

            } else {

                gl.glNormal3f  (ns[i0].x, ns[i0].y, ns[i0].z);
                gl.glVertex3f  (vs[i0].x, vs[i0].y, vs[i0].z);

                gl.glNormal3f  (ns[i1].x, ns[i1].y, ns[i1].z);
                gl.glVertex3f  (vs[i1].x, vs[i1].y, vs[i1].z);

                gl.glNormal3f  (ns[i2].x, ns[i2].y, ns[i2].z);
                gl.glVertex3f  (vs[i2].x, vs[i2].y, vs[i2].z);
            }
            gl.glEnd();
            
        }
        gl.glEnable(GL.GL_LIGHTING);
    }
}