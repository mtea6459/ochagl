package com.googlecode.ochagl.graphics;

import java.io.IOException;
import java.util.Iterator;

import com.googlecode.ochagl.core.SortedList;



/**
 * 描画物マネージャー．画面上の全ての描画物を管理する．
 */
public final class DrawableManager {

    /**
     * テクスチャローダ。
     */
    private TextureLoader textureLoader_;

	/**
	 * ビューのリスト。
	 */
	private SortedList viewList_;

	/**
	 * デフォルトコンストラクタ．
	 */
	public DrawableManager()
	{
		viewList_ = new SortedList();
        textureLoader_ = ResourceFactory.createTextureLoader();
	}

	/**
	 * ビューをリストに追加．
	 *
	 * @param priority 優先
	 * @param view 追加するビュー
	 */
	public void addView(final int priority, final View view)
	{
		viewList_.add(priority, view);
	}

	/**
	 * ビューをリストから削除．
	 *
	 * @param view 削除するビュー
	 */
	public void removeView(final View view)
	{
		viewList_.remove(view);
	}

	/**
	 * 描画物を描画する．
	 *
	 * @param gcon 描画コンテキスト
	 */
	public void draw(final GraphicContext gc) 
	{
		Iterator it = viewList_.iterator();
		while (it.hasNext()) {
			View view = (View) it.next();
			if (view.isShow()) {
				view.begin(gc);
				view.render(gc);
				view.end(gc);
			}
		}

		// 描画ツリーから削除
		it = viewList_.iterator();
		while (it.hasNext()) {
			View view = (View) it.next();
			view.remove();
		}
	}

    /**
     * テクスチャを読み込む。
     *
     * @param gc グラフィックコンテキスト
     * @param fileName ファイル名
     *
     * @return テクスチャオブジェクト
     */
    public Texture loadTexture(final GraphicContext gc, final String fileName) 
    {
        Texture texture = null;

        try {
            texture = textureLoader_.getTexture(gc, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return texture;
    }
}
