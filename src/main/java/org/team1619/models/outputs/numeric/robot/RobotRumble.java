package org.team1619.models.outputs.numeric.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import org.team1619.models.outputs.numeric.Rumble;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

/**
 * RobotRumble extends Rumble, and controls xbox controller rumble motors when running on the robot
 */

public class RobotRumble extends Rumble {

    private final XboxController fRumble;

    private double fAdjustedOutput;

    public RobotRumble(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fRumble = hardwareFactory.get(XboxController.class, fPort);

        fAdjustedOutput = 0.0;
    }

    @Override
    public void processFlag(String flag) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        fAdjustedOutput = outputValue;
        if (fRumbleSide.equals("right")) {
            fRumble.setRumble(GenericHID.RumbleType.kRightRumble, fAdjustedOutput);
        } else if (fRumbleSide.equals("left")) {
            fRumble.setRumble(GenericHID.RumbleType.kLeftRumble, fAdjustedOutput);
        }
    }
}
