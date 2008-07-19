package game;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Vec3;


/**
 * 自機、敵および弾などのゲームの中心となるタスクのスーパークラス。 
 * 動き回ってあたり判定のあるもの。
 * @author ocha
 */
public abstract class GameObjectTask extends SuperTask {

    protected Object3d sprite_ = null;
    protected boolean isHit_;
    protected boolean ignoreHit_; // true: ヒットチェックしない
    protected int width_;
    protected int height_;
    public static List<GameObjectTask> gameObjects = new LinkedList<GameObjectTask>();
    protected int hitRadius_;

    public GameObjectTask(String name, int priority, int attr) {
        super(name, priority, attr);

        gameObjects.add(this);

        isHit_ = false;
        ignoreHit_ = false;

        sprite_ = null;
    }

    public void show(boolean b) {
        sprite_.show(b);
    }
    
    public boolean isHit() {
        return isHit_;
    }
    
    public void ignoreHit(boolean b) {
        ignoreHit_ = b;
    }
    
    public boolean isIgnoreHit() {
    	return ignoreHit_;
    }
    
    public void checkHit(int attr) {
        Iterator it = gameObjects.iterator();
        while (it.hasNext()) {
            GameObjectTask t = (GameObjectTask) it.next();
            if (t == this || t.getAttr() != attr)
            	continue;
            calcHit(t);
        }
    }

    // これをオーバライドしてあたり計算
    protected void calcHit(GameObjectTask target) {
        isHit_ = false;
    }
    
    public final Vec3 pos() {
        return sprite_.getPosition();
    }
    
    protected void createSprite(int width, int height, Texture texture) {
        sprite_ = Primitive3dFactory.createSprite3d(width, height, texture);
        sprite_.reset();
        sprite_.getPosition().set(0, 0, 0);
        sprite_.show(true);
        width_ = width;
        height_ = height;
        GameBox.world().add(sprite_);
        hitRadius_ = width_ / 3; 
    }

    public final boolean isInArea() {
        return logicArea.isIn(pos().x, pos().y);
    }

    public void kill() {
        super.kill();
        if (sprite_ != null)
            sprite_.kill();
        gameObjects.remove(this);
    }

    public void setRadian(float rad) {
        Mat4 rmat = new Mat4();
        rmat.rotZ(rad);
        sprite_.setLocalMatrix(rmat);
    }

    protected void doGameTask() {
        
        if (sprite_ == null)
            return;
        
        if (getGameState() == GAMESTATE_PAUSE)
            return;

        if (!isInArea()) {
            kill();
            return;
        }

        doGameObjectTask();
        //hitRect_.updatePos(pos);
    }

    abstract protected void doGameObjectTask();
}
