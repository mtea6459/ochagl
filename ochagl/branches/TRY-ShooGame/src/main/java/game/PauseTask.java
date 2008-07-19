package game;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.input.InputAction;
import com.googlecode.ochagl.input.NormalInputAction;


public class PauseTask extends SuperTask {

	private Object2d sprite_ = null;

	private InputAction actionPause_ = inputman().createNormalInput(KeyEvent.VK_P);

	private long count_ = 0;

	public PauseTask(String name, int priority) {
		super(name, priority, SuperTask.ATTR_OTHER);
		Texture texture = GameBox.loadTexture("pause.png");
		sprite_ = ResourceFactory.createObject2d("pasuse");
		sprite_.setTexture(texture);
		sprite_.setPosition(GameBox.width() / 2 - sprite_.getWidth() / 2, GameBox.height() / 2 - sprite_.getHeight() / 2);
		view2d.addRenderObject(LAYER_PLAYER, sprite_);
		actionPause_ = new NormalInputAction();
	}

	public void kill() {
		super.kill();
		sprite_.kill();
	}

	protected void doGameTask() {
		if (actionPause_.isPressed()) {
			if (getGameState() != GAMESTATE_PAUSE) {
				changeGameState(GAMESTATE_PAUSE);
				count_ = 0;
				sprite_.show(true);
			} else {
				changeGameState(GAMESTATE_GAME);
				sprite_.show(false);
			}
		}
		if (getGameState() == GAMESTATE_PAUSE) {
			sprite_.show(true);
			if (count_++ % 60 >= 30)
				sprite_.show(false);
		}
	}
}