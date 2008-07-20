/*
 * Created on 2005/11/29
 */
package com.googlecode.ochagl.graphics.xloader;

import java.io.Reader;

/**
 * 一文字戻り機能付きのリーダ。
 */
class LexReader {

    /** リーダ。 */
    private Reader reader_ = null;
    
    /** 文字コード。 */
    private int ch_ = 0;
    
    /** カラム番号 */
    private int col_;

    /** 行番号 */
    private int row_;

    /**
     *  文字列の読み込みフラグ。
     *  true:読まない false:読む
     */
    private boolean unread_ = false;

    LexReader(Reader r) {
        reader_ = r;
    }
    
    public LexReader() {
	}
    
    void setReader(Reader r) {
    	reader_ = r;
    }
    
    /**
     * 文字列を読み込む。
     * @return 文字コード　
     */
    int read()
    {
        try {
            if (unread_) {
                unread_ = false;
            } else {
                ch_ = reader_.read();
                if ((char)ch_ == '\n') {
                    col_ = 0;
                    row_++;
                } else {
                    col_++;
                }
            }
        } catch (Exception e) {
        	ch_ = -1;
        }
		//System.out.print((char)ch_);
        return ch_;
    }

    /**
     * 一文字戻す。<br>
     * 実際には、read()メソッドが呼ばれた時に、1度だけ文字を読まないようにする。
     */
    void unread()
    {
        unread_ = true;
    }

    /**
     * 読み込み位置（列）を返す。1オリジン。
     * @return 列
     */
    public int getCol() {
        return col_ + 1;
    }

    /**
     * 読み込み位置（行）を返す。1オリジン。
     * @return 行
     */
    public int getRow() {
        return row_ + 1;
    }

    /**
     * 繰り返し可能な初期化
     */
    public void reset() {
        row_ = 0;
        col_ = 0;
    }
}
