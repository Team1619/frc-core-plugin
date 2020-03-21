package org.team1619.models.inputs.bool.robot;

import org.team1619.models.inputs.bool.DigitalSensor;
import org.uacr.utilities.Config;

public class RobotDigitalSensor extends DigitalSensor {

    private edu.wpi.first.wpilibj.DigitalInput fSensor;

    public RobotDigitalSensor(Object name, Config config) {
        super(name, config);

        fSensor = new edu.wpi.first.wpilibj.DigitalInput(fId);
    }

    @Override
    public boolean getDigitalInputValue() {
        return fSensor.get();
    }
}
