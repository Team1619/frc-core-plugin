package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.inputs.numeric.sim.SimInputNumericListener;
import org.team1619.models.outputs.numeric.Talon;
import org.uacr.events.sim.SimInputNumericSetEvent;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.utilities.Config;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * SimTalon extends Talon, and acts like talons in sim mode
 */

public class SimTalon extends Talon {

    private final SimInputNumericListener fPositionListener;
    private final SimInputNumericListener fVelocityListener;
    private final Map<String, Map<String, Double>> fProfiles;
    private final Integer fMotor;

    private double mOutput = 0.0;
    private String mCurrentProfileName = "none";

    public SimTalon(Object name, Config config, HardwareFactory hardwareFactory, EventBus eventBus, InputValues inputValues) {
        super(name, config, inputValues);

        fPositionListener = new SimInputNumericListener(eventBus, fPositionInputName);
        fVelocityListener = new SimInputNumericListener(eventBus, fVelocityInputName);

        // Included to mimic RobotTalon for testing
        fMotor = hardwareFactory.get(Integer.class, fDeviceNumber);

        if (!(config.get("profiles", new HashMap<>()) instanceof Map)) throw new RuntimeException();
        fProfiles = (Map<String, Map<String, Double>>) config.get("profiles", new HashMap<>());
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

        switch (outputType) {
            case "percent":
                mOutput = outputValue;
                break;
            case "follower":
                mOutput = outputValue;
                break;
            case "velocity":
                if (profile.equals("none")) {
                    throw new RuntimeException("PIDF Profile name must be specified");
                }

                if (!profile.equals(mCurrentProfileName)) {
                    if (!fProfiles.containsKey(profile)) {
                        throw new RuntimeException("PIDF Profile " + profile + " doesn't exist");
                    }

                    mCurrentProfileName = profile;
                }

                mOutput = outputValue;

                break;
            case "position":
                mOutput = outputValue;
                break;
            case "motion_magic":
                mOutput = outputValue;
                break;
            default:
                throw new RuntimeException("No output type " + outputType + " for TalonSRX");
        }
//		sLogger.trace("{}", outputValue);
    }

    @Override
    public double getMotorCurrent() {
        return 0;
    }

    @Override
    public double getMotorTemperature() {
        return 0;
    }

    @Override
    public double getSensorPosition() {
        return fPositionListener.get();
    }

    @Override
    public double getSensorVelocity() {
        return fVelocityListener.get();
    }

    @Override
    public void zeroSensor() {
        fPositionListener.onInputNumericSet(new SimInputNumericSetEvent(fPositionInputName, 0));
    }
}
