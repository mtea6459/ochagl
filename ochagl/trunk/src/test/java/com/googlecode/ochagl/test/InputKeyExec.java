package com.googlecode.ochagl.test;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.app.SampleBox;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.input.InputAction;


public class InputKeyExec extends SampleBox {

	public InputKeyExec() {
		super("入力キーのテスト");
		GameBox.addInitialize(new AddHook() {
			public void execute() {
				TaskManager man = TaskManager.getInstance();
				InputKeyTask playerTask = new InputKeyTask("player");
				man.addTask(playerTask);
			}
		});
		GameBox.startAnime();
	}

	public static void main(String argv[]) {
		new InputKeyExec();
	}

	class InputKeyTask extends AbstractTask {

		private InputAction inUp_ = inputman().createTriggerInput(
				KeyEvent.VK_UP);

		private InputAction inDn_ = inputman().createTriggerInput(
				KeyEvent.VK_DOWN);

		private InputAction inLt_ = inputman().createTriggerInput(
				KeyEvent.VK_LEFT);

		private InputAction inRt_ = inputman().createTriggerInput(
				KeyEvent.VK_RIGHT);

		public InputKeyTask(String name) {
			super(name, 0);
		}

		public void execute() {
			if (inUp_.isPressed()) {
				System.out.println("inUp pressed.");
			} else if (inDn_.isPressed()) {
				System.out.println("inDn pressed.");
			}

			if (inLt_.isPressed()) {
				System.out.println("inLt pressed.");
			} else if (inRt_.isPressed()) {
				System.out.println("inRt pressed.");
			}
		}
	}
}