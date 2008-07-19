package com.googlecode.ochagl.input;


// TODO ループカウントベースに変更する
public class RepeatInputAction extends AbstractInputAction {

	private static final int STATE_NONE = 0;

	private static final int STATE_WAITING_FOR_REPEAT = 1;

	private static final int STATE_REPEATING = 2;

	private static final int REPEAT_WAIT_TIME = 10;

	private static final int REPEAT_INTERVAL_TIME = 5;

	private int state;

	private long starttime;

	private int repeatWaitTime;

	private int repeatIntervalTime;

	public RepeatInputAction() {
		super();
		repeatWaitTime = REPEAT_WAIT_TIME;
		repeatIntervalTime = REPEAT_INTERVAL_TIME;
	}

	/* (非 Javadoc)
	 * @see om.input.InputAction#execute()
	 */
	public void execute() {
		long nowtime = loopCount_;
		long difftime = nowtime - starttime;
		if (amount == 1) {
			switch (state) {
			case STATE_NONE:
				starttime = nowtime;
				state = STATE_WAITING_FOR_REPEAT;
				this.isPress = true;
				// System.out.println("STATE_RELEASED");
				break;

			case STATE_WAITING_FOR_REPEAT:
				// System.out.println("STATE_WAITING_REPEATING:" +
				// difftime);
				if (difftime >= repeatWaitTime) {
					state = STATE_REPEATING;
					starttime = nowtime;
				}
				break;

			case STATE_REPEATING:
				if (difftime >= repeatIntervalTime) {
					this.isPress = true;
					starttime = nowtime;
				} else {
					this.isPress = false;
				}
				//System.out.println("STATE_REPEATING:" + amount);
				break;
			}
		} else {
			state = STATE_NONE;
		}
	}

	public int getRepeatIntervalTime() {
		return repeatIntervalTime;
	}

	public void setRepeatIntervalTime(int repeatIntervalTime) {
		this.repeatIntervalTime = repeatIntervalTime;
	}

	public int getRepeatWaitTime() {
		return repeatWaitTime;
	}

	public void setRepeatWaitTime(int repeatWaitTime) {
		this.repeatWaitTime = repeatWaitTime;
	}

}
