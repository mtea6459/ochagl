package com.googlecode.ochagl.graphics.impl;

import java.util.Iterator;

import com.googlecode.ochagl.core.SortedList;
import com.googlecode.ochagl.core.TreeImpl;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.ShadeMode;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Vec3;



/**
 * 汎用３Ｄオブジェクト．描画ツリーのノード（座標系）を表す.
 * <li>右手座標系</li>
 * <li>右手回転系</li>
 * <li>縦書きベクトル</li>
 * <li>点は右から掛ける</li>
 */
public class Object3dImpl extends TreeImpl implements Object3d {

	/**
	 * ワールドマトリックス．
	 */
	protected Mat4 worldMatrix_ = new Mat4();

	/**
	 * ローカルマトリックス．
	 */
	protected Mat4 localMatrix_ = new Mat4();

	/**
	 * この座標系がもつ，メッシュのリスト．
	 */
	protected SortedList meshList_ = new SortedList();

	/**
	 * この座標系の座標．
	 */
	protected Vec3 pos_ = new Vec3();

	/**
	 * 表示フラグ．true:表示 false:非表示
	 */
	private boolean isShow_ = false;

	/**
	 * 描画フラグ．true:描画 false:非描画
	 */
	private boolean isAlive_ = true;

	/**
	 * コンストラクタ．
	 *
	 * @param name この座標系の名前
	 */
	public Object3dImpl(final String name) {
		super(name);
		reset();
	}

	/**
	 * コンストラクタ.
	 */
	public Object3dImpl() {
		this("");
	}

	/**
	 * 再初期化する．
	 */
	public void reset() {
		localMatrix_.setIdentity();
		worldMatrix_.setIdentity();
		setPosition(Vec3.zero);
	}

	/**
	 * 表示指定する.
	 *
	 * @param b true:表示 false:非表示
	 */
	public void show(final boolean b) {
		isShow_ = b;
	}

	/**
	 * 表示されているか検査する.
	 *
	 * @return true:表示 false:非表示
	 */
	public boolean isShow() {

		return isShow_;
	}

	/**
	 * 描画ツリーに接続されているか検査する.
	 *
	 * @return true:接続されている false:接続されていない
	 */
	public boolean isAlive() {

		return isAlive_;
	}

	/**
	 * 描画ツリーから外す.
	 */
	public void kill() {
		isAlive_ = false;
	}

	/**
	 * ワールドマトリックスを取得する．
	 *
	 * @return ワールドマトリックス
	 */
	public Mat4 getWorldMatrix() {

		return worldMatrix_;
	}

	/**
	 * ローカルマトリックスを取得する．
	 *
	 * @return ローカルマトリックス
	 */
	public Mat4 getLocalMatrix() {

		return localMatrix_;
	}

	/**
	 * ローカルマトリックスを設定する．
	 *
	 * @param m ローカルマトリックス
	 */
	public void setLocalMatrix(final Mat4 m) {
		localMatrix_.set(m);
	}

	/**
	 * メッシュを追加する．
	 *
	 * @param mesh メッシュ
	 */
	public void addMesh(final Mesh mesh) {
		meshList_.add(0, mesh);
	}

	/**
	 * メッシュを削除する．
	 *
	 * @param mesh メッシュ
	 */
	public void removeMesh(final Mesh mesh) {
		meshList_.remove(mesh);
	}

	/**
	 * シェードモードを設定する．
	 *
	 * @param mode シェードモード
	 */
	public void setShadeMode(final ShadeMode mode) {

		Iterator it = getMeshIterator();

		while (it.hasNext()) {

			Mesh mesh = (Mesh) it.next();
			mesh.setShadeMode(mode);
		}
	}

	/**
	 * 位置を取得する．
	 *
	 * @return 位置ベクトル
	 */
	public Vec3 getPosition() {

		return pos_;
	}

	/**
	 * 位置を設定する．
	 *
	 * @param v 位置ベクトル
	 */
	public void setPosition(final Vec3 v) {
		setPosition(v.x, v.y, v.z);
	}

	/**
	 * 位置を設定する．
	 *
	 * @param x Ｘ座標
	 * @param y Ｙ座標
	 * @param z Ｚ座標
	 */
	public void setPosition(final float x, final float y, final float z) {
		localMatrix_.m03 = x;
		localMatrix_.m13 = y;
		localMatrix_.m23 = z;
		pos_.set(x, y, z);
	}

	/**
	 * ローカルマトリックスのＸ軸方向に移動させる．
	 *
	 * @param val 移動量
	 */
	public void moveX(final float val) {
		pos_.x += (localMatrix_.m00 * val);
		pos_.y += (localMatrix_.m10 * val);
		pos_.z += (localMatrix_.m20 * val);
	}

