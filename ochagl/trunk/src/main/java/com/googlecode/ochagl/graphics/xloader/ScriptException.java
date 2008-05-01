/*
 * Created on 2005/12/01
 */
package com.googlecode.ochagl.graphics.xloader;

public class ScriptException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ。
     */
    public ScriptException() {
        super();
    }
    
    /**
     * コンストラクタ。
     * @param s メッセージ
     */
    public ScriptException(String s) {
        super(s);
    }
}
