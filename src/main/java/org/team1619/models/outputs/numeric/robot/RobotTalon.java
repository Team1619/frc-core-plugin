package org.team1619.models.outputs.numeric.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.team1619.models.outputs.numeric.Talon;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * RobotTalon extends Talon, and controls talon motor controllers on the robot
 */

public class RobotTalon extends Talon {

    private static final int CAN_TIMEOUT_MILLISECONDS = 10;

    private final HardwareFactory fHardwareFactory;
    private final PowerDistributionPanel fPDP;
    private final TalonSRX fMotor;
    private final Map<String, Map<String, Double>> fProfiles;

    private String mCurrentProfileName = "none";

    public RobotTalon(Object name, Config config, HardwareFactory hardwareFactory, InputValues inputValues) {
        super(name, config, inputValues);

        fHardwareFactory = hardwareFactory;

        fPDP = fHardwareFactory.get(PowerDistributionPanel.class);
        fMotor = fHardwareFactory.get(TalonSRX.class, fDeviceNumber);

        fMotor.configFactoryDefault(CAN_TIMEOUT_MILLISECONDS);

        if (!(config.get("profiles", new HashMap<>()) instanceof Map)) throw new RuntimeException();
        fProfiles = (Map<String, Map<String, Double>>) config.get("profiles", new HashMap<>());

        fMotor.setInverted(fIsInverted);
        fMotor.setNeutralMode(fIsBrakeModeEnabled ? NeutralMode.Brake : NeutralMode.Coast);

        fMotor.enableCurrentLimit(fCurrentLimitEnabled);
        fMotor.configContinuousCurrentLimit(fContinuousCurrentLimitAmps, CAN_TIMEOUT_MILLISECONDS);
        fMotor.configPeakCurrentLimit(fPeakCurrentLimitAmps, CAN_TIMEOUT_MILLISECONDS);
        fMotor.configPeakCurrentDuration(fPeakCurrentDurationMilliseconds, CAN_TIMEOUT_MILLISECONDS);

        fMotor.setSensorPhase(fSensorInverted);

        switch (fFeedbackDevice) {
            case "quad_encoder":
                fMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, CAN_TIMEOUT_MILLISECONDS);
                break;
            case "internal_encoder":
                fMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, CAN_TIMEOUT_MILLISECONDS);
                break;
            case "remote_talon":
                fMotor.configRemoteFeedbackFilter(config.getInt("encoder_number"), RemoteSensorSource.TalonSRX_SelectedSensor, 0, CAN_TIMEOUT_MILLISECONDS);
                fMotor.configSelectedFeedbackSensor(RemoteFeedbackDevice.RemoteSensor0, 0, CAN_TIMEOUT_MILLISECONDS);
                break;
            default:
                break;
        }
    }

    @Override
    public void processFlag(String flag) {
        if (flag.equals("zero")) {
            zeroSensor();
        }
    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {

        if (fReadPosition) {
            readEncoderPosition();
        }
        if (fReadVelocity) {
            readEncoderVelocity();
        }
        if (fReadCurrent) {
            readMotorCurrent();
        }
        if (fReadTemperature) {
            readMotorTemperature();
        }

        switch (outputType) {
            case "percent":

                fMotor.set(ControlMode.PercentOutput, outputValue * fPercentScalar);
                break;
            case "follower":
                fMotor.follow(fHardwareFactory.get(TalonSRX.class, (int) outputValue), FollowerType.PercentOutput);
                break;
            case "velocity":

                if (profile.equals("none")) {
                    throw new RuntimeException("PIDF Profile name must be specified");
                }

                if (!profile.equals(mCurrentProfileName)) {
                    setProfile(profile);
                }

                fMotor.set(ControlMode.Velocity, (outputValue / fVelocityScalar) / 10);
                break;
            case "position":
                if (profile.equals("none")) {
                    throw new RuntimeException("PIDF Profile name must be specified");
                }

                if (!profile.equals(mCurrentProfileName)) {
                    setProfile(profile);
                }

                fMotor.set(ControlMode.Position, outputValue / fPositionScalar);
                break;
            case "motion_magic":
                if (profile.equals("none")) {
                    throw new RuntimeException("PIDF Profile name must be specified");
                }

                if (!profile.equals(mCurrentProfileName)) {
                    setProfile(profile);
                }

                fMotor.set(ControlMode.MotionMagic, outputValue / fPositionScalar);
                break;
            default:
                throw new RuntimeException("No output type " + outputType + " for TalonSRX");
        }
    }

    @Override
    public double getSensorPosition() {
        return fMotor.getSelectedSensorPosition(0) * fPositionScalar;
    }

    @Override
    public double getSensorVelocity() {
        return fMotor.getSelectedSensorVelocity(0) * 10 * fVelocityScalar;
    }

    @Override
    public double getMotorCurrent() {
        return fPDP.getCurrent(fDeviceNumber);
    }

    @Override
    public double getMotorTemperature() {
        return fMotor.getTemperature();
    }

    @Override
    public void zeroSensor() {
        fMotor.setSelectedSensorPosition(0, 0, CAN_TIMEOUT_MILLISECONDS);
    }

    private void setProfile(String profileName) {
        if (!fProfiles.containsKey(profileName)) {
            throw new RuntimeException("PIDF Profile " + profileName + " doesn't exist");
        }

        Config profile = new Config("pidf_config", fProfiles.get(profileName));
        fMotor.config_kP(0, profile.getDouble("p", 0.0), CAN_TIMEOUT_MILLISECONDS);
        fMotor.config_kI(0, profile.getDouble("i", 0.0), CAN_TIMEOUT_MILLISECONDS);
        fMotor.config_kD(0, profile.getDouble("d", 0.0), CAN_TIMEOUT_MILLISECONDS);
        fMotor.config_kF(0, profile.getDouble("f", 0.0), CAN_TIMEOUT_MILLISECONDS);
        fMotor.configMotionAcceleration(profile.getInt("acceleration", 0), CAN_TIMEOUT_MILLISECONDS);
        fMotor.configMotionCruiseVelocity(profile.getInt("cruise_velocity", 0), CAN_TIMEOUT_MILLISECONDS);
        //S Curve values tend to cause slamming and jerking
        fMotor.configMotionSCurveStrength(profile.getInt("s_curve", 0), CAN_TIMEOUT_MILLISECONDS);

        mCurrentProfileName = profileName;
    }
}
