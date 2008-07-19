package game;

import com.googlecode.ochagl.graphics.Texture;

/**
 * Ž©‹@‚Ì’P”­’e
 * 
 * @author ocha
 */
public class ShotTask extends GameObjectTask {

    private static final float SPEED = 8.0f;

    public ShotTask(float x, float y) {
        super("PLAYER_BULLET", PRIO_BULLET, SuperTask.ATTR_PLAYER_BULLET);
        //Texture texture = drawman().loadTexture("shot1.png");
        Texture texture = null;
        createSprite(16, 16, texture);
        pos().x = x;
        pos().y = y;
    }

    protected void doGameObjectTask() {
        pos().y += SPEED;
    }

}