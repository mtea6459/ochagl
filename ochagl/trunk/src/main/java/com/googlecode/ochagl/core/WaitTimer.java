package com.googlecode.ochagl.core;

/**
 * ウエィト用のタイマクラス。
 */
public class WaitTimer {

    /**
     * 測定開始時間。
     */
    private long prevTime_ = 0;

    /**
     * コンストラクタ。
     */
    public WaitTimer() {

    }

    /**
     * タイマを初期化する。
     */
    public void reset() {
    	long t = SystemTimer.getTime();
        prevTime_ = t ;
    }

    /**
     * 経過時間を取得する。
     *
     * @return 経過時間
     */
    public long getErapsedTime() {
    	long t = SystemTimer.getTime();

        return t  - prevTime_;
    }
}
