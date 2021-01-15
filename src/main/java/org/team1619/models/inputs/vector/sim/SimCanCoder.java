package org.team1619.models.inputs.vector.sim;

import org.team1619.models.inputs.vector.Encoder;
import org.uacr.utilities.Config;

import java.util.Map;

public class SimCanCoder extends Encoder {

    public SimCanCoder(Object name, Config config) {
        super(name, config);

    }

    @Override
    public void initialize() {
        
    }

    @Override
    protected Map<String, Double> readHardware() {
            return Map.of("position", 0.0, "velocity", 0.0);
    }

    @Override
    protected void zeroEncoder() {
    }
}
