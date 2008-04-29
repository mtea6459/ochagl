package rigidbody;

import java.util.Iterator;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.SortedList;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.task.Generic3dTask;
import com.googlecode.ochagl.math.Rad;


public class StageTask extends Generic3dTask {

    private long count_ = 0;

    private SortedList list_ = new SortedList();

    public StageTask(int priority, String name) {
        super(name, priority);
        Texture texture = GameBox.loadTexture(SuperTask.TEX_EARTH);
        obj_ = ResourceFactory.createObject3d("BG");
        //obj_.add(createSphere(texture));
        obj_.add(createCube(texture));
        obj_.add(createFloor(texture));
        //obj_.add(createCircle());

    }

    private Object3d createSphere(Texture texture)
    {
        Object3d sphere = Primitive3dFactory.createSphere(5.0f, 16, texture);
        sphere.setName("");
        sphere.show(true);
        sphere.getPosition().set(10, 30, 0);
        list_.add(0, sphere);
        return sphere;
    }

    private Object3d createCube(Texture texture)
    {
        Object3d cube = Primitive3dFactory.createCube(50.0f, 10.0f, 50.0f);
        cube.show(true);
        cube.addTo(obj_);
        cube.getPosition().set(0, 0, -30);
        //cube.getPosition().set(0, 0, 0);
        cube.rotateX(Rad.toRad(15));

        Object3d axis = Primitive3dFactory.createAxis(20.0f);
        axis.addTo(cube);
        axis.show(true);

        list_.add(0, cube);
        return cube;
    }

    private Object3d createFloor(Texture texture)
    {
        Object3d obj = Primitive3dFactory.createFloor(150, 150, texture);
        //Object3d obj = Primitive3dFactory.createFloor(15, 15, null);
        //Object3d obj = Primitive3dFactory.createPoly3(15, 15, null);
        obj.show(true);
        obj.getPosition().set(0, 0, 0);
        //obj.setShadeMode(ShadeMode.WIRE);

        list_.add(0, obj);
        return obj;
    }

    private Object3d createCircle()
    {
        //Object3d obj = ShapeFactory.createCircle(10.0f, 1.0f, 1.0f, 1.0f);
        Object3d obj = Primitive3dFactory.createLineSphere(10.0f, 1.0f, 1.0f, 1.0f);
        obj.show(true);
        obj.getPosition().set(10, 30, 0);

        return obj;
    }

    public void setupCollisionObjects(CollisionTask collsionTask)
    {
        Iterator it = list_.iterator();
        while (it.hasNext()) {
            Object3d obj = (Object3d) it.next();
            collsionTask.addBgObject(obj);
        }
    }
    
    public void execute()
    {
        count_++;
    }
}