/*
 * Created on 2005/03/29
 */
package com.googlecode.ochagl.graphics;

/**
 * メッシュのシェード方法の定義．
 */
public class ShadeMode {

    /**
     * ワイヤフレーム．
     */
    public static final ShadeMode WIRE = new ShadeMode();

    /**
     * フラット．
     */
    public static final ShadeMode FLAT = new ShadeMode();

    /**
     * グーロ．
     */
    public static final ShadeMode GOURAUD = new ShadeMode();
}
