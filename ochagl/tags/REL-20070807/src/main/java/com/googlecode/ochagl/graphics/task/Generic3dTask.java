package com.googlecode.ochagl.graphics.task;

import com.googlecode.ochagl.app.AbstractTask;
import com.googlecode.ochagl.graphics.Object3d;

/**
 * タスクの基本クラス
 */
public abstract class Generic3dTask extends AbstractTask {

    protected Object3d obj_ = null;
    
    /**
	 * タスクの生成
	 * @param priority 優先度（小さい順に実行する）
	 */
	public Generic3dTask(int priority) {
		this("", priority);
    }

    /**
     * タスクの生成
     * @param priority 優先度（小さい順に実行する）
     * @param name タスクの名前
     */
    public Generic3dTask(String name, int priority) {
        super(name, priority);
    }

    /**
     * オブジェクトの取得
     * @return 3Dオブジェクト
     */
    public Object3d getObject() {
        return obj_;
    }
}
