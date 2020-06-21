package org.team1619.models.inputs.numeric.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import org.team1619.models.inputs.numeric.AnalogSensor;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

public class RobotAnalogSensor extends AnalogSensor {

    private final AnalogInput fAnalogLogInput;

    public RobotAnalogSensor(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fAnalogLogInput = hardwareFactory.get(AnalogInput.class, fPort);
        fAnalogLogInput.resetAccumulator();
    }

    @Override
    public double getVoltage() {
        return fAnalogLogInput.getVoltage();
    }

    public double getAccumulatorCount() {
        return fAnalogLogInput.getAccumulatorCount();
    }

    public double getAccumulatorValue() {
        return fAnalogLogInput.getAccumulatorValue();
    }

    public double getValue() {
        return fAnalogLogInput.getValue();
    }
}
