package rigidbody;

import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Sphere;
import com.googlecode.ochagl.math.Vec3;

public class CollisionInfo {
    public Sphere sp = null;
    public Vec3 lastPos = null;
    public Vec3[] vs = null;
    public Vec3[] outs = new Vec3[2];
    public Mat4 cm = new Mat4();
}
