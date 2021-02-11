package org.team1619.models.outputs.bool.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team1619.models.outputs.bool.SolenoidDouble;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

import java.util.Set;

public class RobotSolenoidDouble extends SolenoidDouble {

    private final DoubleSolenoid fWpiSolenoid;

    public RobotSolenoidDouble(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fWpiSolenoid = hardwareFactory.get(DoubleSolenoid.class, fDeviceNumberMaster, fDeviceNumberSlave);
        fWpiSolenoid.set(DoubleSolenoid.Value.kOff);
    }



    @Override
    public void setHardware(boolean output) {
        if (output) {
            fWpiSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            fWpiSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }

    @Override
    public void processFlags(Set<String> flag) {

    }
}