package rigidbody;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.graphics.task.GenericCameraTask;
import com.googlecode.ochagl.input.InputAction;

public class CameraManTask extends AbstractTask {

    private InputAction inView_ = inputman().createTriggerInput(KeyEvent.VK_V);

    private List<GenericCameraTask> list_ = new ArrayList<GenericCameraTask>();

    private int index_ = 0;

    public static final String TASK_VIEW1 = "VIEW1";
    public static final String TASK_VIEW2 = "VIEW2";

    public CameraManTask(int priority, String name) {
        super(name, priority);

        GenericCameraTask viewTask1 = new CameraTask(priority + 1);
        GenericCameraTask viewTask2 = new DCameraTask(priority + 2);
        viewTask1.stop(false);
        viewTask2.stop(true);

        list_.add(viewTask1);
        list_.add(viewTask2);

        taskman().addTask(viewTask1);
        taskman().addTask(viewTask2);

        drawman().addView(0, viewTask1.getView());
        drawman().addView(0, viewTask2.getView());
    }

    private void changeView() {
        GenericCameraTask viewTask = (GenericCameraTask) list_.get(index_);
        viewTask.stop(true);
        index_ = (index_ + 1) % list_.size();
        viewTask = (GenericCameraTask) list_.get(index_);
        viewTask.stop(false);

    }

    protected void checkInput() {
        if (inView_.isPressed()) {
            changeView();
        }
    }

    public void execute() {
        checkInput();
    }
}
