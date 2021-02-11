package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.outputs.numeric.Victor;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.utilities.Config;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * SimVictor extends Victor, and acts like victors in sim mode
 */

public class SimVictor extends Victor {

    private final Integer fMotor;

    private double mOutput = 0.0;

    public SimVictor(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        // Included to mimic RobotTalon for testing
        fMotor = hardwareFactory.get(Integer.class, fDeviceNumber);
    }

    @Override
    public void processFlags(Set<String> flags) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        mOutput = outputValue;
    }
}
