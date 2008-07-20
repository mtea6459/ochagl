package rigidbody;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.DebugFont;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.input.InputAction;


public class SphereWindow {
	static final String TITLE = "ペナルティ法による球体の衝突テスト";
	static final int WIDTH = 800;
	static final int HEIGHT= 600;

	public static void main(String argv[]) {
		new SphereWindow();
	}

	public SphereWindow() {
		GameBox.sysinit(WIDTH, HEIGHT);
		GameBox.addInitialize(new AddHook() {
			public void execute() {
				SuperTask.initialize();
				DebugFont.create();
				//GameBox.wait(false);
			}
		});
		GameBox.addPreTask(new AddHook() {
			InputAction exitAction = GameBox.inputman().createTriggerInput(
					KeyEvent.VK_ESCAPE);;

			public void execute() {
				if (exitAction.isPressed()) {
					System.out.println("ESC KEY PRESSED!!");
					System.exit(0);
				}
				DebugFont.print(1, 1, "FPS:" + GameBox.fps());
			}
		});

		Frame frame = GameBox.buildFrame(TITLE, WIDTH, HEIGHT);
		frame.add(GameBox.canvas());
		frame.setVisible(true);

		GameBox.startAnime();
    }
}