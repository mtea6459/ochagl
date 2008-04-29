package rigidbody;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.task.GenericLightTask;
import com.googlecode.ochagl.math.Vec3;

public class LightTask extends GenericLightTask {

    SuperTask game_;
    
    public LightTask(int priority, String name) {
        super(name, priority);
        getLight().setPosition(10, 50, 10);
        getLight().lookat(new Vec3(0,5,0));
        createAxis().addTo(getLight());

        getLight().show(true);
        getLight().setDiffuse(0.8f, 0.8f, 0.8f);
        getLight().setAmbinet(0.8f, 0.8f, 0.8f);
        getLight().setSpecular(1.0f, 1.0f, 1.0f);
    }

    private Object3d createAxis() {
        Object3d obj = Primitive3dFactory.createAxis(4);
        obj.show(true);
        return obj;
    }

    public void execute() {
    }
}
