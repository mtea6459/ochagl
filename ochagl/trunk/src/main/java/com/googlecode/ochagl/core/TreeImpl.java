package com.googlecode.ochagl.core;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * ツリーを表現するクラス。 タスクツリーやレンダリングツリーに利用する。
 */
public class TreeImpl implements Tree {

    /**
     * 親ツリー。
     */
    private Tree parent_ = null;

    /**
     * 兄ツリー。
     */
    private Tree prev_ = null;

    /**
     * 弟ツリー。
     */
    private Tree next_ = null;

    /**
     * 子ツリー。
     */
    private Tree child_ = null;

    /**
     * ツリーの名前。
     */
    private String name_ = null;

    /**
     * コンストラクタ。
     */
    public TreeImpl() {
        this(null);
    }

    /**
     * ツリーの名前を指定するコンストラクタ。
     *
     * @param name ツリーの名前
     */
    public TreeImpl(final String name) {

        setName(name);
    }

    /**
     * 親ツリーを設定する。
     *
     * @param tree 親ツリー
     */
    public void setParent(final Tree tree) {

        parent_ = tree;
    }

    /**
     * 兄ツリーを設定する。
     *
     * @param tree 兄ツリー
     */
    public void setPrev(final Tree tree) {

        prev_ = tree;
    }

    /**
     * 弟ツリーを設定する。
     *
     * @param tree 弟ツリー
     */
    public void setNext(final Tree tree) {

        next_ = tree;
    }

    /**
     * 子供ツリーを設定する。
     *
     * @param tree 子供ツリー
     */
    public void setChild(final Tree tree) {

        child_ = tree;
    }

    /**
     * 親ツリーを取得する。
     *
     * @return 親ツリー
     */
    public Tree getParent() {

        return parent_;
    }

    /**
     * 兄ツリーを取得する。
     *
     * @return 兄ツリー
     */
    public Tree getPrev() {

        return prev_;
    }

    /**
     * 弟ツリーを取得する。
     *
     * @return 弟ツリー
     */
    public Tree getNext() {

        return next_;
    }

    /**
     * 子供ツリーを取得する。
     *
     * @return 子供ツリー
     */
    public Tree getChild() {

        return child_;
    }

    /**
     * ノード（自身）を取得する。
     *
     * @param name 取得したいノード名
     *
     * @return ノード
     */
    public Tree getNode(final String name) {
        Tree node = null;
        Iterator it = iterator();

        while (it.hasNext()) {
            Tree t = (Tree) it.next();
            String n = t.getName();

            if ((n != null) && n.equals(name)) {
                node = t;
                break;
            }
        }

        return node;
    }

    /**
     * ノード名を設定する。
     *
     * @param name ノード名
     */
    public void setName(final String name) {

        name_ = name;
    }

    /**
     * ノード名を取得する。
     *
     * @return ノード名
     */
    public String getName() {

        return name_;
    }

    /**
     * 指定したツリーを自分の子供としてぶら下げる。
     *
     * @param tree ぶら下げたい子供ツリー
     *
     * @see com.googlecode.ochagl.core.Tree#add(com.googlecode.ochagl.core.Tree)
     */
    public void add(final Tree tree) {

        if (tree != null) {

            tree.remove();

            if (getChild() == null) {

                setChild(tree);
                tree.setParent(this);
            } else {

                Tree curr = getChild();

                while (curr != null) {

                    if (curr.getNext() == null) {

                        tree.setPrev(curr);
                        curr.setNext(tree);
                        tree.setParent(this);

                        break;
                    }

                    curr = curr.getNext();
                }
            }
        }
    }

    /**
     * 指定したツリーの子供としてぶら下げる。
     *
     * @param tree ぶら下げたい親ツリー
     */
    public void addTo(final Tree tree) {

        if (tree != null) {

            tree.add(this);
        }
    }

    /**
     * 自分を親ツリーから切り離す。
     */
    public void remove() {

        if (getParent() != null) {

            if (getParent().getChild() == this) {

                // 最初の子供
                getParent().setChild(this.getNext());

                if (this.getNext() != null) {

                    // 次の兄弟がいれば、それを最初の子供にする
                    this.getNext().setPrev(null);
                }
            } else {

                // ２番目以降の子供の処理
                if (this.getNext() != null) {

                    this.getNext().setPrev(this.getPrev());
                }

                if (this.getPrev() != null) {

                    this.getPrev().setNext(this.getNext());
                }
            }

            this.parent_ = null;
        }

        setNext(null);
        setPrev(null);
    }

    /**
     * 子供ツリーの数を取得する。
     *
     * @return 子供ツリーの数
     */
    public int countChild() {

        int cc = 0;

        if (getChild() != null) {

            Tree curr = getChild();

            while (curr != null) {

                cc++;
                curr = curr.getNext();
            }
        }

        return cc;
    }

    /**
     * 自分以下の階層数を取得する。
     *
     * @return 階層数
     */
    public int countHierarchy() {

        int cc = 0;
        Tree curr;

        if (getChild() != null) {

            curr = getChild();

            while (curr != null) {

                cc++; // for myself.
                cc += curr.countHierarchy();
                curr = curr.getNext();
            }
        }

        return cc;
    }

    /**
     * イテレータを取得する。
     *
     * @return イテレータ実体
     */
    public Iterator iterator() {

        return new TreeItarator(this);
    }

    /**
     * イテレータの実装。remove()はサポートしない。
     */
    private static class TreeItarator implements Iterator {

        /**
         * イテレート中のカレントツリー。
         */
        private Tree curr = null;

        /**
         * イテレートする際の最初のツリー。
         */
        private Tree top = null;

        /**
         * DOCUMENT ME!
         */
        private Tree nextObject_ = null;

        /**
         * コンストラクタ。
         *
         * @param node イテレートする際の先頭ノード
         */
        TreeItarator(final Tree node) {

            curr = node;
            top = node;
            nextObject_ = node;
        }

        /**
         * hasNextはnextの前に複数回呼び出されても動作しなければならない。要素を進める処理はここに記述する。
         *
         * @return true:オブジェクトあり false:オブジェクトなし
         */
        public boolean hasNext() {

            if (nextObject_ != null) {

                return true;
            }

            if (curr.getChild() != null) {

                curr = curr.getChild();
            } else if (curr == top) {

                curr = null;
            } else if (curr.getNext() != null) {

                curr = curr.getNext();
            } else {

                while (true) {

                    curr = curr.getParent();

                    if (curr == null) {

                        break;
                    }

                    if (curr == top) {

                        curr = null;

                        break;
                    }

                    if (curr.getNext() != null) {

                        curr = curr.getNext();

                        break;
                    }
                }
            }

            nextObject_ = curr;

            return nextObject_ != null;
        }

        /**
         * ユーザがnextしか呼び出さない場合でも正しく動作し、最後にはNoSuchElementsException
         * をスローしなくてはならない。 要素を進める処理はhasNextで行なわれる。
         *
         * @return ツリーオブジェクト
         *
         * @throws NoSuchElementException DOCUMENT ME!
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
         *
         * @throws UnsupportedOperationException DOCUMENT ME!
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
