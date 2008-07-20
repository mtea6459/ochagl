package com.googlecode.ochagl.core;

/**
 * CDプレイヤー等の状態(再生、停止、一時停止、コマ送り)を あらわすクラス。
 */
public class PlayState {

    /**
     * 再生状態
     */
    public static final String PLAY = "PLAY";

    /**
     * 停止状態
     */
    public static final String STOP = "STOP";

    /**
     * 再生サブ状態
     */
    public static final String SUB_PLAY = "SUB_PLAY";

    /**
     * 一時停止サブ状態
     */
    public static final String SUB_PAUSE = "SUB_PAUSE";

    /**
     * ステップサブ状態
     */
    public static final String SUB_STEP = "SUB_STEP";

    /**
     * 状態を表現
     */
    private String state_;

    /**
     * サブ状態
     */
    private String subState_;

    /**
     * デフォルトコンストラクタ
     */
    public PlayState() {

        state_ = STOP;
        subState_ = SUB_PLAY;
    }

    /**
     * 再生
     */
    public void play() {

        state_ = PLAY;
        subState_ = SUB_PLAY;
    }

    /**
     * 停止
     */
    public void stop() {

        state_ = STOP;
    }

    /**
     * 一時停止
     */
    public void pause() {

        if (state_ == PLAY) {

            if (subState_ == SUB_PLAY) {

                subState_ = SUB_PAUSE;
            } else if (subState_ == SUB_PAUSE) {

                subState_ = SUB_PLAY;
            } else if (subState_ == SUB_STEP) {

                subState_ = SUB_PLAY;
            }
        }
    }

    /**
     * コマ送り
     */
    public void step() {

        state_ = PLAY;
        subState_ = SUB_STEP;
    }

    /**
     * 状態の取得
     *
     * @return 状態
     */
    public String getState() {

        return state_;
    }

    /**
     * サブ状態の取得
     *
     * @return サブ状態
     */
    public String getSubState() {

        return subState_;
    }
}
