package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.View2d;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.musics.Sound;


// 爆発アニメーションテスト
public class SoundExec extends SampleBox {
    public View2d view2d = ResourceFactory.createView2d();

    public SoundExec() {
		super("サウンドのテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
		        view2d.show(true);
		        GameBox.drawman().addView(0, view2d);
		        PlayerTask playerTask = new PlayerTask("player");
		        GameBox.taskman().addTask(playerTask);
			}
		});
		GameBox.startAnime();
    }

    public static void main(String argv[]) {
        new SoundExec();
    }

    class PlayerTask extends AbstractTask {

        private InputAction inPlay_ = inputman().createTriggerInput(KeyEvent.VK_SPACE);

        public PlayerTask(String name) {
            super(name, 0);
        }

        public void execute() {
            if (inPlay_.isPressed()) {
                new Sound("puu57.wav").play();
            }
        }
    }
}

