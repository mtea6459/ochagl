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
import com.googlecode.ochagl.math.Plane;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;


public class PlaneIntersectExec extends SampleBox {

	public PlaneIntersectExec() {
		super("PlaneIntersect");
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
		new PlaneIntersectExec();
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

		private Object3d plane_ = null;

		private Object3d point_ = null;

		private PlayerTask(String name) {
			super(name, 0);
			obj_ = ResourceFactory.createObject3d("Player");
			obj_.show(true);

			Texture texture = GameBox.loadTexture("earth.png");

			plane_ = Primitive3dFactory.createFloor(LINE, LINE, texture);
			plane_.show(true);

			point_ = Primitive3dFactory.createCrossLine(0.2f);
			point_.show(true);

			obj_.add(line_.obj);
			obj_.add(point_);
			obj_.add(plane_);

			reset();
		}

		protected void reset() {
			plane_.reset();
			plane_.rotateY(Rad.toRad(180.0f));
			line_.reset();
		}

		protected void checkInput() {
			if (inLt_.isPressed()) {
				line_.obj.rotateY(Rad.toRad(-OMEGA));
				plane_.rotateY(Rad.toRad(-OMEGA));

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
			Vec3 v3 = new Vec3();

			Object3d obj = Primitive3dFactory.createLineSquare(LINE);

			Line tmpLine = new Line(); // Žó‚¯“n‚µ—p

			Line line = new Line(); // ŒvŽZ—p

			Plane plane = new Plane();

			float[] ts = new float[1];

			LineObject() {
				obj.show(true);
			}

			void reset() {
				line_.obj.reset();
			}

			void update() {
				obj.getVectorY(v3);
				line.Q.scale(-LINE / 2, v3);
				line.Q.add(obj.getPosition());
				line.V.scale(LINE, v3);

				plane_.getVectorZ(v3);
				plane.set(plane_.getPosition(), v3);

				tmpLine.set(line);
				Mat4 tm = new Mat4();
				plane_.getWorldMatrix().invert(tm);
				tm.transform(tmpLine.Q);

				point_.setPosition(Vec3.zero);
				int res = Intersect.plane(tmpLine, plane, ts);
				if (res == 1) {
					if (0 <= ts[0] && ts[0] <= 1) {
						point_.setPosition(line.getP(ts[0]));
					}
				}
			}
		}
	}
}