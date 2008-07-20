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
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;



public class QuatRotateExec extends SampleBox{

	public QuatRotateExec() {
		super("クオータニオンによる任意軸回転のテスト");
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
		new QuatRotateExec();
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

			Texture texture = GameBox.loadTexture("earth.png");

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
			// line_.Q.set(0,1,0,0);
			line_.Q.set(sphere_.getLocalMatrix());
			// sphere_.getLocalMatrix().set(line_.Q);

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

			Quat Q = new Quat();

			Quat newQ = new Quat();

			Mat3 R = new Mat3(); // 姿勢

			Vec3 omega = new Vec3();

			Object3d obj = Primitive3dFactory.createLineSquare(LINE);

			float speed = Rad.toRad(90);

			Line() {
				obj.show(true);
			}

			void rotateAxis() {
				float dt = 1 / 60.0f;

				obj.getVectorY(omega);
				R.set(sphere_.getWorldMatrix());
				R.transpose();
				R.transform(omega);

				newQ.rotateAxis(omega, speed * dt);
				Q.mul(newQ);
				sphere_.getLocalMatrix().set(Q);
				sphere_.getLocalMatrix().normalize();
			}
		}
	}
}
