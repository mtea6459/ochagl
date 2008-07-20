package com.googlecode.ochagl.core;




/**
 * タスクインターフェイス
 */
public interface Task {

    /**
     * タスクの優先順位を取得する。
     *
     * @return 優先順位
     */
    int getPriority();

    /**
     * タスクの名前を取得する。
     *
     * @return タスク名取得
     */
    String getName();

    /**
     * タスクの生死を検査する。
     *
     * @return true:生きている false:死んでいる
     */
    boolean isAlive();

    /**
     * タスク削除 (オーバライド) 派生する場合、super.Kill()を実行すること。
     */
    void kill();

    /**
     * タスク一時停止
     * 
     * @param true:停止 false:再開
     */
    void stop(boolean b);

    /**
     * タスクの停止状態を検査する。
     *
     * @return true:停止 false:再開
     */
    boolean isStop();

    /**
     * タスク処理本体。派生クラスでオーバライドすること。
     */
    void execute();

}
