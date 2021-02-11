package org.team1619.models.outputs.bool.sim;

import org.team1619.models.outputs.bool.SolenoidSingle;
import org.uacr.utilities.Config;

import java.util.Set;

public class SimSolenoidSingle extends SolenoidSingle {

    public SimSolenoidSingle(Object name, Config config) {
        super(name, config);
    }

    @Override
    public void processFlags(Set<String> flags) {

    }

    @Override
    public void setHardware(boolean output) {

    }
}
