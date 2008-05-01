package rigidbody;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.Texture;


public abstract class SuperTask extends AbstractTask {

	public static final String TASK_PLAYER = "PLAYER";

	public static final String TASK_BG = "BG";

	public static final String TASK_LIGHT = "LIGHT";

	public static final String TASK_VIEW = "VIEW";

	public static final String TASK_COLLISION = "COLLISION";

	public static final String TASK_OBJECT = "OBJECT";

	static final String TEX_EARTH = "earth.png";

	public SuperTask() {
		super();
	}

	public static void initialize() {
		Texture texEarth = GameBox.loadTexture(SuperTask.TEX_EARTH);

		LightTask lightTask = new LightTask(0, TASK_LIGHT);
		CameraManTask viewTask = new CameraManTask(1, TASK_VIEW);
		StageTask bgTask = new StageTask(2, TASK_BG);
		PlayerTask playerTask = new PlayerTask(3, TASK_PLAYER, texEarth);
		CollisionTask collisionTask = new CollisionTask(4, TASK_COLLISION);
		ObjectsTask objectTask = new ObjectsTask(5, TASK_OBJECT, texEarth);

		GameBox.taskman().addTask(viewTask);
		GameBox.taskman().addTask(lightTask);
		GameBox.taskman().addTask(playerTask);
		GameBox.taskman().addTask(bgTask);
		GameBox.taskman().addTask(collisionTask);
		GameBox.taskman().addTask(objectTask);

		bgTask.setupCollisionObjects(collisionTask);
		playerTask.setupCollisionObjects(collisionTask);
		objectTask.setupCollisionObjects(collisionTask);

		GameBox.world().add(bgTask.getObject());
		GameBox.world().add(playerTask.getObject());
		GameBox.world().add(lightTask.getLight());
	}
}
