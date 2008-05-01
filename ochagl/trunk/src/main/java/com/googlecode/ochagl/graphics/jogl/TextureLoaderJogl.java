package com.googlecode.ochagl.graphics.jogl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.googlecode.ochagl.graphics.GraphicContext;
import com.googlecode.ochagl.graphics.Texture;
import com.googlecode.ochagl.graphics.TextureLoader;
import com.sun.opengl.util.texture.TextureIO;


/**
 * テクスチャローダのOpenGL実装。
 */
public class TextureLoaderJogl implements TextureLoader {

	/**
	 * テクスチャを保持する。
	 */
	private Map textures_ = new HashMap();

	/**
	 * コンストラクタ。
	 */
	public TextureLoaderJogl() {
	}

	/**
	 * テクスチャを読み込む。
	 *
	 * @param gc グラフィックコンテキスト
	 * @param resourceName リソース名
	 *
	 * @return テクスチャオブジェクト
	 *
	 * @throws IOException リソースが見つからない時に発生。
	 */
	public Texture getTexture(final GraphicContext gc, final String resourceName)
			throws IOException {

		Texture tex = (TextureJogl) textures_.get(resourceName);

		if (tex != null) {

			return tex;
		}
		ClassLoader classloader = getClass().getClassLoader();
		System.out.println("classLoader(TextureLoaderJogl):"+classloader);
		URL url = getClass().getClassLoader().getResource(resourceName);

		if (url == null) {
			throw new IOException("Cannot find: " + resourceName);
		}

		com.sun.opengl.util.texture.Texture jtex = TextureIO.newTexture(url,
				true, // mipmap
				null);// filesuffix
		tex = new TextureJogl(jtex);
		textures_.put(resourceName, tex);

		return tex;
	}

}
