package game;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.input.RepeatInputAction;
import com.googlecode.ochagl.math.Vec3;

public class PlayerTask extends GameObjectTask {

    public static final int STATE_ENTRANCE = 0;

    public static final int STATE_GAME = 1;

    public static final int STATE_BURST = 2;

    private static final float SPEED = 3.0f;

    private Vec3 velo_ = null;

    private InputAction actionUp_ = inputman().createNormalInput(KeyEvent.VK_UP);

    private InputAction actionDown_ = inputman().createNormalInput(KeyEvent.VK_DOWN);

    private InputAction actionLeft_ = inputman().createNormalInput(KeyEvent.VK_LEFT);

    private InputAction actionRight_ = inputman().createNormalInput(KeyEvent.VK_RIGHT);

    private RepeatInputAction actionShoot_ = inputman().createRepeatInput(KeyEvent.VK_SPACE);

    private int state_ = 0;

    private int count_ = 0;

    private int step_ = 0;

    private int animeCount_;

    public PlayerTask(String name, int priority) {

        super(name, priority, SuperTask.ATTR_PLAYER);

        // Texture texture = drawman().loadTexture("btl_1.png");
        Texture texture = null;
        createSprite(32, 32, texture);
        velo_ = new Vec3(0, 0, 0);

        actionShoot_.setRepeatIntervalTime(4); // 弾発射間隔
        actionShoot_.setRepeatWaitTime(5); // 最初の弾発射までのフレーム数

        reset();
    }

    public void reset() {
        show(true);
        isHit_ = false;
        ignoreHit(true);
        changeState(STATE_ENTRANCE);
        pos().x = 0;
        pos().y = -GameBox.height() / 2 + 64;
        velo_.x = 0;
        velo_.y = 0;
        animeCount_ = 0;
    }

    protected void checkInput() {
        if (actionUp_.isPressed()) {
            velo_.y += SPEED;
        } else if (actionDown_.isPressed()) {
            velo_.y -= SPEED;
        }

        if (actionLeft_.isPressed()) {
            velo_.x -= SPEED;
            animeCount_--;
        } else if (actionRight_.isPressed()) {
            velo_.x += SPEED;
            animeCount_++;
        } else {
            if (animeCount_ < 0) {
                animeCount_++;
            } else if (animeCount_ > 0) {
                animeCount_--;
            }
        }

        if (actionShoot_.isPressed()) {
            shoot();
        }

    }

    public void shoot() {
        taskman().addTask(new ShotTask(pos().x, pos().y - 8));
        // System.out.println("SHOT");
    }

    public void changeState(int state) {
        state_ = state;
    }

    public int getState() {
        return state_;
    }

    protected void checkArea() {
        if (pos().x < dispArea.getX1()) {
            pos().x = dispArea.getX1();
        }
        if (pos().x > dispArea.getX2()) {
            pos().x = dispArea.getX2();
        }
        float h = dispArea.getY1() - 32;
        if (pos().y > h) {
            pos().y = h;
        }
        h = dispArea.getY2() + 32;
        if (pos().y < h) {
            pos().y = h;
        }
    }

    private void entrance() {
        count_ = 0;
        step_ = 0;
        changeState(STATE_GAME);
    }

    private void game() {

        switch (step_) {
        case 0:
            if (count_ % 2 == 0)
                show(false);
            else
                show(true);

            if (count_++ > 60 * 2) {
                show(true);
                ignoreHit(false);
                step_++;
            }
            break;

        case 1:
        }

        velo_.set(0, 0, 0);
        checkInput();
        pos().add(velo_);
        checkArea();
        if (isHit()) {
            taskman().addTask(new BurstTask(pos()));
            show(false);
            count_ = 0;
            ignoreHit(true);
            changeState(STATE_BURST);
        }
    }

    private void burst() {
        if (count_++ > 60 * 2)
            reset();
    }

    protected void doGameObjectTask() {
        switch (state_) {
        case STATE_ENTRANCE:
            entrance();
            break;
        case STATE_GAME:
            game();
            break;
        case STATE_BURST:
            burst();
            break;
        }
    }

    // これをオーバライドしてあたり計算
    protected void calcHit(GameObjectTask target) {
        float dist = new Vec3().sub(pos(), target.pos()).len();
        if (dist < hitRadius_ + target.hitRadius_) {
            isHit_ = true;
            System.out.println("hit");
        }
    }

}
