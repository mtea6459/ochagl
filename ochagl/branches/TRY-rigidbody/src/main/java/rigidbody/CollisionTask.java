package rigidbody;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.SortedList;
import com.googlecode.ochagl.graphics.Mesh;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.Vertex;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Sphere;
import com.googlecode.ochagl.math.Vec3;


public class CollisionTask extends AbstractTask {

    private SortedList bgObjList_ = new SortedList();
    private List<SphereRb> sphereRbList_ = new ArrayList<SphereRb>();
    private static final boolean DEBUG = false;
    private Object3d point1_ = null;
    private Object3d cylinder_ = null;

    // 以下コリジョン用
    private SphereToPoly3Collider collider_ = new SphereToPoly3Collider();

    private Sphere sphere_ = new Sphere();

    private Vec3 vecs_[] = new Vec3[3];

    private Vec3 tps_[] = new Vec3[128]; // 接触位置(touch point)

    private Mat4 invert_ = new Mat4();

    private Vec3 tmpVec1 = new Vec3();

    private Vec3 tmpVec2 = new Vec3();

    private Vec3 tmpVec3 = new Vec3();

    private Vec3 tmpVec4 = new Vec3();

    public CollisionTask(int priority, String name) {
        super(name, priority);
        vecs_[0] = new Vec3();
        vecs_[1] = new Vec3();
        vecs_[2] = new Vec3();

        for (int i = 0; i < tps_.length; i++) {
            tps_[i] = new Vec3();
        }

        if (DEBUG) {
            point1_ = Primitive3dFactory.createCrossLine(0.2f);
            point1_.addTo(GameBox.world());
            point1_.show(true);
            
            cylinder_ = Primitive3dFactory.createCylinder(1.0f, 10.0f, 16, null);
            cylinder_ .addTo(GameBox.world());
            cylinder_ .show(true);
            Object3d axis = Primitive3dFactory.createAxis(15.0f);
            axis.addTo(cylinder_);
            axis.show(true);
        }
    }

    public void addBgObject(Object3d obj) {
        bgObjList_.add(0, obj);
    }

    public void addSphereRb(SphereRb rb) {
        sphereRbList_.add(0, rb);
    }

    public void collision(float dtime, SphereRb rigidBody)
    {
        CollisionInfo ci = new CollisionInfo();
        Vec3 x1 = new Vec3();
        Vec3 x2 = new Vec3();
        Vec3 x3 = new Vec3();
        x1.set(rigidBody.getLastPosition());
        x2.set(rigidBody.getPosition());
        x3.set(x1);
        Iterator it0 = bgObjList_.iterator();
        while (it0.hasNext()) {
            Object3d obj = (Object3d) it0.next();

            if (DEBUG) {
                cylinder_.addTo(obj);
            }

            // 位置をローカル座標系に変換しておく
            invert_.invert(obj.getWorldMatrix());
            invert_.transform(x2, sphere_.pos);
            invert_.transform(x1);

            sphere_.r = rigidBody.getRadius();
            int res = 0;
            int crossCount = 0;
            ci.sp = sphere_;
            ci.lastPos = x1;
            ci.vs = vecs_;
            ci.outs[0] = tmpVec2;
            ci.outs[1] = tmpVec3;
            Iterator it = obj.getMeshIterator();
            while (it.hasNext()) {
                Mesh mesh = (Mesh) it.next();
                if (mesh == null)
                    continue;
                Vertex vertex = mesh.getVertex();
                int[] is = vertex.getGT3I();
                Vec3[] vs = vertex.getVertexTop();
                for (int i = 0; i < is.length; i += 3) {
                    int i0 = is[i + 0];
                    int i1 = is[i + 1];
                    int i2 = is[i + 2];
                    vecs_[0] = vs[i0];
                    vecs_[1] = vs[i1];
                    vecs_[2] = vs[i2];
                    res = collider_.checkCollision(ci);
                    //System.out.println("res:" + res);
                    if (res > 0) {
                        obj.getWorldMatrix().transform(ci.outs[0], tps_[crossCount++]);
                        if (DEBUG) {
                            if (res == 2) {
                                point1_.setPosition(tps_[0]);
                                cylinder_.setLocalMatrix(ci.cm);
                                cylinder_.setPosition(ci.cm.m03, ci.cm.m13, ci.cm.m23);
                            } else if (res == 3) {
                                point1_.setPosition(tps_[0]);
                            }
                        }
                    }
                }
            }
            if (crossCount > 0) {
                Vec3 ap = new Vec3();// 作用点
                Vec3 n = new Vec3(); // 接触法線
                float depth = 0;      //めり込み量
                float max = 0;
                float radius = rigidBody.getRadius();
                for (int i = 0; i < crossCount; i++) {

                    n.sub(tps_[i], rigidBody.getPosition());
                    depth = n.normalize();

                    // 複数交差点がある場合，修正後の座標がより遠い方を採用する
                    if (depth > max) {
                        max = depth;
                        ap.scale(-radius, n);
                        ap.add(rigidBody.getPosition());
                        rigidBody.collisionResponse(dtime,      // DT
                                                    ap,         // 作用点     
                                                    n,          // 衝突法線
                                                    depth,      // めり込み量
                                                    crossCount);// 接触数
                    }
                }
            }
        }
    }

    private void sphereToSphere(float dt) {
        Vec3 dis = new Vec3();
        int size = sphereRbList_.size();
        for (int i = 0, max = size-1; i < max; i++) {
        	SphereRb a = sphereRbList_.get(i);

            for (int j = i+1; j < size; j++) {
            	SphereRb b = sphereRbList_.get(j);

            	Vec3 ax = a.getPosition();
                Vec3 bx = b.getPosition();
                dis.sub(a.getPosition(), b.getPosition());
                float d = dis.len2();
                float ra = a.getRadius();
                float rb = b.getRadius();
                if (d < ra * ra + rb * rb) {
                    if (d != 0.0f) {
                        dis.normalize();
                        dis.scale(-1);
                        float l = (float) Math.sqrt(d);
                        float f = (l - (ra + rb)) * 2 / dt;
                        if (true)
                        {
                            //起動時に、力がつりあってしまうので、うその挙動を入れてみる。
                            Vec3 tmp0 = new Vec3();
                            Vec3 tmp1 = new Vec3();
                            tmp0.sub(ax, bx).normalize();
                            tmp0.x += 0.02f; // <= これ。インチキ
                            tmp1.add(bx, tmp0.scale(dis.len()/2.0f));
                            ax = tmp1;
                            bx = tmp1;
                        }
                        a.addForce(ax, new Vec3().scale( f, dis));
                        b.addForce(bx, new Vec3().scale(-f, dis));
                    }
                }
            }
        }
    }
    
    public void execute() {
 
        float dt = 1.0f / 60.0f;

        // 背景との衝突
        Iterator it = sphereRbList_.iterator();
        while (it.hasNext()) {
            SphereRb rb = (SphereRb) it.next();
            collision(dt, rb);
        }


        // 剛体同士の衝突
        sphereToSphere(dt);
    }
}
