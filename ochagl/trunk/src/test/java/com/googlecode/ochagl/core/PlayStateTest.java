package com.googlecode.ochagl.core;

import com.googlecode.ochagl.core.PlayState;

import junit.framework.TestCase;

/**
 * プレイヤー(CDプレイヤーなど)の状態遷移をあらわすクラス
 */
public class PlayStateTest extends TestCase {

	/**
	 * 停止状態のテスト
	 */
	public void testStopState() {

        PlayState playState = new PlayState();
        assertEquals(PlayState.STOP, playState.getState());

        //////////////////
        // 停止→再生
        //////////////////
        playState.play();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // 停止→コマ送り
        //////////////////
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());

        playState.step();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_STEP, playState.getSubState());

        //////////////////
        // 停止→一時停止
        //////////////////
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());

        playState.pause();
        assertEquals(PlayState.STOP, playState.getState());

        //////////////////
        // 停止→停止
        //////////////////
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());

        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());
    }

    /**
     * 再生中状態のテスト
     */
    public void testPlayState() {

        PlayState playState = new PlayState();

        //////////////////
        // 再生→再生
        //////////////////
        playState.play();
        playState.play();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // 再生→一時停止
        //////////////////
        playState.play();
        playState.pause();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PAUSE, playState.getSubState());

        //////////////////
        // 再生→コマ送り
        //////////////////
        playState.play();
        playState.step();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_STEP, playState.getSubState());

        //////////////////
        // 再生→停止
        //////////////////
        playState.play();
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());
    }

    /**
     * 一時停止中状態のテスト
     */
    public void testPauseState() {

        PlayState playState = new PlayState();

        //////////////////
        // 一時停止→再生
        //////////////////
        playState.pause();
        playState.play();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // 一時停止→一時停止
        //////////////////
        playState.play();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());
        playState.pause();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PAUSE, playState.getSubState());
        playState.pause();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // 一時停止→コマ送り
        //////////////////
        playState.pause();
        playState.step();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_STEP, playState.getSubState());

        //////////////////
        // 一時停止→停止
        //////////////////
        playState.pause();
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());
    }

    /**
     * コマ送り中状態のテスト
     */
    public void testStepState() {

        PlayState playState = new PlayState();

        //////////////////
        // コマ送り→再生
        //////////////////
        playState.step();
        playState.play();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // コマ送り→一時停止
        //////////////////
        playState.step();
        playState.pause();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_PLAY, playState.getSubState());

        //////////////////
        // コマ送り→コマ送り
        //////////////////
        playState.step();
        playState.step();
        assertEquals(PlayState.PLAY, playState.getState());
        assertEquals(PlayState.SUB_STEP, playState.getSubState());

        //////////////////
        // コマ送り→停止
        //////////////////
        playState.step();
        playState.stop();
        assertEquals(PlayState.STOP, playState.getState());
    }
}
