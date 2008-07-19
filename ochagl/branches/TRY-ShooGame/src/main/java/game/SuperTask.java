package game;
import java.util.Random;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.Rect;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.View2d;


/**
 * このゲームのすべてのタスクのスーパークラス。 
 * @author ocha
 */
public abstract class SuperTask extends AbstractTask {

	public static final int ATTR_PLAYER = 0;
    public static final int ATTR_PLAYER_BULLET = 1;
    public static final int ATTR_ENEMY = 2;
    public static final int ATTR_BULLET = 3;
    public static final int ATTR_PAUSE = 4;
    public static final int ATTR_LOGIC = 5;
    public static final int ATTR_OTHER = 6;
    
    // タスク優先
    public static final int PRIO_PLAYER = 0; 
    public static final int PRIO_INPUT = 1; 
    public static final int PRIO_ENEMY = 2;
    public static final int PRIO_BULLET = 3; 
    public static final int PRIO_HIT = 4; 
    public static final int PRIO_ENEMYMANAGER = 5;
    public static final int PRIO_OTHER = 99; 

    // 描画レイヤ
    public static final int LAYER_BG = 0; 
    public static final int LAYER_PLAYER = 1; 
    public static final int LAYER_ENEMY = 2; 
    public static final int LAYER_BULLET = 3; 
    public static final int LAYER_BURST = 4; 
    public static final int LAYER_PANEL = 5; 
    public static final int LAYER_2DOBJECT = 6; 
    public static final int LAYER_FADE = 7; 
    public static final int LAYER_DEBUG = 8;

    // タスクID
    public static final String TASK_MOTHER = "MOTHER"; 
    public static final String TASK_TITLE = "TITLE"; 
    public static final String TASK_PLAYER = "PLAYER"; 
    public static final String TASK_ENEMYMANAGER = "ENEMYMANAGER"; 
    public static final String TASK_BG = "BG"; 
    public static final String TASK_HIT = "HIT"; 
    public static final String TASK_PAUSE = "PAUSE";
    public static final String TASK_FADE = "FADE";
    public static final String TASK_DEBUG = "DEBUG"; 

    // ゲーム全体の状態
    public static final int SCENESTATE_TITLE = 0; 
    public static final int SCENESTATE_GAME = 1; 
    public static final int SCENESTATE_GAMEOVER = 2; 

    // ゲーム中の状態
    public static final int GAMESTATE_DEMO = 0; 
    public static final int GAMESTATE_GAME = 1; 
    public static final int GAMESTATE_PAUSE = 2; 

    public static final int DISP_AREA_WIDTH = 512;
    public static final int LOGIC_AREA_MARGIN = 64;

    public static final View2d view2d = ResourceFactory.createView2d();

    public static Rect dispArea;
    public static Rect logicArea;
    public static Random random;

    private static int state_ = 0;
    private static int gameState_ = 0;
    
    protected static FadeTask fadeTask;

    protected int attr_ = 0;

    public SuperTask(String name, int priority, int attr) {
        super(name, priority);
        attr_ = attr;
    }

    public int getAttr() {
    	return attr_;
    }
    
    public void execute() {
        doGameTask();
    }

    public void kill() {
        super.kill();
    }

    protected abstract void doGameTask();

    public static void initialize() {

        //view2d.unsetState(View2d.CLEAR);
        view2d.show(true);
        drawman().addView(2, view2d);

        random = new Random();
        
        float x = -DISP_AREA_WIDTH/2;
        float y = GameBox.height()/2;
        dispArea = new Rect(x, y, DISP_AREA_WIDTH, GameBox.height());
        logicArea = new Rect(x - LOGIC_AREA_MARGIN, y + LOGIC_AREA_MARGIN, 
                DISP_AREA_WIDTH + LOGIC_AREA_MARGIN * 2,
                GameBox.height() + LOGIC_AREA_MARGIN * 2);

        fadeTask = new FadeTask(TASK_FADE, PRIO_PLAYER);
        taskman().addTask(fadeTask);
        taskman().addTask(new TitleTask(TASK_TITLE, PRIO_OTHER));

        changeSceneState(SCENESTATE_TITLE);
        changeGameState(GAMESTATE_GAME);

        loadTexutres();
    }

    private static void loadTexutres() {
        //GameBox.loadTexture("bg.png");
        //GameBox.loadTexture("burst.png"); 
        //GameBox.loadTexture("pause.png");
        //GameBox.loadTexture("btl_1.png");
        //GameBox.loadTexture("shot1.png");
        GameBox.loadTexture("title.png");
        GameBox.loadTexture("pressspace.png");
        //GameBox.loadTexture("tama1.png");
        //GameBox.loadTexture("tama.png"); 
       // GameBox.loadTexture("enemy.png");
    }

    public static void setupPlayGame() {
        //taskman().addTask(new BgTask(TASK_BG, PRIO_OTHER));
        taskman().addTask(new PlayerTask(TASK_PLAYER, PRIO_PLAYER));
        taskman().addTask(new HitTask(TASK_HIT, PRIO_HIT));
        taskman().addTask(new PauseTask(TASK_PAUSE, PRIO_OTHER));
        changeSceneState(SCENESTATE_GAME);
        changeGameState(GAMESTATE_GAME);

        taskman().addTask(new HassyaTask(TASK_BG, PRIO_OTHER));

        taskman().addTask(new ViewTask(""));
    }

    public static final void changeSceneState(int state) {
        state_ = state;
    }
    
    public  static final void changeGameState(int state) {
        gameState_ = state;
    }
    
    public  static final int getSceneState() {
        return state_;
    }
    
    public  static final int getGameState() {
        return gameState_;
    }

 }
