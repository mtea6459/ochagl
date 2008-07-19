package game;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.input.InputAction;

public class TitleTask extends SuperTask {

    private InputAction actionStart_ = inputman().createNormalInput(KeyEvent.VK_SPACE);

    private Object2d spriteBg_;

    private PressspaceTask pressspace_;

    private int step_ = 0;
    private int timer_;

    public TitleTask(String name, int priority) {
        super(name, priority, SuperTask.ATTR_OTHER);

        Texture texture = GameBox.loadTexture("title.png");
        spriteBg_ = ResourceFactory.createObject2d("title");
        spriteBg_.setTexture(texture);
        spriteBg_.setLeftopPosition(0, 0);
        spriteBg_.show(true);
        view2d.addRenderObject(LAYER_BG, spriteBg_);

        pressspace_ = new PressspaceTask(name, priority);
        taskman().addTask(pressspace_);

        timer_ = 60;
    }

    public void kill() {
        super.kill();
        spriteBg_.kill();
        pressspace_.kill();
    }

    protected void doGameTask() {
        switch (step_) {
        case 0:
            if (--timer_ < 0) {
                step_++;
            }
            break;

        case 1:
            if (fadeTask.getMode() == FadeTask.DONE) {
                fadeTask.fadeIn(30);
                step_++;
            }
            break;

        case 2:
            if (actionStart_.isPressed()) {
                fadeTask.fadeOut(30);
                step_++;
            }
            break;

        case 3:
            if (fadeTask.getMode() == FadeTask.DONE) {
                fadeTask.fadeIn(60);
                setupPlayGame();
                kill();
            }
            break;
        }
    }

    private class PressspaceTask extends SuperTask {
        private Object2d spritePressspace_ = null;

        public PressspaceTask(String name, int priority) {
            super(name, priority, SuperTask.ATTR_OTHER);
            Texture texture = GameBox.loadTexture("pressspace.png");
            spritePressspace_ = ResourceFactory.createObject2d("pressspace");
            spritePressspace_.setTexture(texture);
            spritePressspace_.setWidth(300);
            spritePressspace_.setHeight(32);
            spritePressspace_.setPosition(GameBox.width() / 2, 400);
            view2d.addRenderObject(LAYER_2DOBJECT, spritePressspace_);
        }

        public void kill() {
            super.kill();
            spritePressspace_.kill();
        }

        protected void doGameTask() {
            spritePressspace_.show(true);
            if (GameBox.loopCount() % 60 < 30)
                spritePressspace_.show(false);
        }
    }
}

