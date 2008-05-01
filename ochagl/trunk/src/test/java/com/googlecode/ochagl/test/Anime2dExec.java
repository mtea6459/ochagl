package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.DebugFont;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.core.InterpolateStep;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.View2d;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Vec2;


// 爆発アニメーションテスト
public class Anime2dExec extends SampleBox {
	public View2d view2d = ResourceFactory.createView2d();

	public Anime2dExec() {
		super("2Dアニメーションのテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
				view2d.show(true);
				DebugFont.create();
				GameBox.drawman().addView(0, view2d);
				PlayerTask playerTask = new PlayerTask("player");
				GameBox.taskman().addTask(playerTask);
				GameBox.wait(false);
			}
		});
		GameBox.startAnime();
	}

	class PlayerTask extends AbstractTask {

		private static final float SPEED = 3.0f;

		private InputAction inUp_ = inputman()
				.createNormalInput(KeyEvent.VK_UP);

		private InputAction inDn_ = inputman().createNormalInput(
				KeyEvent.VK_DOWN);

		private InputAction inLt_ = inputman().createNormalInput(
				KeyEvent.VK_LEFT);

		private InputAction inRt_ = inputman().createNormalInput(
				KeyEvent.VK_RIGHT);

		private Object2d sprite_ = null;

		public Vec2 pos = new Vec2();

		InterpolateStep a = new InterpolateStep();
		List animeuv = new ArrayList();

		public PlayerTask(String name) {
			super(name, 0);

			// a.setValue(0.0f, 0);
			// a.setValue(0.3f, 1);
			// a.setValue(0.6f, 2);
			// a.setLoop(true);
			// a.setDuration(60);
			// animeuv.add(new int[]{96*0,0,96,96});
			// animeuv.add(new int[]{96*1,0,96,96});
			// animeuv.add(new int[]{96*2,0,96,96});

			for (int i = 0; i < 16; i++) {
				a.setValue(1.0f / 16 * i, i);
			}
			a.setLoop(true);
			a.setDuration(120);
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) {
					animeuv.add(new int[] { 96 * j, 96 * i, 96, 96 });
				}
			}
			a.setStartTime(GameBox.loopCount());

			Texture texture = GameBox.loadTexture("burst.png");
			sprite_ = ResourceFactory.createObject2d("player");
			sprite_.setTexture(texture);
			sprite_.setUv(0, 0, 96, 96);
			sprite_.setWidth(96);
			sprite_.setHeight(96);
			view2d.addRenderObject(0, sprite_);

			reset();
		}

		protected void reset() {
			sprite_.show(true);
		}

		protected void checkInput() {
			if (inUp_.isPressed()) {
				pos.y -= SPEED;
			} else if (inDn_.isPressed()) {
				pos.y += SPEED;
			}

			if (inLt_.isPressed()) {
				pos.x -= SPEED;
			} else if (inRt_.isPressed()) {
				pos.x += SPEED;
			}

		}

		public void execute() {
			checkInput();
			int index = (int) a.getValue(GameBox.loopCount());
			// System.out.println("index:" + index);
			int[] uv = (int[]) animeuv.get(index);
			sprite_.setUv(uv[0], uv[1], uv[2], uv[3]);
			sprite_.setPosition(pos);
			DebugFont.print(10, 10, "FPS:" + GameBox.fps());
		}
	}

	public static void main(String argv[]) {
		new Anime2dExec();
	}
}
