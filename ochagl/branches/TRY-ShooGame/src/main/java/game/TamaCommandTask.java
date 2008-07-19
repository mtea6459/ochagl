package game;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.ochagl.app.GameBox;

// íeÇëÄçÏÇ∑ÇÈÉ^ÉXÉN
public class TamaCommandTask extends SuperTask {
    public static final int NORMAL = 0;
    public static final int HOMING = 1;
    public static final int DIRECTION = 2;
    public static final int WAIT = 3;
    
    
    private List<int[]> commands_ = new ArrayList<int[]>();

    private int step_;

    private int pc_;

    private TamaTask tama_;

    private int wait_;

    public TamaCommandTask(int priority) {
        super("TAMACOMMAND", priority, SuperTask.ATTR_OTHER);
        reset();
    }

    public void reset() {
        if (tama_ == null)
            tama_ = new TamaTask(0, GameBox.height() / 2);
        step_ = 0;
        pc_ = 0;
        commands_.clear();
    }

    public void addCommand(int[] cmd) {
        commands_.add(cmd);
    }

    public void start() {
        taskman().addTask(tama_);
        step_ = fetch();
    }

    private int fetch() {
        while (pc_ < commands_.size()) {
            int[] cmd = commands_.get(pc_);
            if (cmd[0] == NORMAL) {
                tama_.setMode(TamaTask.MODE_NORMAL);
            } else if (cmd[0] == HOMING) {
                tama_.setMode(TamaTask.MODE_HOMING);
            } else if (cmd[0] == DIRECTION) {
                tama_.changeDirection(cmd[1], cmd[2]);
            } else if (cmd[0] == WAIT) {
                wait_ = cmd[1];
                pc_++;
                return 2;
            }
            pc_++;
        }
        return 0;
    }

    protected void doGameTask() {
        switch (step_) {
        case 0:
            break;
        case 1:
            step_ = fetch();
            break;
        case 2:
            if (--wait_ <= 0) {
                step_ = 1;
            }
            break;
        }
    }
}
