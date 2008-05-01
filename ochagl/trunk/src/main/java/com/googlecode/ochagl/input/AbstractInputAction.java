package com.googlecode.ochagl.input;


public abstract class AbstractInputAction implements InputAction {
	protected int amount;

	protected int amountOld;

	protected boolean isPress;

	protected boolean isGet;
	
	protected boolean isReleased;
	
	protected long loopCount_ = 0;

	public AbstractInputAction() {
		reset();
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#update()
	 */
	public synchronized void update(final long loopCount) {
		this.isPress = false;
		loopCount_ = loopCount;
		execute();
		amountOld = amount;
		if (isReleased) {
			isReleased = false;
			amount = 0;
		}
		isGet = false;
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#reset()
	 */
	public synchronized void reset() {
		amount = 0;
		amountOld = 0;
		isGet = false;
		isPress = false;
		isReleased = false;
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#isPressed()
	 */
	public synchronized boolean isPressed() {
		return isPress;
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#release()
	 */
	public synchronized void release() {
		isReleased = true;
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#press()
	 */
	public synchronized void press(int amount) {
		if (!isGet) {
			isGet = true;
			this.amount = 1;
		}
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#press()
	 */
	public synchronized void press() {
		press(1);
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#execute()
	 */
	public abstract void execute();
}
