package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.outputs.numeric.Victor;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.utilities.Config;

import javax.annotation.Nullable;

/**
 * SimVictor extends Victor, and acts like victors in sim mode
 */

public class SimVictor extends Victor {

	private double fOutput = 0.0;
	@Nullable
	private Integer fMotor;

	public SimVictor(Object name, Config config, ObjectsDirectory objectsDirectory) {
		super(name, config);

		// Included to mimic RobotTalon for testing
		fMotor = (Integer) objectsDirectory.getHardwareObject(fDeviceNumber);
		if (fMotor == null) {
			fMotor = fDeviceNumber;
			objectsDirectory.setHardwareObject(fDeviceNumber, fMotor);
		}
	}

	@Override
	public void processFlag(String flag) {
	}

	@Override
	public void setHardware(String outputType, double outputValue, String profile) {
		fOutput = outputValue;
	}
}
