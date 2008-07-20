/*
 * 作成日： 2004/09/20
 *
 * TODO この生成されたファイルのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
package com.googlecode.ochagl.core;

import java.util.Iterator;

import com.googlecode.ochagl.core.Tree;
import com.googlecode.ochagl.core.TreeImpl;

import junit.framework.TestCase;

/**
 * @author ocha
 *
 * 
 * 
 */
public class TreeTest extends TestCase {

	class TestObject extends TreeImpl {
		private int i_;
        public TestObject(int i, String name) {
            super(name);
            i_ = i;
        }
		public TestObject(int i) {
			this(i, null);
		}
		public String toString() {
			return String.valueOf(i_);
		}
	}

    /**
     * 
     */
    public TreeTest() {
        super();
    }

    /**
     * 追加のテスト
     */
    public void testAdd() {
		StringBuffer sb = new StringBuffer();
		TestObject tree = new TestObject(0);
		tree.add(new TestObject(1));
        tree.add(new TestObject(2));
        tree.add(new TestObject(3));
        tree.add(new TestObject(4));
		
		Iterator it = tree.iterator();
		while (it.hasNext()) {
			TestObject o = (TestObject) it.next();
			sb.append(o.toString());
		}
		assertEquals("01234", sb.toString());
    }

    /**
     * 追加のテスト
     */
    public void testAddTo() {
        StringBuffer sb = new StringBuffer();
        TestObject tree0 = new TestObject(0);
        TestObject tree1 = new TestObject(1);
        TestObject tree2 = new TestObject(2);
        TestObject tree3 = new TestObject(3);
        tree2.addTo(tree0);
        tree1.addTo(tree0);
        tree3.addTo(tree0);
        
        Iterator it = tree0.iterator();
        while (it.hasNext()) {
            TestObject o = (TestObject) it.next();
            sb.append(o.toString());
        }
        assertEquals("0213", sb.toString());
    }

    /**
     * 追加のテスト
     * 二重にaddToしたら最後に登録したものが有効になること
     */
    public void testAdd2() {
        StringBuffer sb = new StringBuffer();
        TestObject tree0 = new TestObject(0);
        TestObject tree1 = new TestObject(1);
        TestObject tree2 = new TestObject(2);
        TestObject tree3 = new TestObject(3);
        tree1.addTo(tree0);
        tree2.addTo(tree1);
        tree2.addTo(tree3);
        tree3.addTo(tree0);
        
        Iterator it = tree0.iterator();
        while (it.hasNext()) {
            TestObject o = (TestObject) it.next();
            sb.append(o.toString());
        }
        assertEquals("0132", sb.toString());
    }

    /**
     * 名前取得のテスト
     */
    public void testName() {
        StringBuffer sb = new StringBuffer();
        TestObject tree0 = new TestObject(0, "0");
        TestObject tree1 = new TestObject(1, "1");
        TestObject tree2 = new TestObject(2, "2");
        TestObject tree3 = new TestObject(3, "3");
        tree2.addTo(tree0);
        tree1.addTo(tree0);
        tree3.addTo(tree0);
        
        Tree node = tree0.getNode("3");
        assertEquals(tree3, node);
    }

    /**
	 * 削除のテスト
     */
    public void testRemoveRange() {
        StringBuffer sb = new StringBuffer();
        TestObject tree0 = new TestObject(0);
        TestObject tree1 = new TestObject(1);
        TestObject tree2 = new TestObject(2);
        TestObject tree3 = new TestObject(3);
        tree1.addTo(tree0);
        tree2.addTo(tree0);
        tree3.addTo(tree0);
        tree2.remove();
        Iterator it = tree0.iterator();
        while (it.hasNext()) {
            TestObject o = (TestObject) it.next();
            sb.append(o.toString());
        }
        assertEquals("013", sb.toString());
    }
}
