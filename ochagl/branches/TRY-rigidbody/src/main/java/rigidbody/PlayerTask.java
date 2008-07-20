package rigidbody;                      

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;


public class PlayerTask extends SphereTask {

    private static final float RADIUS = 4.0f;
    private static final float MASS = 1.5f;
    private static final float TORQUE = 10.0f;
    private static final float FORCE = 50.0f;

    private InputAction inGo_ = inputman().createNormalInput(KeyEvent.VK_UP);
    private InputAction inBk_ = inputman().createNormalInput(KeyEvent.VK_DOWN);
    private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_LEFT);
    private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);
    private InputAction inJump_ = inputman().createNormalInput(KeyEvent.VK_SPACE);
    private InputAction inReset_ = inputman().createNormalInput(KeyEvent.VK_R);
    private InputAction inInfo_ = inputman().createTriggerInput(KeyEvent.VK_I);

    private float omega_ = 0;
    private float theta_ = 0;
    private Object3d line_ = null;
    
    private SuperTask game_ = null;
    private long count_ = 0;
    private Vec3 tmpVec1 = new Vec3();
    private Vec3 tmpVec2 = new Vec3();
    private boolean isInfo_ = false;

    public PlayerTask(int priority, String name, Texture texture) {
        super(priority, name, texture);
        // 向き(XZ平面上)と位置を保持する
        line_ = createLine();
        
        GameBox.world().add(line_);
        GameBox.world().add(obj_);
        reset();
    }

    private Object3d createLine() {
        Object3d obj = Primitive3dFactory.createLine(RADIUS*1.5f);
        obj.show(true);
        return obj;
    }
    
    public void reset()
    {
        setInitPos(0, 10, 4);
        //setInitPos(0, 7, 8);//辺をかすめる位置
        //setInitPos(7.6f, 7, 7.6f);// 頂点をかすめる位置
        //setInitPos(7.6f, 7, -7.6f);// 頂点をかすめる位置
        super.reset();
    }

    private void checkInput()
    {
        if (inLt_.isPressed()) {
            omega_ += Rad.toRad(0.2f);
        }
        if (inRt_.isPressed()) {
            omega_ += -Rad.toRad(0.2f);
        }
        
        if (inGo_.isPressed() || inBk_.isPressed()) {
            Vec3 force = tmpVec1;
            Vec3 r = tmpVec2;
            line_.getVectorZ(force);
            force.scale(TORQUE);
            line_.getVectorY(r);
            r.scale(((SphereRb) rigidBody_).getRadius());
            r.add(rigidBody_.getPosition());
            if (inGo_.isPressed()) {
                rigidBody_.addTorque(r, force);
            }
            if (inBk_.isPressed()) {
                rigidBody_.addTorque(r, force.scale(-1));
            }
        }

        if (inJump_.isPressed()) {
            Vec3 force = tmpVec1;
            line_.getVectorY(force);
            force.scale(FORCE);
            rigidBody_.addForce(rigidBody_.getPosition(), force);
        }

        if (inReset_.isPressed()) {
            ObjectsTask t = (ObjectsTask) TaskManager.getInstance().getTask(SuperTask.TASK_OBJECT);
            t.reset();
            reset();
        }
        if (inInfo_.isPressed()) {
            isInfo_ = !isInfo_;
        }
    }

    public void execute()
    {
        update();
        checkInput();
        if (isInfo_ ) {
            rigidBody_.dispInfo();
        }
        omega_ *= 0.9f;
        theta_ += omega_;
        line_.getLocalMatrix().rotY(theta_);
        line_.setPosition(obj_.getPosition());
        count_++;
    }
}