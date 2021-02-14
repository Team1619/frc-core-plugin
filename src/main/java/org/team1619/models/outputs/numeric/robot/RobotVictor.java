package org.team1619.models.outputs.numeric.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.team1619.models.outputs.numeric.Victor;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;

/**
 * RobotVictor extends Victor, and controls victor motor controllers on the robot
 */

public class RobotVictor extends Victor {

    private final HardwareFactory fHardwareFactory;
    private final VictorSPX fMotor;

    public RobotVictor(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);
        fHardwareFactory = hardwareFactory;

        fMotor = fHardwareFactory.get(VictorSPX.class, fDeviceNumber);

        fMotor.setInverted(fIsInverted);
        fMotor.setNeutralMode(fIsBrakeModeEnabled ? NeutralMode.Brake : NeutralMode.Coast);
    }

    @Override
    public void processFlag(String flag) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        switch (outputType) {
            case "percent":
                fMotor.set(ControlMode.PercentOutput, outputValue);
                break;
            case "follower":
                fMotor.follow(fHardwareFactory.get(TalonSRX.class, (int) outputValue), FollowerType.PercentOutput);
                break;
            default:
                throw new RuntimeException("No output type " + outputType + " for VictorSPX");
        }
    }
}
