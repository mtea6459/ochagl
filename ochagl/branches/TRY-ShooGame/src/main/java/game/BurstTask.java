package game;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.ochagl.core.InterpolateStep;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.math.Vec3;

/**
 * 爆発エフェクト 
 * @author ocha
 */
public class BurstTask extends GameObjectTask {
    
    private long count_ = 0;
    InterpolateStep a = new InterpolateStep();
    List animeuv = new ArrayList();

    public BurstTask(Vec3 org) {
        super("BURST", PRIO_BULLET, SuperTask.ATTR_OTHER);

        for (int i = 0; i < 16; i++) {
            a.setValue(1.0f / 16 * i, i);
        }
        a.setLoop(true);
        a.setDuration(60);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                animeuv.add(new int[]{96*j,96*i,96,96});
            }
        }
        a.setStartTime(0);

        //Texture texture = drawman().loadTexture("burst.png"); 
        Texture texture = null;
        createSprite(48, 48, texture);
        pos().x = org.x;
        pos().y = org.y;

    }

    protected void doGameObjectTask() {

        if (count_ > 30) {
            kill();
        	return;
        }

        int index = (int) a.getValue(count_++);
        int[] uv = (int[]) animeuv.get(index);
//        sprite_.setUv(uv[0], uv[1], uv[2], uv[3]);
    }
    
}
