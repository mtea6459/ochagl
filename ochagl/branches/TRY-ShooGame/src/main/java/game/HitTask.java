package game;

import java.util.Iterator;

public class HitTask extends SuperTask {

	public HitTask(String name, int priority) {
		super(name, priority, SuperTask.ATTR_LOGIC);
	}

	protected void doGameTask() {
		Iterator it = GameObjectTask.gameObjects.iterator();
		while (it.hasNext()) {
			GameObjectTask t = (GameObjectTask) it.next();
            if (t.isIgnoreHit())
                continue;
            if (t.isHit())
                continue;
            switch (t.getAttr()) {
			case SuperTask.ATTR_PLAYER:
				t.checkHit(SuperTask.ATTR_BULLET);
				t.checkHit(SuperTask.ATTR_ENEMY);
				break;
            case SuperTask.ATTR_ENEMY:
                t.checkHit(SuperTask.ATTR_PLAYER_BULLET);
                break;
			}
		}
	}
}