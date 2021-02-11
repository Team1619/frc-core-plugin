package org.team1619.models.outputs.bool.sim;

import org.team1619.models.outputs.bool.SolenoidDouble;
import org.uacr.utilities.Config;

import java.util.Set;

public class SimSolenoidDouble extends SolenoidDouble {

    public SimSolenoidDouble(Object name, Config config) {
        super(name, config);
    }

    @Override
    public void setHardware(boolean output) {

    }

    @Override
    public void processFlags(Set<String> flag) {

    }
}
