package com.googlecode.ochagl.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class SysUtils {

	private SysUtils() {}
	
	/**
	 * Creates an integer buffer to hold specified ints - strictly a utility
	 * method
	 * 
	 * @param size
	 *            how many int to contain
	 * @return created IntBuffer
	 */
	public static FloatBuffer createFloatBuffer(final int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());
		return temp.asFloatBuffer();
	}

	/**
	 * Creates an integer buffer to hold specified ints
	 * - strictly a utility method
	 *
	 * @param size how many int to contain
	 * @return created IntBuffer
	 */
	public static IntBuffer createIntBuffer(final int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asIntBuffer();
	}

}
