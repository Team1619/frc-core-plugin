package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.outputs.numeric.Victor;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.utilities.Config;

import javax.annotation.Nullable;

/**
 * SimVictor extends Victor, and acts like victors in sim mode
 */

public class SimVictor extends Victor {

    @Nullable
    private Integer mMotor;
    private double mOutput = 0.0;

    public SimVictor(Object name, Config config, ObjectsDirectory objectsDirectory) {
        super(name, config);

        // Included to mimic RobotTalon for testing
        mMotor = (Integer) objectsDirectory.getHardwareObject(fDeviceNumber);
        if (mMotor == null) {
            mMotor = fDeviceNumber;
            objectsDirectory.setHardwareObject(fDeviceNumber, mMotor);
        }
    }

    @Override
    public void processFlag(String flag) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        mOutput = outputValue;
    }
}