	/**
	 * ローカルマトリックスのＹ軸方向に移動させる．
	 *
	 * @param val 移動量
	 */
	public void moveY(final float val) {
		pos_.x += (localMatrix_.m01 * val);
		pos_.y += (localMatrix_.m11 * val);
		pos_.z += (localMatrix_.m21 * val);
	}

	/**
	 * ローカルマトリックスのＺ軸方向に移動させる．
	 *
	 * @param val 移動量
	 */
	public void moveZ(final float val) {
		pos_.x += (localMatrix_.m02 * val);
		pos_.y += (localMatrix_.m12 * val);
		pos_.z += (localMatrix_.m22 * val);
	}

	/**
	 * ローカルマトリックスをＸ軸回転させる．
	 *
	 * @param val 回転量
	 */
	public void rotateX(final float val) {

		Mat4 rmat = new Mat4();
		rmat.rotX(val);
		localMatrix_.mul(rmat);
	}

	/**
	 * ローカルマトリックスをＹ軸回転させる．
	 *
	 * @param val 回転量
	 */
	public void rotateY(final float val) {

		Mat4 rmat = new Mat4();
		rmat.rotY(val);
		localMatrix_.mul(rmat);
	}

	/**
	 * ローカルマトリックスをＺ軸回転させる．
	 *
	 * @param val 回転量
	 */
	public void rotateZ(final float val) {

		Mat4 rmat = new Mat4();
		rmat.rotZ(val);
		localMatrix_.mul(rmat);
	}

	/**
	 * ローカルマトリックスを任意軸回転させる．
	 *
	 * @param quat DOCUMENT ME!
	 */
	public void rotateAxis(final Quat quat) {

		Mat4 rmat = new Mat4();
		rmat.setRotation(quat);
		localMatrix_.mul(rmat);

		//localMatrix_.setRotation(axis);
	}

	/**
	 * ワールドマトリックスのＸ軸方向のベクトルを取得する．
	 *
	 * @param out 格納用ベクトル
	 */
	public void getVectorX(final Vec3 out) {
		out.x = worldMatrix_.m00;
		out.y = worldMatrix_.m10;
		out.z = worldMatrix_.m20;
	}

	/**
	 * ワールドマトリックスのＹ軸方向のベクトルを取得する．
	 *
	 * @param out 格納用ベクトル
	 */
	public void getVectorY(final Vec3 out) {
		out.x = worldMatrix_.m01;
		out.y = worldMatrix_.m11;
		out.z = worldMatrix_.m21;
	}

	/**
	 * ワールドマトリックスのＺ軸方向のベクトルを取得する．
	 *
	 * @param out 格納用ベクトル
	 */
	public void getVectorZ(final Vec3 out) {
		out.x = worldMatrix_.m02;
		out.y = worldMatrix_.m12;
		out.z = worldMatrix_.m22;
	}

	/**
	 * マトリックスを指定した方向へ向ける．
	 *
	 * @param lat 目標ベクトル
	 */
	public void lookat(final Vec3 lat) {

		Mat4 mat = localMatrix_;
		Vec3 up = new Vec3(0, 1, 0);
		Vec3 lt = new Vec3();
		Vec3 at = new Vec3();

		at.sub(lat, pos_);
		at.normalize();

		lt.cross(up, at);
		lt.normalize();

		up.cross(at, lt);
		up.normalize();

		mat.m00 = lt.x;
		mat.m01 = up.x;
		mat.m02 = at.x;
		mat.m10 = lt.y;
		mat.m11 = up.y;
		mat.m12 = at.y;
		mat.m20 = lt.z;
		mat.m21 = up.z;
		mat.m22 = at.z;
	}

	/**
	 * メッシュのイテレータを取得する．
	 *
	 * @return メッシュのイテレータ
	 */
	public Iterator getMeshIterator() {

		return meshList_.iterator();
	}

	/**
	 * レンダリングツリーのマトリックスを計算する．
	 *
	 * @param matrix レンダリングの起点となるワールドマトリックス
	 */
	public void setupTree(final Mat4 matrix) {
		setPosition(pos_.x, pos_.y, pos_.z);
		worldMatrix_.mul(matrix, localMatrix_);
		Object3dImpl o = (Object3dImpl) getChild();
		while (o != null) {
			o.setupTree(worldMatrix_);
			o = (Object3dImpl) o.getNext();
		}
	}

	/**
	 * レンダリングする．
	 *
	 * @param gcon 描画コンテキスト
	 */
	public void render(final GraphicContext gcon) {
	}
}
