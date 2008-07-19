package game;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.InterpolateLinear;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;

/**
 * 自機の単発弾 TODO 進行方向の角度と見た目の角度は分離させる。 TODO 相対的な角度指定 TODO 自機に向かっていく弾
 * 
 * @author ocha
 */
public class TamaTask extends GameObjectTask {
    public static int MODE_NORMAL = 0;
    public static int MODE_HOMING = 1;

    private static final float SPEED = 8.0f;

    private static final float ACCEL = 0.2f;

    private float rad_ = 0; // 姿勢

    private float dir_ = 0;
    
    private float dist_ = 0;

    private float speed_ = 0;

    private float accel_ = 0;
    
    private int mode_ = MODE_NORMAL;

    private Vec3 velo_ = new Vec3();

    private InterpolateLinear a = new InterpolateLinear();

    protected PlayerTask player_ = null;

    protected Vec3 playerVec = new Vec3();

    private boolean isPast_;

    public TamaTask(float x, float y) {
        super("TamaTest", PRIO_BULLET, SuperTask.ATTR_BULLET);

        player_ = (PlayerTask) taskman().getTask(TASK_PLAYER);

        createSprite(32, 16, null);
        reset();
        pos().x = x;
        pos().y = y;
    }

    public void reset() {
        dist_ = 0;
        setDirection(0.0f);
        speed_ = SPEED;
        accel_ = ACCEL;
        velo_.set(0, 0, 0);
        a.setStartTime(GameBox.loopCount());
        isPast_ = false;
    }

    public void setDirection(float degree) {
        changeDirection(degree, 0);
    }

    public void setMode(int mode) {
        mode_ = mode;
    }
    
    public void setSpeed(float speed) {
        speed_ = speed;
    }

    public void changeDirection(float degree, int duration) {
        a.clear();
        a.setValue(0.0f, dir_);
        a.setValue(1.0f, Rad.toRad(degree));
        a.setDuration(duration);
        a.setStartTime(GameBox.loopCount());
    }

    public void homing() {
        final float max = 30;
        float dir = (float) Math.atan2(playerVec.y, playerVec.x);
        float diff = Rad.diff(dir_, dir);
        if (diff < 0) {
            if (diff < -max) diff = -max;
        } else {
            if (diff >  max) diff =  max;
        }
        dir_ += diff/20;
    }

    protected void doGameObjectTask() {
        if (isHit()) {
            kill();
            return;
        }

        // 自機へのベクトル求む
        playerVec.sub(player_.pos(), pos());
        dist_ = playerVec.normalize();


        if (mode_ == MODE_NORMAL) {
            if (a.getFraction() < 1.0f) {
                dir_ = a.getValue(GameBox.loopCount());
            }
        } else {
             if (dist_ < 60)
                 isPast_ = true;

             if (!isPast_)
                 homing();
        }

        velo_.x = Rad.cos(dir_) * speed_;
        velo_.y = Rad.sin(dir_) * speed_;
        pos().add(velo_);

        rad_ = dir_;
        setRadian(rad_);
    }

}