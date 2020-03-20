package org.team1619.models.inputs.bool.robot;

import edu.wpi.first.wpilibj.Joystick;
import org.team1619.models.inputs.bool.Button;
import org.uacr.utilities.Config;

public class RobotJoystickButton extends Button {

	private final Joystick fJoystick;

	public RobotJoystickButton(Object name, Config config) {
		super(name, config);
		fJoystick = new Joystick(fPort);
	}

	@Override
	public boolean isPressed() {
		return fJoystick.getRawButton(Integer.valueOf(fButton));
	}
}
