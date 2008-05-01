package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.DebugFont;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.core.InterpolateLinear;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.View2d;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Vec2;


// アニメーションクラスによる移動（補間）
public class Move2dExec extends SampleBox {
    public View2d view2d = ResourceFactory.createView2d();
    public Move2dExec() {
		super("スプライト移動のテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
		        view2d.show(true);
		        GameBox.drawman().addView(0, view2d);
		        PlayerTask playerTask = new PlayerTask();
		        GameBox.taskman().addTask(playerTask);
			}
		});
		GameBox.startAnime();
    }

    public static void main(String argv[]) {
        new Move2dExec();
    }

    class PlayerTask extends AbstractTask {

        private static final float SPEED = 3.0f;

        private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_UP);

        private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_DOWN);

        private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_LEFT);

        private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);

        private Object2d sprite_ = null;

        public Vec2 pos = new Vec2();
        InterpolateLinear a = new InterpolateLinear();

        public PlayerTask() {
            super(0);

            Texture texture = GameBox.loadTexture("sankaku.png");
            sprite_ = ResourceFactory.createObject2d("player");
            sprite_.setTexture(texture);
            sprite_.setUv(0, 0, 32, 32);
            sprite_.setWidth(32);
            sprite_.setHeight(32);
            view2d.addRenderObject(0, sprite_);

            a.setDuration(60);
            a.setValue(0.0f, 10);
            a.setValue(1.0f, 300);
            a.setStartTime(GameBox.loopCount());
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

        public void autoMove() {
            pos.x = a.getValue(GameBox.loopCount());
        }

        public void execute() {
            //checkInput();
            autoMove();
            sprite_.setPosition(pos);
        }
    }
}
