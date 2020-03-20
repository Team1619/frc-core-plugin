package org.team1619.models.outputs.bool;

import org.uacr.models.outputs.bool.OutputBoolean;
import org.uacr.utilities.Config;

public abstract class SolenoidDouble extends OutputBoolean {

	protected final int fDeviceNumberMaster;
	protected final int fDeviceNumberSlave;

	public SolenoidDouble(Object name, Config config) {
		super(name, config);

		fDeviceNumberMaster = config.getInt("device_number_master");
		fDeviceNumberSlave = config.getInt("device_number_slave");
	}
}
