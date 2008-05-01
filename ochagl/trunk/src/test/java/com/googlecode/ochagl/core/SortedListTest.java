/*
 * 作成日： 2004/09/20
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package com.googlecode.ochagl.core;

import java.util.Iterator;

import com.googlecode.ochagl.core.SortedList;

import junit.framework.TestCase;

/**
 * @author ocha
 *
 * 
 * 
 */
public class SortedListTest extends TestCase {

	class TestObject {
		private int i_;
		public TestObject(int i) {
			i_ = i;
		}
		public String toString() {
			return String.valueOf(i_);
		}
	}

    /**
     * 
     */
    public SortedListTest() {
        super();
    }

    /**
     * 追加のテスト
     */
    public void testAdd() {
		StringBuffer sb = new StringBuffer();
		SortedList list = new SortedList();
		list.add(0, new TestObject(0));
		list.add(2, new TestObject(2));
		list.add(4, new TestObject(4));
		list.add(3, new TestObject(3));
		list.add(1, new TestObject(1));
		
		Iterator it = list.iterator();
		while (it.hasNext()) {
			TestObject o = (TestObject) it.next();
			sb.append(o.toString());
		}
		assertEquals("01234", sb.toString());
    }

    /**
	 * 削除のテスト
     */
    public void testRemoveRange() {
        StringBuffer sb = new StringBuffer();
        SortedList list = new SortedList();
        TestObject to = null;
        list.add(0, new TestObject(0));
        list.add(2, new TestObject(2));
        list.add(4, new TestObject(4));
        list.add(3, to = new TestObject(3));
        list.add(1, new TestObject(1));
        list.remove(to);
        
        Iterator it = list.iterator();
        while (it.hasNext()) {
            TestObject o = (TestObject) it.next();
            sb.append(o.toString());
        }
        assertEquals("0124", sb.toString());
    }
}
