package org.team1619.models.inputs.numeric.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.team1619.models.inputs.numeric.Axis;
import org.uacr.utilities.Config;

public class RobotJoystickAxis extends Axis {

	private Joystick fJoystick;

	public RobotJoystickAxis(Object name, Config config) {
		super(name, config);
		fJoystick = new Joystick(fPort);
	}

	@Override
	public double getAxis() {
		return fJoystick.getRawAxis(fAxis);
	}
}
