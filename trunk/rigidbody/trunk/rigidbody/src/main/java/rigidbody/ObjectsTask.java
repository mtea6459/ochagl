package rigidbody;

import java.util.Iterator;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.SortedList;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.Texture;


// オブジェクトたちを管理
public class ObjectsTask extends AbstractTask {
 
    private static final float RADIUS = 1.0f;
    private static final float MASS = 1.0f;
    private static final float TORQUE = 2.0f;
    private static final int OBJ_MAX = 128;

    private SortedList list_ = new SortedList();

    public ObjectsTask(int priority, String name, Texture texture) {
        super(name, priority);
        for (int i = 0; i < OBJ_MAX; i++) {
            SphereTask t = new SphereTask(10 + i, "SPHERE", texture);
            //t.setInitPos(0, 7 + 2 * i, 0);
            t.setInitPos(0, 7 + (RADIUS*2+2) * i, 0);
            t.reset();
            list_.add(i, t);
        }

        TaskManager tman = TaskManager.getInstance();
        Iterator it = list_.iterator();
        while (it.hasNext()) {
            SphereTask t = (SphereTask) it.next();
            tman.addTask(t);
            GameBox.world().add(t.getObject());
        }
        reset();
    }

    public void reset() {
        Iterator it = list_.iterator();
        while (it.hasNext()) {
            SphereTask t = (SphereTask) it.next();
            t.reset();
        }
    }

    public void setupCollisionObjects(CollisionTask collsionTask)
    {
        TaskManager tman = TaskManager.getInstance();
        Iterator it = list_.iterator();
        while (it.hasNext()) {
            SphereTask t = (SphereTask) it.next();
            collsionTask.addSphereRb(t.rigidBody_);
        }
    }
    
    public void execute()
    {
        if (false) {
            TaskManager tman = TaskManager.getInstance();
            Iterator it = list_.iterator();
            SphereTask t = (SphereTask) it.next();
            t.rigidBody_.dispInfo();
        }
    }
}