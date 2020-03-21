package org.team1619.models.inputs.numeric.sim;

import org.team1619.models.inputs.numeric.AnalogSensor;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.utilities.Config;

public class SimAnalogSensor extends AnalogSensor {

    private final SimInputNumericListener fListener;

    public SimAnalogSensor(EventBus eventBus, Object name, Config config) {
        super(name, config);

        fListener = new SimInputNumericListener(eventBus, name);
    }

    @Override
    public double getVoltage() {
        return fListener.get();
    }

    public double getAccumulatorCount() {
        return fListener.get();
    }

    public double getAccumulatorValue() {
        return fListener.get();
    }

    public double getValue() {
        return fListener.get();
    }
}
