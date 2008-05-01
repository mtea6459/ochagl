package com.googlecode.ochagl.core;

import com.googlecode.ochagl.core.Queue;

import junit.framework.TestCase;


public class QueueTest extends TestCase {

	class TestObject {
		private int i_;
		public TestObject(int i) {
			i_ = i;
		}
		public String toString() {
			return String.valueOf(i_);
		}
	}

    public QueueTest() {
        super();
    }

    /**
     * 書き込み読み込みのテスト。
	 * FIFO が実現されていること。
     */
	public void testQueue() {

		StringBuffer sb = new StringBuffer();
		Queue queue = new Queue();

		queue.enqueue(new TestObject(0));
		queue.enqueue(new TestObject(1));
		queue.enqueue(new TestObject(2));
		queue.enqueue(new TestObject(3));
		queue.enqueue(new TestObject(4));
		
		TestObject o = null;
		while ((o = (TestObject) queue.dequeue()) != null) {
			sb.append(o.toString());
		}
		assertEquals("01234", sb.toString());

        sb = new StringBuffer();
		queue.enqueue(new TestObject(7));
		queue.enqueue(new TestObject(8));
		queue.enqueue(new TestObject(9));
		queue.enqueue(new TestObject(0));
		
		o = null;
		while ((o = (TestObject) queue.dequeue()) != null) {
			sb.append(o.toString());
		}
		assertEquals("7890", sb.toString());
    }
}
