package com.googlecode.ochagl.core;

import java.util.Iterator;


/**
 * タスクマネージャー
 */
public final class TaskManager {

    /**
     * インスタンス。
     */
    private static TaskManager instance__ = null;

    /**
     * タスクリスト。
     */
    private SortedList taskList_ = null;

    /**
     * 基本コンストラクタ。
     */
    private TaskManager() {

        taskList_ = new SortedList();
    }

    /**
     * タスクマネージャのインスタンスを取得する。
     *
     * @return インスタンス
     */
    public static TaskManager getInstance() {

        if (instance__ == null) {

            instance__ = new TaskManager();
        }

        return instance__;
    }

    public static void destroy() {
    	instance__ = null;
    }

    
    /**
     * タスクリストに新しいタスクを追加する。
     *
     * @param task 追加するタスク
     */
    public void addTask(final Task task) {

        taskList_.add(task.getPriority(), task);
    }

    /**
     * タスクを取得する。
     *
     * @param name 取得したいタスクの名前
     *
     * @return タスク
     */
    public Task getTask(final String name) {

        Iterator it = taskList_.iterator();

        while (it.hasNext()) {

            Task t = (Task) it.next();

            if ((t != null) && t.isAlive() && t.getName().equals(name)) {

                return t;
            }
        }

        return null;
    }

    /**
     * 範囲指定した優先度のタスクを削除する。
     *
     * @param from 開始するタスクの優先度
     * @param to 終了するタスクの優先度
     */
    public void removeRange(final int from, final int to) {

        Iterator it = taskList_.iterator();

        while (it.hasNext()) {

            Task t = (Task) it.next();
            int pri = t.getPriority();

            if (t.isAlive() && ((from <= pri) && (pri <= to))) {

                t.kill();
            }
        }
    }

    /**
     * タスクを実行する。
     */
    public void execute() {

        executeTasks();
        removeTasks();
    }

    /**
     * タスクたちを実行する。
     */
    private void executeTasks() {

        Iterator it = taskList_.iterator();

        while (it.hasNext()) {

            Task t = (Task) it.next();

            if ((t != null) && t.isAlive() && !t.isStop()) {

                t.execute();
            }
        }
    }

    /**
     * Killされたタスクを削除する。
     */
    private void removeTasks() {

        Iterator it = taskList_.iterator();

        while (it.hasNext()) {

            Task t = (Task) it.next();

            if (!t.isAlive()) {

                taskList_.remove(t);
            }
        }
    }
}
