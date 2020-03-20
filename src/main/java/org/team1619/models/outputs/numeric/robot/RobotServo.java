package org.team1619.models.outputs.numeric.robot;

import edu.wpi.first.wpilibj.Servo;
import org.uacr.utilities.Config;

/**
 * RobotServo extends Servo, and controls servo motors on the robot
 */

public class RobotServo extends org.team1619.models.outputs.numeric.Servo {

	private final Servo fServo;

	public RobotServo(Object name, Config config) {
		super(name, config);
		fServo = new Servo(fChannel);
	}

	@Override
	public void processFlag(String flag) {
	}

	@Override
	public void setHardware(String outputType, double outputValue, String profile) {
		if (outputValue < 0) {
			fServo.setDisabled();
		} else {
			fServo.set(outputValue);
		}
	}

	public double getSetpoint() {
		return fServo.getPosition();
	}
}
