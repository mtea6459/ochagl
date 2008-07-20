package com.googlecode.ochagl.input;


public class NormalInputAction extends AbstractInputAction {

	public NormalInputAction() {
		super();
	}

	/* (”ñ Javadoc)
	 * @see om.input.InputAction#execute()
	 */
	public void execute() {
		if (amount == 1) {
			//System.out.println("RAW:" + amount);
			this.isPress = true;
		}
	}
}
