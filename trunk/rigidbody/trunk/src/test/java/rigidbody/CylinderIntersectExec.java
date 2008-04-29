package rigidbody;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.task.Generic3dTask;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Line;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;


public class CylinderIntersectExec extends SampleBox {

	public CylinderIntersectExec() {
		super("CylinderIntersect");
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
		new CylinderIntersectExec();
	}

	private class PlayerTask extends Generic3dTask {

		private static final float RADIUS = 0.8f;

		private static final float HEIGHT = 2.5f;

		private static final float OMEGA = 2.0f;

		private static final float LINE = 3.0f;

        private InputAction inTUp_ = inputman().createNormalInput(KeyEvent.VK_E);

        private InputAction inTDn_ = inputman().createNormalInput(KeyEvent.VK_Q);

        private InputAction inTLt_ = inputman().createNormalInput(KeyEvent.VK_A);

        private InputAction inTRt_ = inputman().createNormalInput(KeyEvent.VK_D);

        private InputAction inTF_ = inputman().createNormalInput(KeyEvent.VK_W);

        private InputAction inTB_ = inputman().createNormalInput(KeyEvent.VK_S);

        private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_UP);

        private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_DOWN);

        private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_LEFT);

        private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);

        private InputAction inRotate_ = inputman().createNormalInput(KeyEvent.VK_SPACE);

        private InputAction inReset_ = inputman().createNormalInput(KeyEvent.VK_R); 

		private LineObject line_ = new LineObject();

		private Object3d cylinder_ = null;

		private Object3d point1_ = null;

		private Object3d point2_ = null;

		public PlayerTask(String name) {

			super(name, 0);

			obj_ = ResourceFactory.createObject3d("Player");
			obj_.show(true);

			line_.obj.addTo(obj_);

			Texture texture = GameBox.loadTexture("earth.png");

			cylinder_ = Primitive3dFactory.createCylinder(RADIUS, HEIGHT, 16,
					texture);
			cylinder_.show(true);
			cylinder_.addTo(obj_);
			cylinder_.setPosition(0, -1.4f, 0);

			point1_ = Primitive3dFactory.createCrossLine(0.2f);
			point2_ = Primitive3dFactory.createCrossLine(0.2f);
			point1_.addTo(obj_);
			point2_.addTo(obj_);
			point1_.show(true);
			point2_.show(true);

			reset();
		}

		protected void reset() {
			obj_.reset();
			cylinder_.reset();
			line_.obj.reset();
			line_.obj.getPosition().set(0, 0, 0);
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

			float val = 0.01f;
			if (inTLt_.isPressed()) {
				line_.obj.moveX(val);
			}
			if (inTRt_.isPressed()) {
				line_.obj.moveX(-val);
			}
			if (inTUp_.isPressed()) {
				line_.obj.moveY(val);
			}
			if (inTDn_.isPressed()) {
				line_.obj.moveY(-val);
			}
			if (inTF_.isPressed()) {
				line_.obj.moveZ(val);
			}
			if (inTB_.isPressed()) {
				line_.obj.moveZ(-val);
			}

			if (inReset_.isPressed()) {
				reset();
			}

		}

		public void execute() {
			checkInput();
			line_.update();
		}

		private class LineObject {

			Quat quat = new Quat();

			Mat4 m4 = new Mat4();

			Vec3 v3 = new Vec3();

			Vec3 p3 = new Vec3();

			Object3d obj = Primitive3dFactory.createLineSquare(LINE);

			float omega = 0.0f;

			float ts[] = new float[2];// Žó‚¯“n‚µ—p

			Line tmpLine = new Line(); // Žó‚¯“n‚µ—p

			Line line = new Line(); // ŒvŽZ—p

			LineObject() {
				obj.show(true);

			}

			void update() {
				obj.getVectorY(v3);
				line.Q.scale(-LINE / 2, v3);
				line.Q.add(obj.getPosition());
				line.V.scale(LINE, v3);

				tmpLine.set(line);
				Mat4 tm = new Mat4();
				cylinder_.getWorldMatrix().invert(tm);
				tm.transform(tmpLine.Q);

				point1_.setPosition(Vec3.zero);
				point2_.setPosition(Vec3.zero);
				Vec3 vecs[] = new Vec3[2];
				vecs[0] = new Vec3();
				vecs[1] = new Vec3();
				int res = Intersect.cylinder(tmpLine, RADIUS, HEIGHT, vecs);
				if (res == 1) {
					point1_.setPosition(vecs[0]);
				} else {
					if ((res & Intersect.RES1) > 0)
						point1_.setPosition(vecs[0]);
					if ((res & Intersect.RES2) > 0)
						point2_.setPosition(vecs[1]);
				}
			}

			void rotateAxis() {
				obj.getVectorY(v3);

				Mat4 mat = new Mat4(cylinder_.getWorldMatrix());
				mat.transpose();
				mat.transform(v3);

				omega = Rad.toRad(OMEGA * 2);
				quat.rotateAxis(v3, omega);

				Mat4 rmat = new Mat4();
				rmat.setIdentity();
				rmat.setRotation(quat);
				cylinder_.getLocalMatrix().mul(rmat);

			}
		}
	}
}
