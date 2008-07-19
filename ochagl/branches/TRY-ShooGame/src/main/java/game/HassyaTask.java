package game;

import java.awt.event.KeyEvent;

import com.googlecode.ochagl.input.InputAction;

public class HassyaTask extends SuperTask {

    private InputAction actionStart_ = inputman().createTriggerInput(KeyEvent.VK_T);

    private int count_;

    private int wait_;

    private int step_;

    public HassyaTask(String name, int priority) {
        super(name, priority, SuperTask.ATTR_OTHER);
        step_ = 0;
        wait_ = 0;
    }

    protected void commands() {
        TamaCommandTask t = new TamaCommandTask(0);
        t.addCommand(new int[]{TamaCommandTask.HOMING});
        //t.addCommand(new int[] { TamaCommandTask.NORMAL });
        t.addCommand(new int[] { TamaCommandTask.DIRECTION, -90, 30 });
        t.addCommand(new int[] { TamaCommandTask.WAIT, 30 });
        t.addCommand(new int[] { TamaCommandTask.DIRECTION, -140, 20 });
        t.start();
        taskman().addTask(t);
    }

    protected void doGameTask() {
        switch (step_) {
        case 0:
            if (actionStart_.isPressed()) {
                step_++;
                count_ = 10;
            }
            break;
        case 1:
            if (--count_ > 0) {
                commands();
                wait_ = 15;
                step_++;
            } else {
                step_ = 0;
            }
            break;
        case 2:
            if (--wait_ <= 0) {
                step_ = 1;
            }
            break;
            
        }
    }
}
