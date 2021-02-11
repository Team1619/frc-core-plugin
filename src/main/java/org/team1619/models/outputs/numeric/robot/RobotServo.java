package org.team1619.models.outputs.numeric.robot;

import edu.wpi.first.wpilibj.Servo;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

import java.util.Set;

/**
 * RobotServo extends Servo, and controls servo motors on the robot
 */

public class RobotServo extends org.team1619.models.outputs.numeric.Servo {

    private final Servo fServo;

    public RobotServo(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);
        fServo = hardwareFactory.get(Servo.class, fChannel);
    }

    @Override
    public void processFlags(Set<String> flags) {

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
