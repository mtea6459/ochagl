package com.googlecode.ochagl.graphics.jogl;

import java.util.Iterator;

import javax.media.opengl.GL;

import com.googlecode.ochagl.core.SortedList;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.View2d;


/**
 * DOCUMENT ME!
 * 
 * @author
 */
public class View2dJogl implements View2d {

    protected Object2d renderObject_ = null;

    /**
     * このビューを表示するかどうか．true:表示 false:非表示
     */
    private boolean isShow_ = false;

    /**
     * 表示する2Dオブジェクトのリスト
     */
    private SortedList objectList_ = new SortedList();

    /**
     * ビューのカラー（赤）.
     */
    protected float r_ = 0;

    /**
     * ビューのカラー（緑）.
     */
    protected float g_ = 0;

    /**
     * ビューのカラー（青）.
     */
    protected float b_ = 0.5f;

    private int state_;

    /**
     * デフォルトコンストラクタ.
     */
    public View2dJogl() {
        setClearColor(0.7f, 0.7f, 0.7f);
    }

    /**
     * フラグ指定版コンストラクタ.
     * 
     * @param state
     */
    public View2dJogl(int state) {
        this();
        state_ = state;
    }

    /**
     * 表示・非表示
     * 
     * @param b
     *            true：表示 false：非表示
     */
    public void show(final boolean b) {
        isShow_ = b;
    }

    /**
     * 表示チェック
     * 
     * @return true：表示 false：非表示
     */
    public boolean isShow() {
        return isShow_;
    }

    /**
     * クリアカラーを設定する
     * 
     * @param r
     *            赤
     * @param g
     *            緑
     * @param b
     *            青
     */
    public void setClearColor(final float r, final float g, final float b) {
        r_ = r;
        g_ = g;
        b_ = b;
    }

    /**
     * Viewステートの設定
     * 
     * @param state
     *            設定値フラグORで組み合わせる
     */
    public void setState(int state) {
        state_ |= state;
    }

    /**
     * Viewステートのを外す
     * 
     * @param state
     *            設定値フラグORで組み合わせる
     */
    public void unsetState(int state) {
        state_ &= ~state;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param gcon
     */
    public void begin(final GraphicContext gcon) {
        GraphicContextJogl gc = (GraphicContextJogl) gcon;
        GL gl = gc.getGL();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, gcon.getWidth(), gcon.getHeight(), 0, -1, 1);
        gl.glDisable(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_DEPTH_TEST);
        if ((state_ & CLEAR) == CLEAR) {
            gl.glClearColor(r_, g_, b_, 1.0f);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);
System.out.println("2dclear");
        }
    }

    /**
     * DOCUMENT ME!
     * 
     * @param gcon
     *            DOCUMENT ME!
     */
    public void end(final GraphicContext gcon) {
    }

    /**
     * 対象の2Dオブジェクト配下のツリーをレンダリングする。
     * <li>画面＝ワールド。真ん中を原点とする。</li>
     * <li>プライオリティ順に描画する。</li>
     * <li>objectList_に登録された2Dオブジェクトは子供を持つ場合がある。</li>
     * <li>そのため、レンダリング前に座標変換（setupTree）が必要となる。</li>
     * 
     * @param gcon
     *            グラフィックコンテキスト
     * 
     */
    public void render(GraphicContext gcon) {
        Iterator i = objectList_.iterator();
        while (i.hasNext()) {
            Object2d o = (Object2d) i.next();
            if (o.isShow()) {
                o.render(gcon);
            }
        }
    }

    /**
     * オブジェクトを描画ツリーから削除
     * 
     */
    public void remove() {
        Iterator i = objectList_.iterator();
        while (i.hasNext()) {
            Object2d o = (Object2d) i.next();
            if (!o.isAlive()) {
                objectList_.remove(o);
            }
        }
    }

    /**
     * 描画オブジェクトを登録する
     * 
     * @param priority
     *            描画優先順位
     * @param object
     *            描画対象のオブジェクト
     */
    public void addRenderObject(int priority, Object2d object) {
        objectList_.add(priority, object);
    }
}
