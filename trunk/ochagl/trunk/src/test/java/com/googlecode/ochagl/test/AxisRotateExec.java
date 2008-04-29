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
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;



public class AxisRotateExec extends SampleBox {

	public AxisRotateExec() {
		super("”CˆÓŽ²‰ñ“]‚ÌƒeƒXƒg");
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
		new AxisRotateExec();
	}

	public class PlayerTask extends Generic3dTask {

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

			Quat quat = new Quat();

			Mat4 m4 = new Mat4();

			Vec3 v3 = new Vec3();

			Vec3 p3 = new Vec3();

			Object3d obj = Primitive3dFactory.createLineSquare(LINE);

			float omega = 0.0f;

			Line() {
				obj.show(true);
			}

			void rotateAxis() {
				obj.getVectorY(v3);

				Mat4 mat = new Mat4(sphere_.getWorldMatrix());
				mat.transpose();
				mat.transform(v3);

				omega = Rad.toRad(OMEGA * 2);
				quat.rotateAxis(v3, omega);
				Mat4 rmat = new Mat4();
				rmat.setIdentity();
				rmat.setRotation(quat);
				sphere_.getLocalMatrix().mul(rmat);
			}
		}
	}
}
