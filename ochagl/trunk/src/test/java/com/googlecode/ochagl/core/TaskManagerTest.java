/*
 * 作成日： 2004/09/20
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package com.googlecode.ochagl.core;

import com.googlecode.ochagl.core.TaskManager;

import junit.framework.TestCase;

/**
 * @author ocha
 *
 * 
 * 
 */
public class TaskManagerTest extends TestCase {

    StringBuffer sb_ = null;

    /**
     * 
     */
    public TaskManagerTest() {
        super();
        sb_ = new StringBuffer("");
    }

    /**
     * 実行順のテスト
     * 優先順に実行されること
     * 同一優先順の場合も登録できること
     */
    public void testExecute() {
        TaskManager man = TaskManager.getInstance();
        man.addTask(new TaskObject(4, sb_));
        man.addTask(new TaskObject(1, sb_));
        man.addTask(new TaskObject(3, sb_));
        man.addTask(new TaskObject(2, sb_));
        man.addTask(new TaskObject(4, sb_));
        man.addTask(new TaskObject(0, sb_));
        man.execute();
        assertEquals("012344", sb_.toString());
    }

    /**
     * 指定優先度範囲内のタスクを削除
     */
    public void testRemoveRange() {
        TaskManager man = TaskManager.getInstance();
        man.addTask(new TaskObject(4, sb_));
        man.addTask(new TaskObject(1, sb_));
        man.addTask(new TaskObject(3, sb_));
        man.addTask(new TaskObject(2, sb_));
        man.addTask(new TaskObject(0, sb_));
        man.removeRange(1, 3);
        man.execute();
        assertEquals("04", sb_.toString());
    }


    /**
     * 指定タスクを取得
     */
    public void testGetTask() {
        TaskManager man = TaskManager.getInstance();
        man.addTask(new TaskObject("task1", 4, sb_));
        man.addTask(new TaskObject("task2", 1, sb_));
        man.addTask(new TaskObject("task3", 3, sb_));
        man.addTask(new TaskObject("task4", 2, sb_));
        man.addTask(new TaskObject("task5", 0, sb_));
        man.execute();
        TaskObject task = (TaskObject) man.getTask("task3");
        assertEquals("task3", task.getName());
    }

    // 指定したタスクの処理がポーズされているか
    
    // 同じ優先を持つタスクが重複して登録されること
    
    // removeRangeの限界値チェック
    
}
