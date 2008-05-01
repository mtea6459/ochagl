/*
 * 作成日： 2004/09/20
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package com.googlecode.ochagl.core;

import com.googlecode.ochagl.app.AbstractTask;


/**
 * @author ocha
 *
 *
 *
 */
public class TaskObject extends AbstractTask {
    StringBuffer sb_ = null;

    public TaskObject(int prio, StringBuffer sb) {
        this("", prio, sb);
    }

    public TaskObject(String name, int prio, StringBuffer sb) {
        super(name, prio);
        sb_ = sb;
    }

    public void execute() {
        sb_.append(getPriority());
    }
}
