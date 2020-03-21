package org.team1619.models.inputs.numeric.robot;

import edu.wpi.first.wpilibj.XboxController;
import org.team1619.models.inputs.numeric.Axis;
import org.uacr.utilities.Config;

public class RobotControllerAxis extends Axis {

    private final XboxController fController;

    public RobotControllerAxis(Object name, Config config) {
        super(name, config);
        fController = new XboxController(fPort);
    }

    @Override
    public double getAxis() {
        return fController.getRawAxis(fAxis);
    }
}
