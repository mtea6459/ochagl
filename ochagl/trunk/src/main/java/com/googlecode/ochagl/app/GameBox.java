package com.googlecode.ochagl.app;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;


import com.googlecode.ochagl.core.SystemTimer;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.DrawableManager;
import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.ResourceFactory;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.jogl.GraphicContextJogl;
import com.googlecode.ochagl.input.InputManager;
import com.googlecode.ochagl.math.Mat4;
import com.sun.opengl.util.Animator;

public class GameBox {
    private static long lastLoopTime__;

    private static long lastFpsTime__;

    private static int fpsCount__;

    private static int fps__;
    
    private static boolean wait__;

    private static long loopCount__;

    private static int width__;

    private static int height__;

    private static Object3d world__;

    private static InputManager inputman__;

    private static TaskManager taskman__;

    private static DrawableManager drawman__;

    private static GraphicContext graphicContext__;

    private static GLCanvas canvas__;

    private static List<AddHook> initializeHooks__;
    private static List<AddHook> preTaskHooks__;
    private static List<AddHook> postTaskHooks__;
    private static List<AddHook> preDrawsHooks__;
    private static List<AddHook> postDrawHooks__;
    private static Listener glListener__;

    // //////////////////////////////////////////////////////////////////////
    // GLEventListenerの実装
    private static class Listener implements GLEventListener {
        private Animator animator_;
        public Listener() {
            animator_ = new Animator(canvas__);
        }
        public void display(GLAutoDrawable drawable) {
            clear();
            calcFps();
            inputman().updateActions(loopCount__);

            preTaskExecute();
            taskman().execute();
            postTaskExecute();

            world().setupTree(Mat4.imat);
            preDraw();
            drawman().draw(graphicContext__);
            postDraw();

            loopCount__++;
        }

        public void displayChanged(GLAutoDrawable drawable,
                                   boolean modeChanged, boolean deviceChanged) {
        }

        public void init(GLAutoDrawable drawable) {
            canvas__.requestFocus();
            initialize();

            final GL gl = drawable.getGL();
            System.out.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
            System.out
                .println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
            System.out.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
        }

        public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                            int height) {
            final GL gl = drawable.getGL();
            gl.glMatrixMode(GL.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrtho(0, width, height, 0, -1, 1);
        }

        public void startAnime() {
            animator_.start();
        }

        public void stopAnime() {
            animator_.stop();
        }
    }

    ////////////////////////////////////////////////////////////////////////

    private GameBox() {
    }

    // //////////////////////////////////////////////////////////////////////
    public static void sysinit(int width, int height) {
        width__ = width;
        height__ = height;
        lastLoopTime__ = SystemTimer.getTime();
        wait__ = true;
        
        canvas__ = new GLCanvas();
        canvas__.setSize(width__, height__);

        glListener__ = new Listener();
        canvas__.addGLEventListener(glListener__);

        graphicContext__ = new GraphicContextJogl(canvas__, width__, height__);
        InputManager.getInstance().setComponet(canvas__);

        getCapabilities();

        world__ = ResourceFactory.createObject3d("world");
        inputman__ = InputManager.getInstance();
        taskman__ = TaskManager.getInstance();
        drawman__ = new DrawableManager();

        initializeHooks__ = new ArrayList<AddHook>();
        preTaskHooks__ = new ArrayList<AddHook>();
        postTaskHooks__ = new ArrayList<AddHook>();
        preDrawsHooks__ = new ArrayList<AddHook>();
        postDrawHooks__ = new ArrayList<AddHook>();

    }

    public static void sysexit() {
        InputManager.destroy();
        TaskManager.destroy();
    }

    public static void startAnime() {
        glListener__.startAnime();
    }

    public static void stopAnime() {
        glListener__.stopAnime();
    }

    private static GLCapabilities getCapabilities() {
        GLCapabilities caps = new GLCapabilities();
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        return caps;
    }

    private static void clear() {
        final GL gl = canvas__.getGL();
        gl.glClearDepth(1.0);
        gl.glClearColor(0, 0, 0.5f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        DebugFont.clear();
    }

    private static void calcFps() {
        // System.out.println("val:" + (lastLoopTime_+16 -
        // SystemTimer.getTime()));
        long wait = lastLoopTime__ + 16 - SystemTimer.getTime();
        if (wait__ && wait >= 0) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
            }
        }

        lastFpsTime__ += SystemTimer.getTime() - lastLoopTime__;
        lastLoopTime__ = SystemTimer.getTime();
        fpsCount__++;

        if (lastFpsTime__ >= 1000) {
            lastFpsTime__ = 0;
            fps__ = fpsCount__;
            fpsCount__ = 0;
        }
    }

    public static void addInitialize(AddHook hook) {
        initializeHooks__.add(hook);
    }

    public static void addPreTask(AddHook hook) {
        preTaskHooks__.add(hook);
    }

    public static void addPostTask(AddHook hook) {
        postTaskHooks__.add(hook);
    }

    public static void addPreDraw(AddHook hook) {
        preDrawsHooks__.add(hook);
    }

    public static void addPostDraw(AddHook hook) {
        postDrawHooks__.add(hook);
    }

    public static void initialize() {
        for (AddHook h : initializeHooks__) {
            h.execute();
        }
    }

    private static void preTaskExecute() {
        for (AddHook h : preTaskHooks__) {
            h.execute();
        }
    }

    private static void postTaskExecute() {
        for (AddHook h : postTaskHooks__) {
            h.execute();
        }
    }

    private static void preDraw() {
        for (AddHook h : preDrawsHooks__) {
            h.execute();
        }
    }

    private static void postDraw() {
        for (AddHook h : postDrawHooks__) {
            h.execute();
        }
    }

    /**
     * テクスチャを読み込む。
     * 
     * @param fileName
     *            ファイル名
     * @return テクスチャオブジェクト
     */
    public static Texture loadTexture(final String fileName) {
        return drawman().loadTexture(graphicContext__, fileName);
    }

    public static GLCanvas canvas() {
        return canvas__;
    }

    public static InputManager inputman() {
        return inputman__;
    }

    public static TaskManager taskman() {
        return taskman__;
    }

    public static DrawableManager drawman() {
        return drawman__;
    }

    public static int width() {
        return width__;
    }

    public static int height() {
        return height__;
    }

    public static void wait(boolean b) {
        wait__ = b;
    }

    public static Object3d world() {
        return world__;
    }

    public static int fps() {
        return fps__;
    }

    public static long loopCount() {
        return loopCount__;
    }

    public static Frame buildFrame(String title, int width, int height) {
        Frame frame = new Frame(title);
        Dimension d = frame.getToolkit().getScreenSize();
        int x = (int) (d.getWidth() / 2) - (int) (width/ 2);
        int y = (int) (d.getHeight() / 2) - (int) (height / 2);
        frame.setBounds(x, y, width, height);//のコンポーネントを移動し、サイズ変更します
        frame.setPreferredSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() { // for Java SE5
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        return frame;
    }

}
