package com.googlecode.ochagl.app;

import com.googlecode.ochagl.core.Task;
import com.googlecode.ochagl.core.TaskManager;
import com.googlecode.ochagl.graphics.DrawableManager;
import com.googlecode.ochagl.input.InputManager;


/**
 * タスクの基本クラス
 */
public abstract class AbstractTask implements Task {

    /**
     * タスク数
     */
    private static int taskCount__ = 0;

    /**
     * 処理プライオリティ
     */
    private int priority_ = 0;

    /**
     * 生死フラグ
     */
    private boolean alive_ = true;

    /**
     * 停止フラグ
     */
    private boolean isStop_ = false;

    /**
     * タスク名
     */
    private String name_ = null;

    /**
     * タスクの生成
     *
     * @param priority 優先度（小さい順に実行する）
     */
    public AbstractTask() {
        this("", 0);
    }

    /**
     * タスクの生成
     *
     * @param priority 優先度（小さい順に実行する）
     */
    public AbstractTask(final int priority) {
        this("", priority);
    }

    /**
     * タスクの生成
     *
     * @param name タスクの名前
     * @param priority 優先度（小さい順に実行する）
     */
    public AbstractTask(final String name, final int priority) {

        priority_ = priority;
        name_ = name;
        alive_ = true;
        taskCount__++;
    }

    /**
     * 優先度取得
     *
     * @return プライオリティ
     *
     * @see com.googlecode.ochagl.core.Task#getPriority()
     */
    public final int getPriority() {

        return priority_;
    }

    /**
     * 名前取得
     *
     * @return 名前
     *
     * @see com.googlecode.ochagl.core.Task#getName()
     */
    public final String getName() {

        return name_;
    }

    /**
     * 生死検査
     *
     * @return true:生きている false:死亡
     *
     * @see com.googlecode.ochagl.core.Task#isAlive()
     */
    public final boolean isAlive() {

        return alive_;
    }

    /**
     * タスク処理本体 派生クラスでoverrideする
     */
    public abstract void execute();

    /**
     * タスク削除 (オーバライド) 派生する場合、super.Kill()を行うこと
     *
     * @see com.googlecode.ochagl.core.Task#kill()
     */
    public void kill() {

        alive_ = false;
        taskCount__--;
    }

    /**
     * アクティブなタスク数を取得する。
     *
     * @return アクティブなタスク数
     */
    public static int getCount() {

        return taskCount__;
    }

    /**
     * タスク一時停止
     * 
     * @param true:停止 false:再開
     */
    public void stop(boolean b) {
        isStop_ = b;
    }

    /**
     * タスクの停止状態を検査する。
     *
     * @return true:停止 false:再開
     */
    public boolean isStop() {
        return isStop_;
    }

    /**
     * インプットマネージャの取得
     */
    public static final InputManager inputman() {
        return InputManager.getInstance();
    }

    /**
     * タスクマネージャの取得
     */
    public static final TaskManager taskman() {
        return TaskManager.getInstance();
    }

    /**
     * 描画マネージャの取得
     */
    public static final DrawableManager drawman() {
        return GameBox.drawman();
    }
}
