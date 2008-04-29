package com.googlecode.ochagl.core;



// TODO ナノとミリを使い分けるべき
public final class SystemTimer {

    /**
     * コンストラクタ。生成禁止
     */
    private SystemTimer() {

    }

    public static long getTime() {
    	return System.nanoTime() / 1000 / 1000; // ミリ秒へ変換
    }
}
