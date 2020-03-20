package org.team1619.models.outputs.bool;

import org.uacr.models.outputs.bool.OutputBoolean;
import org.uacr.utilities.Config;

public abstract class SolenoidSingle extends OutputBoolean {

	protected final int fDeviceNumber;

	public SolenoidSingle(Object name, Config config) {
		super(name, config);

		fDeviceNumber = config.getInt("device_number");
	}
}
