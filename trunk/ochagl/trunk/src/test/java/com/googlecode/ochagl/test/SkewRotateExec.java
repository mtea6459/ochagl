package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.graphics.Material;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.Vertex;
import com.googlecode.ochagl.graphics.task.Generic3dTask;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Mat3;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;



public class SkewRotateExec extends SampleBox {

	public SkewRotateExec() {
		super("歪対象行列による任意軸回転のテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
				PlayerTask playerTask = new PlayerTask(TASK_PLAYER);
				GameBox.taskman().addTask(playerTask);
				GameBox.world().add(playerTask.getObject());
			}
		});
		GameBox.startAnime();
	}

	public static void main(String argv[]) {
		new SkewRotateExec();
	}
	private class PlayerTask extends Generic3dTask {

		private static final float RADIUS = 1.0f;

		private static final float OMEGA = 2.0f;

		private static final float LINE = 3.0f;

        private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_UP);

        private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_DOWN);

        private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_LEFT);

        private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);

        private InputAction inRotate_ = inputman().createNormalInput(KeyEvent.VK_SPACE);

        private InputAction inReset_ = inputman().createNormalInput(KeyEvent.VK_R); 

		private Line line_ = new Line();

		private Object3d sphere_ = null;

		public PlayerTask(String name) {
			super(name, 0);

			obj_ = ResourceFactory.createObject3d("Player");
			obj_.show(true);

			line_.obj.addTo(obj_);

			Texture texture = GameBox.loadTexture(TEX_EARTH);

			Material material = ResourceFactory.createMaterial();
			material.init();

			Mesh mesh = ResourceFactory.createMesh();
			mesh.setVertex(new Vertex().createSphere(RADIUS, 16));
			mesh.setTexture(texture);
			mesh.setMaterial(material);

			sphere_ = ResourceFactory.createObject3d("Sphere");
			sphere_.show(true);
			sphere_.addMesh(mesh);
			sphere_.addTo(obj_);

			Object3d axis = Primitive3dFactory.createAxis(2.0f);
			axis.addTo(sphere_);
			axis.show(true);

			reset();
		}

		protected void reset() {
			obj_.reset();
			sphere_.reset();
			line_.obj.reset();
			obj_.getPosition().set(0, 0, 0);
		}

		protected void checkInput() {
			if (inRotate_.isPressed()) {
				line_.rotateAxis();
			}
			if (inLt_.isPressed()) {
				line_.obj.rotateY(Rad.toRad(-OMEGA));
			}
			if (inRt_.isPressed()) {
				line_.obj.rotateY(Rad.toRad(OMEGA));
			}
			if (inUp_.isPressed()) {
				line_.obj.rotateX(Rad.toRad(OMEGA));
			}
			if (inDn_.isPressed()) {
				line_.obj.rotateX(Rad.toRad(-OMEGA));
			}
			if (inReset_.isPressed()) {
				reset();
			}

		}

		public void execute() {
			checkInput();
		}

		private class Line {

			Mat3 V = new Mat3(); // 各軸の微小速度

			Mat3 R = new Mat3(); // 姿勢

			Mat3 O = new Mat3(); // 歪対象行列

			Vec3 omega = new Vec3();

			Object3d obj = Primitive3dFactory.createLineSquare(LINE);

			float speed = Rad.toRad(2);

			Line() {
				obj.show(true);
			}

			// ある回転軸の周りを等速円運動するときの質点の速度は
			//
			// V = ω x R 
			// x:外積記号
			//
			// ただし、Vは速度ベクトル、ωは角速度（とその回転軸）、
			// Rは質点までの位置ベクトルとする。
			// 
			// ここでRを|R|(姿勢マトリックス)として、座標系のx,y,zの単位ベクトルが
			// 3つの質点と考えたとき、角速度から各質点の速度を求める。
			// このときのωの外積を行列で表したものを歪対象行列という(|ω*|とする)。
			// ここで、質点の微小変化d|R| (質点の位置微分)を求めると
			//
			// d|R|/dt = |ω*||R|
			// d|R| = |ω*||R|dt
			//
			// となる
			//
			void rotateAxis() {
				float dt = 1 / 60.0f;

				// 角速度をローカルに変換後、そのベクトルで
				// 歪対象行列をつくる
				obj.getVectorY(omega);
				omega.normalize();
				omega.scale(speed * dt);
				O.skew(omega);

				R.set(sphere_.getLocalMatrix());

				// 積分
				for (int i = 0; i < 60; i++) {
					V.mul(O, R);
					R.add(V);
				}
				sphere_.getLocalMatrix().set(R);
				sphere_.getLocalMatrix().normalize();
			}
		}
	}
}
