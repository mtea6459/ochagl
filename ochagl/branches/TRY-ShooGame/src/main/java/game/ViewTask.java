package game;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.DrawableManager;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.Primitive3dFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.task.GenericCameraTask;

public class ViewTask extends GenericCameraTask {

    private Object3d plane_ = null;

    public ViewTask(String name) {
        super(name, 0, GameBox.world(), GameBox.width(), GameBox.height());

        setupScreenView(GameBox.width(), 45.0f);

        drawman().addView(1, getView());

        Texture texture = GameBox.loadTexture("earth.png");
        plane_ = Primitive3dFactory.createSprite3d(800, 600, texture);
        plane_.reset();
        plane_.getPosition().set(0, 0, 0);
        //plane_.show(true);
        GameBox.world().add(plane_);
    }

    public void execute() {
        //plane_.rotateY(0.01f);
    }
}