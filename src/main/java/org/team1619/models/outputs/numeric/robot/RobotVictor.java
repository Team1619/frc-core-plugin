package org.team1619.models.outputs.numeric.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.team1619.models.outputs.numeric.Victor;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.utilities.Config;

import javax.annotation.Nullable;

/**
 * RobotVictor extends Victor, and controls victor motor controllers on the robot
 */

public class RobotVictor extends Victor {

    private final VictorSPX fMotor;
    private ObjectsDirectory fSharedObjectsDirectory;

    public RobotVictor(Object name, Config config, ObjectsDirectory objectsDirectory) {
        super(name, config);
        fSharedObjectsDirectory = objectsDirectory;
        // Only create one WPILib VictorSPX class per physical victor (device number). Otherwise, the physical victor gets confused getting instructions from multiple VictorSPX classes
        @Nullable
        Object motorObject = fSharedObjectsDirectory.getHardwareObject(fDeviceNumber);
        if (motorObject == null) {
            fMotor = new VictorSPX(fDeviceNumber);
            fSharedObjectsDirectory.setHardwareObject(fDeviceNumber, fMotor);
        } else {
            fMotor = (VictorSPX) motorObject;
        }

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
                @Nullable
                Object followerMotor = fSharedObjectsDirectory.getHardwareObject((int) outputValue);
                if (followerMotor != null) {
                    fMotor.follow((IMotorController) followerMotor, FollowerType.PercentOutput);
                }
                break;
            default:
                throw new RuntimeException("No output type " + outputType + " for VictorSPX");
        }
    }
}
