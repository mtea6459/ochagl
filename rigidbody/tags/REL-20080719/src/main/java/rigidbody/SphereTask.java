package rigidbody;                      

import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.task.Generic3dTask;
import com.googlecode.ochagl.math.Vec3;

public class SphereTask extends Generic3dTask {

    protected static final float RADIUS = 2.0f;
    protected static final float MASS = 2.0f;
    protected static final float TORQUE = 2.0f;

    protected SphereRb rigidBody_ = null;
    protected Vec3 initPos_ = new Vec3();

    public SphereTask(int priority, String name, Texture texture) {
        super(name, priority);
        obj_ = createSphere(texture);
        rigidBody_ = new SphereRb(RADIUS, MASS, obj_);
        reset();
    }

    protected Object3d createSphere(Texture texture)
    {
        Object3d obj = Primitive3dFactory.createSphere(RADIUS, 8, texture);
        //obj.setShadeMode(ShadeMode.WIRE);
        obj.show(true);
        return obj;
    }

    public void setupCollisionObjects(CollisionTask collsionTask)
    {
        collsionTask.addSphereRb(rigidBody_);
    }
    
    public void setInitPos(float x, float y, float z)
    {
        initPos_.set(x, y, z);
    }

    public void reset()
    {
        rigidBody_.reset(initPos_.x, initPos_.y, initPos_.z);
    }

    public void update()
    {
        float dt = 1.0f/60.0f;
        rigidBody_.update(dt);
        rigidBody_.resetCalc();
        rigidBody_.calcForce(dt);
    }

    public void execute()
    {
        update();
    }
}