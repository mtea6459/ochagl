package com.googlecode.ochagl.core;

import java.util.ArrayList;
import java.util.List;


/**
 * Queueクラス。 リングバッファで実装する。
 */
public class Queue {

    /**
     * リングバッファのサイズ。
     */
    private static final int SIZE = 256;

    /**
     * 次に読むべきバッファの位置。
     */
    private int read_;

    /**
     * 次に書くべきバッファの位置。
     */
    private int write_;

    /**
     * バッファコンテナ。
     */
    private List list_ = null;

    /**
     * コンストラクタ。
     */
    public Queue() {
        this(SIZE);
    }

    /**
     * コンストラクタ。
     *
     * @param size バッファサイズ
     */
    public Queue(final int size) {

        write_ = 0;
        read_ = 0;
        list_ = new ArrayList();

        for (int i = 0; i < size; i++) {

            list_.add(i, null);
        }
    }

    /**
     * 次のインデックスを得る。
     *
     * @param index インデックス
     *
     * @return 次のインデックス
     */
    private int next(final int index) {

        return (index + 1) % list_.size();
    }

    /**
     * キューに入れる。
     *
     * @param object 格納するオブジェクト
     *
     * @return 入れたオブジェクト。nullの場合は失敗
     */
    public Object enqueue(final Object object) {

        // バッファはすべて使用中
        // write_ ポインタが read_ ポインタに追いつくことはない
        if (next(write_) == read_) {

            return null;
        }

        list_.add(write_, object);
        write_ = next(write_);

        return object;
    }

    /**
     * キューから出す。
     *
     * @return 取り出したオブジェクト。nullの場合は失敗
     */
    public Object dequeue() {

        // リングバッファは空
        if (read_ == write_) {

            return null;
        }

        Object o = list_.get(read_);
        read_ = next(read_);

        return o;
    }
}
