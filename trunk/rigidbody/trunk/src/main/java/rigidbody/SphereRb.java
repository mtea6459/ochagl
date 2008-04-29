package rigidbody;

import com.googlecode.ochagl.graphics.Object3d;


/**
 * 球の剛体クラス
 */
public class SphereRb extends RigidBody {

    /**
     * 半径.
     */
    protected float radius_ = 0.0f;

    /**
     * コンストラクタ.<br>
     * @param radius 半径
     * @param mass 質量
     * @param obj メッシュオブジェクト
     * @param game Gameインスタンス
     */
    public SphereRb(float radius, float mass, Object3d obj) {
        super(mass, obj);
        radius_ = radius;
        initInertia();
    }

    /**
     * 剛体球の半径を取得する.<br>
     * @return 半径
     */
    public float getRadius() {
        return radius_;
    }

    /**
     * 慣性テンソルを初期化する.<br>
     */
    public void initInertia() {
        float val = mass_*2/5*radius_*radius_;
        I0_.m00 = val; I0_.m01 =   0; I0_.m02 =   0;
        I0_.m10 =   0; I0_.m11 = val; I0_.m12 =   0;
        I0_.m20 =   0; I0_.m21 =   0; I0_.m22 = val;
        I0inv_.invert(I0_);
    }
}
