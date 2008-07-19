package game;
import com.googlecode.ochagl.app.GameBox;
import com.googlecode.ochagl.core.InterpolateLinear;
import com.googlecode.ochagl.graphics.Object2d;
import com.googlecode.ochagl.graphics.ResourceFactory;

public class FadeTask extends SuperTask {

    public static final int NONE = 0;
    public static final int FADE = 1;
    public static final int DONE = 2;

    private Object2d tile_ = null;

    private int mode_ = 0;
    private int count_ = 0;
    private float toR_ = 0.0f;
    private float toG_ = 0.0f;
    private float toB_ = 0.0f;

    private InterpolateLinear a = new InterpolateLinear();
    
    public FadeTask(String name, int priority) {
        super(name, priority, SuperTask.ATTR_OTHER);
        tile_ = ResourceFactory.createObject2d("fade");
        tile_.setWidth(GameBox.width());
        tile_.setHeight(GameBox.height());
        view2d.addRenderObject(LAYER_FADE, tile_);
        reset();
    }

    public void reset() {
        tile_.setRGBA(0.0f, 0.0f, 0.0f, 1.0f);
        tile_.setLeftopPosition(0, 0);
        tile_.show(true);
        mode_ = DONE; 
    }
    
    public void kill() {
    	super.kill();
    }

    public void setRgb(float r, float g, float b) {
        toR_ = r;
        toG_ = g;
        toB_ = b;
    }

    // •s“§–¾‚©‚ç“§–¾‚Ö
    public void fadeIn(long time) {
        mode_ = FADE; 
        count_ = 0;
        a.setValue(0.0f, 1.0f);
        a.setValue(1.0f, 0.0f);
        a.setDuration(time);
        a.setStartTime(0);
        tile_.setRGBA(toR_, toG_, toB_, 1.0f);
    }
    
    // “§–¾‚©‚ç•s“§–¾‚Ö
    public void fadeOut(long time) {
        mode_ = FADE; 
        count_ = 0;
        a.setValue(0.0f, 0.0f);
        a.setValue(1.0f, 1.0f);
        a.setDuration(time);
        a.setStartTime(0);
        tile_.setRGBA(toR_, toG_, toB_, 0.0f);
    }
    
    public int getMode() {
        return mode_;
    }
    
    protected void doGameTask() {
        tile_.show(true);
        switch (mode_) {
            case NONE:
                tile_.show(false);
                break;
            case FADE:
                tile_.setRGBA(toR_, toG_, toB_, a.getValue(count_++));
                if (a.getFraction() >= 1.0f) {
                    mode_ = DONE;
                }
                break;
            case DONE:
                break;
        }
    }
}

