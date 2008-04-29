package com.googlecode.ochagl.input;


public class TriggerInputAction extends AbstractInputAction implements InputAction {

	public TriggerInputAction() {
		super();
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#execute()
	 */
	public void execute() {
		if (amountOld == 0 && amount == 1) {
			this.isPress = true;
		}
	}
}
