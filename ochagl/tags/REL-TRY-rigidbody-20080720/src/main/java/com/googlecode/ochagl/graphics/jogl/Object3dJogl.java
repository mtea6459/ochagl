/**
 * 座標系クラス
 * 
 * 採用座標系など ・右手座標系 ・右手回転系 ・縦書きベクトル ・点は右から掛ける
 *  
 */
package com.googlecode.ochagl.graphics.jogl;

import java.util.Iterator;

import javax.media.opengl.GL;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.impl.Object3dImpl;
import com.googlecode.ochagl.math.Mat4;



public class Object3dJogl extends Object3dImpl {

    protected Mat4 wvm_ = new Mat4();
    
    /**
     * コンストラクタ
     */
    public Object3dJogl(String name) {
        super(name);
    }

    /**
     * コンストラクタ
     */
    public Object3dJogl() {
        this("");
    }

    /**
     * プリミティブの描画
     */
    public void render(GraphicContext gcon)
    {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();

        Iterator it = meshList_.iterator();
        while (it.hasNext()) {
            Mesh mesh = (Mesh) it.next();
            if (mesh != null) {
                wvm_.mul(gc.getViewMatrix(), worldMatrix_);
                gl.glMatrixMode(GL.GL_MODELVIEW);
                gl.glPushMatrix();
                gl.glLoadMatrixf(gc.matrixToArray(wvm_));
                mesh.render(gcon);
                gl.glPopMatrix();
            }
        }
    }
}