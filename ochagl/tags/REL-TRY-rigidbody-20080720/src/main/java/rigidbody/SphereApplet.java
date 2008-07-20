package rigidbody;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.googlecode.ochagl.app.AddHook;
import com.googlecode.ochagl.app.GameBox;

public class SphereApplet extends Applet {

	private static final long serialVersionUID = 1L;

	int width = 800;

	int height = 600;

	public void init() {
		GameBox.sysinit(width, height);
		GameBox.addInitialize(new AddHook() {
			public void execute() {
				SuperTask.initialize();
			}
		});

		setLayout(new BorderLayout());
		add(GameBox.canvas(), BorderLayout.CENTER);

		System.out.println("o init");
	}

	public void destroy() {
		GameBox.sysexit();
		System.out.println("o destroy");
	}

	public void start() {
		GameBox.startAnime();
		System.out.println("o start");
	}

	public void stop() {
		GameBox.stopAnime();
		System.out.println("o stop");
	}
}