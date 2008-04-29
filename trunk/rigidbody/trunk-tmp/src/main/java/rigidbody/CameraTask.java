package rigidbody;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.task.GenericCameraTask;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;


// プレイヤーを見続けるカメラ
public class CameraTask extends GenericCameraTask {

    private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_E);

    private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_Q);

    private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_A);

    private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_D);

    private InputAction inGo_ = inputman().createNormalInput(KeyEvent.VK_W);

    private InputAction inBk_ = inputman().createNormalInput(KeyEvent.VK_S);

	private float theta_ = Rad.toRad(0);

	private float omega_ = Rad.toRad(1);

	private float radius_ = 30;

	private Vec3 tgt_ = new Vec3();

	private Vec3 lat_ = new Vec3();
    
	public CameraTask(int prio) {
		super("Camera", prio, GameBox.world(), GameBox.width(), GameBox.height());
		getCamera().setPosition(0, 10, 40);
    }

	protected void checkInput() {
		if (inGo_.isPressed()) {
			radius_ -= 0.5f;
		}
		if (inBk_.isPressed()) {
			radius_ += 0.5f;
		}
		if (inUp_.isPressed()) {
			getCamera().moveY(0.5f);
		}
		if (inDn_.isPressed()) {
			getCamera().moveY(-0.5f);
		}
		if (inLt_.isPressed()) {
			theta_ -= omega_;
		}
		if (inRt_.isPressed()) {
			theta_ += omega_;
		}
	}

    public void execute() {
        checkInput();

		Vec3 v = null;
		if (true) {
			SphereTask playerTask = (SphereTask) TaskManager.getInstance()
					.getTask(SuperTask.TASK_PLAYER);
			v = playerTask.getObject().getPosition();
		} else {
			v = new Vec3();
		}
		tgt_.z = v.z + (float) Math.cos(theta_) * radius_;
		tgt_.x = v.x + (float) Math.sin(theta_) * radius_;

		Vec3 p = getCamera().getPosition();
		float ratio = 0.2f;
		p.x += (tgt_.x - p.x) * ratio;
        p.y += (tgt_.y+10 - p.y) * ratio;
		p.z += (tgt_.z - p.z) * ratio;
		lat_.x += (v.x - lat_.x) * ratio;
		lat_.y += (v.y - lat_.y) * ratio;
        lat_.z += (v.z - lat_.z) * ratio;
		getCamera().lookat(lat_);
	}
}
