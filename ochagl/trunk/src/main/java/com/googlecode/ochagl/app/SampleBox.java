package com.googlecode.ochagl.app;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.task.GenericCameraTask;
import com.googlecode.ochagl.graphics.task.GenericLightTask;
import com.googlecode.ochagl.input.InputAction;


public class SampleBox {
    static final int WIDTH = 640;
    static final int HEIGHT = 480;

    public static final String TASK_PLAYER = "PLAYER";

    public static final String TASK_BG = "BG";

    public static final String TASK_LIGHT = "LIGHT";

    public static final String TASK_VIEW = "VIEW";

    public static final String TEX_EARTH = "earth.png";

    public SampleBox(String title) {
        GameBox.sysinit(WIDTH, HEIGHT);
        GameBox.addInitialize(new AddHook() {
                public void execute() {
                    GenericLightTask lightTask = new GenericLightTask("",0);
                    ViewTask viewTask = new ViewTask("");
                    lightTask.getLight().setPosition(0,0,-7);

                    TaskManager man = TaskManager.getInstance();
                    man.addTask(lightTask);
                    man.addTask(viewTask);

                    GameBox.world().show(true);// TODO ïKóvÅH
                    GameBox.world().add(lightTask.getLight());

                    GameBox.drawman().addView(0, viewTask.getView());
                }
            }
            );
        GameBox.addPreTask(new AddHook() {
                InputAction exitAction = GameBox.inputman().createTriggerInput(
                                                                               KeyEvent.VK_ESCAPE);;

                public void execute() {
                    if (exitAction.isPressed()) {
                        System.out.println("ESC KEY PRESSED!!");
                        System.exit(0);
                    }
                }
            });

        Frame frame = GameBox.buildFrame(title, WIDTH, HEIGHT);
        frame.add(GameBox.canvas());
        frame.setVisible(true);
    }
}

class ViewTask extends GenericCameraTask {

    private InputAction inUp_ = inputman().createNormalInput(KeyEvent.VK_W);
    private InputAction inDn_ = inputman().createNormalInput(KeyEvent.VK_S);
    private InputAction inLt_ = inputman().createNormalInput(KeyEvent.VK_A);
    private InputAction inRt_ = inputman().createNormalInput(KeyEvent.VK_D);
    private InputAction inGo_ = inputman().createNormalInput(KeyEvent.VK_E);
    private InputAction inBk_ = inputman().createNormalInput(KeyEvent.VK_Q);

    private Object3d own_ = null;

    public ViewTask(String name) {
        super(name, 0, GameBox.world(), GameBox.width(), GameBox.height());
        getCamera().setPosition(0, 0, -7);
    }

    protected void checkInput() {

        if (inGo_.isPressed()) {
            getCamera().moveZ(0.2f);
        }
        if (inBk_.isPressed()) {
            getCamera().moveZ(-0.2f);
        }
        if (inUp_.isPressed()) {
            getCamera().moveY(0.2f);
        }
        if (inDn_.isPressed()) {
            getCamera().moveY(-0.2f);
        }
        if (inLt_.isPressed()) {
            getCamera().moveX(0.2f);
        }
        if (inRt_.isPressed()) {
            getCamera().moveX(-0.2f);
        }
    }

    public void execute() {
        checkInput();
    }
}
