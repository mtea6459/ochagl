/*
 * Created on 2005/11/29
 */
package com.googlecode.ochagl.graphics.xloader;

import java.util.HashMap;
import java.util.Map;


/**
 * トークンの種類を表すクラス。
 * @author micky
 *
 */
public class ScriptTokenType {

    /** 文の終りをあらわす。 */
    public static final int EOS = -1;

    /** 整数をあらわす。 */
    public static final int INT = 256;

    /** 実数をあらわす。 */
	public static final int FLOAT = 257;

    /** 文字列をあらわす。 */
	public static final int STRING = 258;

	/** キーワード（予約語）をあらわす。 */
    public static final int KEYWORD = 259;

	/** シンボル(予約語以外)をあらわす。 */
    public static final int SYMBOL = 260;
    
    /** 予約語。 */
    private static Map reserved_ = new HashMap();
    static {
        reserved_.put("Frame", "");
        reserved_.put("Mesh", "");
    }

    /**
     * 予約語のチェック。
     * 
     * @param symbol
     *            シンボル
     * @return true: あり false :なし
     */
    public static boolean isReserve(String symbol) {
        return reserved_.containsKey(symbol);
    }

    /**
     * 予約語のトークン種類を取得
     * 
     * @param symbol
     *            シンボル
     * @return トークン種類
     */
    public static int getReserveTokenType(String symbol) {
        return ((Integer) reserved_.get(symbol)).intValue();
    }
}
