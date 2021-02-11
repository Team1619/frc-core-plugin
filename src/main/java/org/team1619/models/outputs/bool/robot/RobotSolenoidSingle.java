package org.team1619.models.outputs.bool.robot;

import edu.wpi.first.wpilibj.Solenoid;
import org.team1619.models.outputs.bool.SolenoidSingle;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

import java.util.Set;

public class RobotSolenoidSingle extends SolenoidSingle {

    private final Solenoid fWpiSolenoid;

    public RobotSolenoidSingle(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fWpiSolenoid = hardwareFactory.get(Solenoid.class, fDeviceNumber);
    }



    @Override
    public void setHardware(boolean output) {
        fWpiSolenoid.set(output);
    }

    @Override
    public void processFlags(Set<String> flag) {

    }
}