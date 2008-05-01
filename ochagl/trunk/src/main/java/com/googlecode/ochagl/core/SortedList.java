package com.googlecode.ochagl.core;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * ソートされたリスト実現するクラス。 同じ優先順位を持つ場合は、登録順にソートされる。 List
 * インタフェイスは実装すべきメソッドが多すぎるのでとりあえず保留。
 */
public class SortedList {

    /**
     * リストの先頭ノード。
     */
    private Node head_ = null;

    /**
     * リストの最後尾ノード。
     */
    private Node tail_ = null;

    /**
     * デフォルトコンストラクタ。
     */
    public SortedList() {

        head_ = new Node(0, null);
        tail_ = new Node(Integer.MAX_VALUE, null);

        head_.prev_ = null;
        head_.next_ = tail_;
        tail_.prev_ = head_;
        tail_.next_ = null;
    }

    /**
     * オブジェクトをリストに追加する。
     *
     * @param priority 優先順位
     * @param object 追加するオブジェクト
     */
    public void add(final int priority, final Object object) {

        for (Node n = head_; n != null; n = n.next_) {

            if (priority < n.priority_) {

                Node node = new Node(priority, object);

                node.prev_ = n.prev_;
                node.next_ = n;

                n.prev_.next_ = node;
                n.prev_ = node;

                break;
            }
        }
    }

    /**
     * ノードをリストから外す。
     *
     * @param object 外すオブジェクト
     */
    public void remove(final Object object) {

        for (Node n = head_; n != null; n = n.next_) {

            if (object == n.object_) {

                n.next_.prev_ = n.prev_;
                n.prev_.next_ = n.next_;

                break;
            }
        }
    }

    /**
     * イテレータを取得する。
     *
     * @return イテレータ実体
     */
    public Iterator iterator() {

        return new SortedListItarator(head_);
    }

    /**
     * リストのノードを表すクラス。
     */
    private static class Node {

        /**
         * ノードの優先順位（リストに挿入する際の位置）。
         */
        private int priority_;

        /**
         * ノードが保持するオブジェクト。
         */
        private Object object_;

        /**
         * 前方ノード。
         */
        private Node prev_;

        /**
         * 後方ノード。
         */
        private Node next_;

        /**
         * コンストラクタ。挿入したい位置を指定し、ノードにぶら下げるオブジェクトも設定する。
         *
         * @param priority 挿入する位置
         * @param object ぶら下げたいオブジェクト
         */
        Node(final int priority, final Object object) {

            priority_ = priority;
            object_ = object;
        }
    }



    /**
     * イテレータの実装。remove()はサポートしない。
     */
    private static class SortedListItarator implements Iterator {

        /**
         * イテレートする際の先頭ノード。
         */
        private Node node_ = null;

        /**
         * イテレートの際のカレントのオブジェクト。
         */
        private Object nextObject_ = null;

        /**
         * コンストラクタ。
         *
         * @param node イテレートする際の先頭ノード
         */
        SortedListItarator(final Node node) {

            node_ = node;
        }

        /**
         * hasNextはnextの前に複数回呼び出されても動作しなければならない。 要素を進める処理はここに記述する。
         *
         * @return true:オブジェクトあり false:オブジェクトなし
         */
        public boolean hasNext() {

            if (nextObject_ != null) {

                return true;
            }

            node_ = node_.next_;
            nextObject_ = node_.object_;

            return nextObject_ != null;
        }

        /**
         * ユーザがnextしか呼び出さない場合でも 正しく動作し、最後にはNoSuchElementsException
         * をスローしなくてはならない。 要素を進める処理はhasNextで行なわれる。
         *
         * @return コンテナに格納されるオブジェクト
         */
        public Object next() {

            if ((nextObject_ == null) && !hasNext()) {

                throw new NoSuchElementException();
            }

            Object o = nextObject_;
            nextObject_ = null;

            return o;
        }

        /**
         * 実装をサポートしない。
         */
        public void remove() {

            throw new UnsupportedOperationException();
        }
    }
}
