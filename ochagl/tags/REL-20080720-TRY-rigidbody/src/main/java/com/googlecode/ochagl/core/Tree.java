package com.googlecode.ochagl.core;

import java.util.Iterator;


/**
 * ツリー構造を表現するインタフェイス。
 */
public interface Tree {

    /**
     * 親ツリーを設定する。
     *
     * @param tree 親ツリー
     */
    void setParent(Tree tree);

    /**
     * 兄ツリーを設定する。
     *
     * @param tree 兄ツリー
     */
    void setPrev(Tree tree);

    /**
     * 弟ツリーを設定する。
     *
     * @param tree 弟ツリー
     */
    void setNext(Tree tree);

    /**
     * 子供ツリーを設定する。
     *
     * @param tree 子供ツリー
     */
    void setChild(Tree tree);

    /**
     * 親ツリーを取得する。
     *
     * @return 親ツリー
     */
    Tree getParent();

    /**
     * 兄ツリーを取得する。
     *
     * @return 兄ツリー
     */
    Tree getPrev();

    /**
     * 弟ツリーを取得する。
     *
     * @return 弟ツリー
     */
    Tree getNext();

    /**
     * 子供ツリーを取得する。
     *
     * @return 子供ツリー
     */
    Tree getChild();

    /**
     * ノード（自身）を取得する。
     *
     * @param name 取得したいノード名
     *
     * @return ノード
     */
    Tree getNode(String name);

    /**
     * ノード名を設定する。
     *
     * @param name ノード名
     */
    void setName(String name);

    /**
     * ノード名を取得する。
     *
     * @return ノード名
     */
    String getName();

    /**
     * 指定したツリーを自分の子供としてぶら下げる。
     *
     * @param tree ぶら下げたい子供ツリー
     */
    void add(Tree tree);

    /**
     * 指定したツリーの子供としてぶら下げる。
     *
     * @param tree ぶら下げたい親ツリー
     */
    void addTo(Tree tree);

    /**
     * 自分を親ツリーから切り離す。
     */
    void remove();

    /**
     * 子供ツリーの数を取得する。
     *
     * @return 子供ツリーの数
     */
    int countChild();

    /**
     * 自分以下の階層数を取得する。
     *
     * @return 階層数
     */
    int countHierarchy();

    /**
     * イテレータを取得する。
     *
     * @return イテレータ実体
     */
    Iterator iterator();
}
