package org.team1619.models.inputs.bool;

import org.uacr.models.inputs.bool.InputBoolean;
import org.uacr.utilities.Config;

public abstract class DigitalSensor extends InputBoolean {

	protected int fId;
	private boolean fPreviousValue;
	private boolean fValue;

	public DigitalSensor(Object name, Config config) {
		super(name, config);

		fId = config.getInt("id");
	}

	public abstract boolean getDigitalInputValue();

	@Override
	public void initialize() {
		fValue = getDigitalInputValue();
	}

	@Override
	public void update() {
		fPreviousValue = fValue;
		fValue = getDigitalInputValue();
	}

	@Override
	public boolean get() {
		return fIsInverted != fValue;
	}

	@Override
	public DeltaType getDelta() {
		if (!fPreviousValue && fValue) {
			return DeltaType.RISING_EDGE;
		} else if (fPreviousValue && !fValue) {
			return DeltaType.FALLING_EDGE;
		} else {
			return DeltaType.NO_DELTA;
		}
	}

	@Override
	public void processFlag(String flag) {
	}
}
