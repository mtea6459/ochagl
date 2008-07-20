package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.task.Generic3dTask;
import com.googlecode.ochagl.graphics.task.GenericCameraTask;
import com.googlecode.ochagl.graphics.task.GenericLightTask;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Rad;



public class Sprite3dExec extends SampleBox {

	public Sprite3dExec() {
		super("カメラを動かして、ワールド座標が２Ｄ座標になるようにするテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
		        GenericLightTask lightTask = new GenericLightTask("",0);
		        ViewTask viewTask = new ViewTask("ViewTask");
		        lightTask.getLight().setPosition(0,0, CAMERA_POS);

		        GameBox.taskman().addTask(lightTask);
		        GameBox.taskman().addTask(viewTask);

		        GameBox.world().show(true);
		        GameBox.world().add(lightTask.getLight());

		        GameBox.drawman().addView(0, viewTask.getView());

		        PlayerTask playerTask = new PlayerTask("PlayerTask");
		        GameBox.taskman().addTask(playerTask);
		        GameBox.world().add(playerTask.getObject());
			}
		});
		GameBox.startAnime();
	}

	public static void main(String argv[]) {
		new Sprite3dExec();
	}

    private static final float CAMERA_POS = -700;

    private class PlayerTask extends Generic3dTask {

        private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_UP);

        private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_DOWN);

        private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_LEFT);

        private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);

        private InputAction inReset_ = inputman().createNormalInput(KeyEvent.VK_R); 

		private Object3d plane_ = null;

		private Object3d point_ = null;

		private PlayerTask(String name) {
			super(name, 0);

            obj_ = ResourceFactory.createObject3d("Player");
			obj_.show(true);

			Texture texture = GameBox.loadTexture("earth.png");

			plane_ = Primitive3dFactory.createFloor(640, 480, texture);
			plane_.show(true);

			point_ = Primitive3dFactory.createCrossLine(0.2f);
			point_.show(true);

			obj_.add(point_);
			obj_.add(plane_);

			reset();
		}

		protected void reset() {
			plane_.reset();
			plane_.rotateY(Rad.toRad(180.0f));
			//plane_.rotateY(Rad.toRad(90.0f));
            plane_.getPosition().set(0,0,0);
		}

		protected void checkInput() {

			if (inLt_.isPressed()) {
			}
			if (inRt_.isPressed()) {
			}
			if (inUp_.isPressed()) {
			}
			if (inDn_.isPressed()) {
			}

			if (inReset_.isPressed()) {
				reset();
			}

		}

		public void execute() {
			checkInput();
		}
    }

    class ViewTask extends GenericCameraTask {

        private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_E);

        private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_Q);

        private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_A);

        private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_D);

        private InputAction inGo_ = inputman().createNormalInput(KeyEvent.VK_W);

        private InputAction inBk_ = inputman().createNormalInput(KeyEvent.VK_S);

        public ViewTask(String name) {
            super(name, 0, GameBox.world(), GameBox.width(), GameBox.height());

            //float aspect = (float) height / width;
            //view_.setPerspective(Rad.toRad(45.0f), aspect, 1.0f, 1000.0f);
            float r = 320.0f / Rad.sin(Rad.toRad(45.0f/2));
            float z = r * Rad.cos(Rad.toRad(45.0f/2));
            getCamera().setPosition(0,0,-z);
        }

        protected void checkInput() {

            if (inGo_.isPressed()) {
                getCamera().moveZ(0.2f);
            }
            if (inBk_.isPressed()) {
                getCamera().moveZ(-0.2f);
            }
            if (inUp_.isPressed()) {
                getCamera().moveY(0.2f);
            }
            if (inDn_.isPressed()) {
                getCamera().moveY(-0.2f);
            }
            if (inLt_.isPressed()) {
                getCamera().moveX( 0.2f);
            }
            if (inRt_.isPressed()) {
                getCamera().moveX(-0.2f);
            }
        }

        public void execute() {
            checkInput();
        }
    }
}