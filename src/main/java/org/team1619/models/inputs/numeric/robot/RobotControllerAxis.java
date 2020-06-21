package org.team1619.models.inputs.numeric.robot;

import edu.wpi.first.wpilibj.XboxController;
import org.team1619.models.inputs.numeric.Axis;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

public class RobotControllerAxis extends Axis {

    private final XboxController fController;

    public RobotControllerAxis(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fController = hardwareFactory.get(XboxController.class, fPort);
    }

    @Override
    public double getAxis() {
        return fController.getRawAxis(fAxis);
    }
}
