package com.googlecode.ochagl.graphics.jogl;

import java.util.Iterator;

import javax.media.opengl.GL;

import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.View3d;
import com.googlecode.ochagl.math.Mat4;


public class View3dJogl implements View3d {

    protected float fovx_ = 0;

    protected float aspect_ = 0;

    protected float near_ = 0;

    protected float far_ = 0;

    protected Mat4 viewMatrix_ = new Mat4();

    protected Mat4 tmat_ = new Mat4();

    protected Object3d renderObject_ = null;

    protected Object3d viewObject_ = null;

    protected float r_ = 0;

    protected float g_ = 0;

    protected float b_ = 0.5f;

    private boolean isShow_;

    private int state_;

    /**
     * デフォルトコンストラクタ
     */
    public View3dJogl() {
        super();
    }

    /**
     * フラグ指定版コンストラクタ.
     * @param state
     */
    public View3dJogl(int state) {
        this();
        state_ = state;
    }

    /**
     * クリアカラーを設定する
     * 
     * @param r
     * @param g
     * @param b
     */
    public void setClearColor(float r, float g, float b) {
        r_ = r;
        g_ = g;
        b_ = b;
    }

    /**
     * Viewステートの設定
     * @param state 設定値フラグORで組み合わせる
     */
    public void setState(int state) {
        state_ |= state;
    }

    /**
     * Viewステートのを外す
     * @param state 設定値フラグORで組み合わせる
     */
    public void unsetState(int state) {
        state_ &= ~state;
    }

    /**
     * 画角から透視投影変換行列を設定する
     * 
     * @param fovx
     *            横方向の画角
     * @param aspect
     *            縦/横
     * @param near
     *            近平面位置
     * @param far
     *            遠平面位置
     */
    public void setPerspective(float fovx, float aspect, float near, float far) {
        fovx_ = fovx;
        aspect_ = aspect;
        near_ = near;
        far_ = far;
    }

    /**
     * レンダリング対象の3Dオブジェクトを設定する。 普通はワールドオブジェクト。
     * 
     * @param object
     *            レンダリング対象となるオブジェクト
     */
    public void setRenderObject(Object3d object) {
        renderObject_ = object;
    }

    /**
     * レンダリング対象となるオブジェクトを取得する
     * 
     * @return レンダリング対象となるオブジェクト
     */
    public Object3d getRenderObject() {
        return renderObject_;
    }

    /**
     * ビューマトリックスの作成
     * 
     */
    private void buildViewMatrix() {

        //
        // ※ ビューは視線の向きが負の方向なので2軸(x,z)を反転させる。
        //
        tmat_.set(viewObject_.getWorldMatrix());
        // z 軸反転
        tmat_.m02 = -tmat_.m02;
        tmat_.m12 = -tmat_.m12;
        tmat_.m22 = -tmat_.m22;
        // x 軸反転
        tmat_.m00 = -tmat_.m00;
        tmat_.m10 = -tmat_.m10;
        tmat_.m20 = -tmat_.m20;

        // ローカル→ワールド行列を
        // ワールド→ローカル（ビュー）に変換する。
        // viewMatrix_ = worldMatrix_^-1
        viewMatrix_.invert(tmat_);
    }

    /**
     * レンダリング環境のセットアップ
     * 
     * @param gcon
     */
    public void begin(GraphicContext gcon) {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();

        if ((state_ & CLEAR) == CLEAR) {
            gl.glClearColor(r_, g_, b_, 1.0f); // カラープレーン初期値
            gl.glClearDepth(1.0); // デプスバッファの初期値
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        }

        gl.glEnable(GL.GL_DEPTH_TEST); // デプスバッファをイネーブル
        gl.glDepthFunc(GL.GL_LESS); // デプスバッファの計算法指定
        gl.glDepthMask(true); // デプスバッファをリードライト
        gl.glEnable(GL.GL_LIGHTING);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        float right = (float) (near_ * Math.tan(fovx_ / 2));
        float left = -right;
        float top = right * aspect_;
        float bottom = -top;
        gl.glFrustum(left, right, bottom, top, near_, far_);

        buildViewMatrix();

        gc.setViewMatrix(viewMatrix_);
    }

    /**
     * レンダリングの後始末
     * 
     * @param gcon
     */
    public void end(GraphicContext gcon) {
        // GraphicContextJogl gc = (GraphicContextJogl) gcon;
        // GL gl = gc.getGL();
    }

    /**
     * 対象の3Dオブジェクト配下のツリーをレンダリングする。
     * 
     * @param gcon
     *            グラフィックコンテキスト
     * 
     */
    public void render(GraphicContext gcon) {
        Object3d ro = getRenderObject();
        if (ro == null)
            return;
        Iterator it = ro.iterator();
        while (it.hasNext()) {
            Object3d obj = (Object3d) it.next();
            if (obj.isShow()) {
                obj.render(gcon);
            }
        }
    }

    /**
     * オブジェクトを描画ツリーから削除
     * 
     */
    public void remove() {
        Object3d ro = getRenderObject();
        if (ro == null)
            return;
        Iterator it = ro.iterator();
        while (it.hasNext()) {
            Object3d object = (Object3d) it.next();
            if (!object.isAlive()) {
                object.remove();
            }
        }
    }

    /**
     * ビューとして振舞うオブジェクト設定する。
     *
     * @param object ビューとして振舞うオブジェクト
     */
    public void setViewObject(Object3d object) {
        viewObject_ = object;
    }

    /**
     * ビューとして振舞うオブジェクト取得する。
     *
     * @retrun object ビューとして振舞うオブジェクト
     */
    public Object3d getViewObject() {
        return viewObject_;
    }

    public void show(boolean b) {
        isShow_ = b;
    }

    public boolean isShow() {
        return isShow_;
    }
}