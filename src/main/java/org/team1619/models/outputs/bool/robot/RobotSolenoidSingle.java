package org.team1619.models.outputs.bool.robot;

import org.team1619.models.outputs.bool.SolenoidSingle;
import org.uacr.utilities.Config;


public class RobotSolenoidSingle extends SolenoidSingle {

	private final edu.wpi.first.wpilibj.Solenoid fWpiSolenoid;

	public RobotSolenoidSingle(Object name, Config config) {
		super(name, config);

		fWpiSolenoid = new edu.wpi.first.wpilibj.Solenoid(fDeviceNumber);
	}

	@Override
	public void processFlag(String flag) {

	}

	@Override
	public void setHardware(boolean output) {
		fWpiSolenoid.set(output);
	}
}