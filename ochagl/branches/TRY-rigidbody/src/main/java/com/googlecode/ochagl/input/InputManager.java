package com.googlecode.ochagl.input;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputManager implements KeyListener, MouseListener,
		MouseMotionListener, MouseWheelListener {

//TODO JavaWebStart実行時に、アクセスデニードエラーになるのでコメントアウト
    //	public static final Cursor INVISIBLE_CURSOR = Toolkit.getDefaultToolkit()
//			.createCustomCursor(Toolkit.getDefaultToolkit().getImage(""),
//					new Point(0, 0), "invisible");

	public static final int MOUSE_BUTTON_1 = 600;

	public static final int MOUSE_BUTTON_2 = 601;

	public static final int MOUSE_BUTTON_3 = 602;

	private Map<Integer, NormalInputAction> normals_ = new HashMap<Integer, NormalInputAction>();
    private Map<Integer, TriggerInputAction> triggers_ = new HashMap<Integer, TriggerInputAction>();
    private Map<Integer, RepeatInputAction> repeats_ = new HashMap<Integer, RepeatInputAction>();
    private List<Map> actions_ = new ArrayList<Map>();

    private Point mouse_;

	private Component comp_;

	private static InputManager instance__;
    
    /**
     * 基本コンストラクタ。
     */
	private InputManager() {
	    actions_.add(normals_);
        actions_.add(triggers_);
        actions_.add(repeats_);
        mouse_ = new Point();
	}

    /**
     * 描画物マネージャのインスタンスを取得する。
     *
     * @return インスタンス
     */
    public static InputManager getInstance() {

        if (instance__ == null) {
            instance__ = new InputManager();
        }

        return instance__;
    }
    
    public static void destroy() {
    	instance__ = null;
    }

    public NormalInputAction createNormalInput(int keyCode) {
        NormalInputAction ia = normals_.get(keyCode);
        if (ia == null) {
            ia = new NormalInputAction();
            normals_.put(new Integer(keyCode), ia);
        }
        return ia;
    }

    public TriggerInputAction createTriggerInput(int keyCode) {
        TriggerInputAction ia = triggers_.get(keyCode);
        if (ia == null) {
            ia = new TriggerInputAction();
            triggers_.put(new Integer(keyCode), ia);
        }
        return ia;
    }

    public RepeatInputAction createRepeatInput(int keyCode) {
        RepeatInputAction ia = repeats_.get(keyCode);
        if (ia == null) {
            ia = new RepeatInputAction();
            repeats_.put(new Integer(keyCode), ia);
        }
        return ia;
    }

    public void setComponet(Component cmp) {
		comp_ = cmp;
		comp_.addKeyListener(this);
		comp_.addMouseListener(this);
		comp_.addMouseMotionListener(this);
		comp_.addMouseWheelListener(this);
		comp_.setFocusTraversalKeysEnabled(false);
	}

	public void setCursor(Cursor cursor) {
		comp_.setCursor(cursor);
	}

	public void resetActions() {
        for (Map<Integer, InputAction> action: actions_) {
            for (Map.Entry<Integer, InputAction> entry : action.entrySet()) {
                InputAction ia = entry.getValue();
                ia.reset();
            }
        }
	}

	public void updateActions(final long loopCount) {
        for (Map<Integer, InputAction> action: actions_) {
            for (Map.Entry<Integer, InputAction> entry : action.entrySet()) {
                InputAction ia = entry.getValue();
                ia.update(loopCount);
            }
        }
	}

	public static String getKeyName(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}

	public int getMouseX() {
		return mouse_.x;
	}

	public int getMouseY() {
		return mouse_.y;
	}

	private static int getMouseButtonCode(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			return MOUSE_BUTTON_1;
		case MouseEvent.BUTTON2:
			return MOUSE_BUTTON_2;
		case MouseEvent.BUTTON3:
			return MOUSE_BUTTON_3;
		default:
			return -1;
		}
	}

	// キーを押したとき
	public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Map<Integer, InputAction> action: actions_) {
            InputAction ia = action.get(keyCode);
            if (ia != null) {
                ia.press();
            }
        }
		e.consume();
	}

	// キーが離されたとき
	public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Map<Integer, InputAction> action: actions_) {
            InputAction ia = action.get(keyCode);
            if (ia != null) {
                ia.release();
            }
        }
		e.consume();
	}

	// 文字が入力されたとき
	public void keyTyped(KeyEvent e) {
		//System.out.println("key typed");
		e.consume();
	}

	// マウスを押した瞬間
	public void mousePressed(MouseEvent e) {
        int keyCode = getMouseButtonCode(e);
        for (Map<Integer, InputAction> action: actions_) {
            InputAction ia = action.get(keyCode);
            if (ia != null) {
                ia.press();
            }
        }
	}

	// マウスボタンがあがったとき
	// clickedより早いイベント
	public void mouseReleased(MouseEvent e) {
        int keyCode = getMouseButtonCode(e);
        for (Map<Integer, InputAction> action: actions_) {
            InputAction ia = action.get(keyCode);
            if (ia != null) {
                ia.release();
            }
        }
	}

	// マウスボタンをクリックしボタンがあがったとき
	// releaseのあとのイベント
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	// マウスがウィンドウ内に入ったとき
	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	// マウスがウィンドウから外れたとき
	public void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	// マウスがドラッグされたとき
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	// マウスが動いたとき
	public synchronized void mouseMoved(MouseEvent e) {
		mouse_.x = e.getX();
		mouse_.y = e.getY();
		//System.out.println("mouse:"+ mouse.x+"," + mouse.y);
	}
}
