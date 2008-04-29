package com.googlecode.ochagl.input;

public interface InputAction {

	void update(final long loopCount);

	void reset();

	void release();

	boolean isPressed();

	void press();

	void press(int amount);
}