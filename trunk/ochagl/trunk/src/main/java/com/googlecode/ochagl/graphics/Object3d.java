/*
 * Created on 2005/01/03
 */
package com.googlecode.ochagl.graphics;

import java.util.Iterator;

import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Vec3;




/**
 * ３Ｄオブジェクトのインタフェイス．描画ツリーのノード（座標系）を表す.
 * <li>右手座標系</li>
 * <li>右手回転系</li>
 * <li>縦書きベクトル</li>
 * <li>点は右から掛ける</li>
 */
public interface Object3d extends Drawable {

    /**
     * 再初期化する．
     */
    void reset();

    /**
     * ワールドマトリックスを取得する．
     *
     * @return ワールドマトリックス
     */
    Mat4 getWorldMatrix();

    /**
     * ローカルマトリックスを取得する．
     *
     * @return ローカルマトリックス
     */
    Mat4 getLocalMatrix();

    /**
     * メッシュを追加する．
     *
     * @param mesh メッシュ
     */
    void addMesh(Mesh mesh);

    /**
     * メッシュを削除する．
     *
     * @param mesh メッシュ
     */
    void removeMesh(Mesh mesh);

    /**
     * メッシュのイテレータを取得する．
     *
     * @return メッシュのイテレータ
     */
    Iterator getMeshIterator();

    /**
     * シェードモードを設定する．
     *
     * @param mode シェードモード
     */
    void setShadeMode(ShadeMode mode);

    /**
     * ローカルマトリックスを設定する．
     *
     * @param m ローカルマトリックス
     */
    void setLocalMatrix(Mat4 m);

    /**
     * 位置を設定する．
     *
     * @param v 位置ベクトル
     */
    void setPosition(Vec3 v);

    /**
     * 位置を設定する．
     *
     * @param x Ｘ座標
     * @param y Ｙ座標
     * @param z Ｚ座標
     */
    void setPosition(
        float x,
        float y,
        float z);

    /**
     * 位置を取得する．
     *
     * @return 位置ベクトル
     */
    Vec3 getPosition();

    /**
     * DOCUMENT ME!
     *
     * @param matrix DOCUMENT ME!
     */
    void setupTree(Mat4 matrix);

    /**
     * ローカルマトリックスのＸ軸方向に移動させる．
     *
     * @param val 移動量
     */
    void moveX(float val);

    /**
     * ローカルマトリックスのＹ軸方向に移動させる．
     *
     * @param val 移動量
     */
    void moveY(float val);

    /**
     * ローカルマトリックスのＺ軸方向に移動させる．
     *
     * @param val 移動量
     */
    void moveZ(float val);

    /**
     * マトリックスを指定した方向へ向ける．
     *
     * @param lat 目標ベクトル
     */
    void lookat(Vec3 lat);

    /**
     * ローカルマトリックスをＸ軸回転させる．
     *
     * @param val 回転量
     */
    void rotateX(float val);

    /**
     * ローカルマトリックスをＹ軸回転させる．
     *
     * @param val 回転量
     */
    void rotateY(float val);

    /**
     * ローカルマトリックスをＺ軸回転させる．
     *
     * @param val 回転量
     */
    void rotateZ(float val);

    /**
     * ローカルマトリックスを任意軸回転させる．
     *
     * @param quat DOCUMENT ME!
     */
    void rotateAxis(Quat quat);

    /**
     * ワールドマトリックスのＸ軸方向のベクトルを取得する．
     *
     * @param out 格納用ベクトル
     */
    void getVectorX(Vec3 out);

    /**
     * ワールドマトリックスのＹ軸方向のベクトルを取得する．
     *
     * @param out 格納用ベクトル
     */
    void getVectorY(Vec3 out);

    /**
     * ワールドマトリックスのＺ軸方向のベクトルを取得する．
     *
     * @param out 格納用ベクトル
     */
    void getVectorZ(Vec3 out);

    /**
     * レンダリングする．
     *
     * @param gcon 描画コンテキスト
     */
    void render(GraphicContext gcon);
}
