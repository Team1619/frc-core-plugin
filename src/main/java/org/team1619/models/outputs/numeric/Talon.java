package org.team1619.models.outputs.numeric;

import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;

/**
 * Talon is a motor object, which is extended to control talons
 */

public abstract class Talon extends CTREMotor {

    protected final InputValues fSharedInputValues;
    protected final String fFeedbackDevice;
    protected final String fPositionInputName;
    protected final String fVelocityInputName;
    protected final String fCurrentInputName;
    protected final String fTemperatureInputName;
    protected final boolean fHasEncoder;
    protected final boolean fSensorInverted;
    protected final boolean fReadPosition;
    protected final boolean fReadVelocity;
    protected final boolean fReadCurrent;
    protected final boolean fReadTemperature;
    protected final boolean fCurrentLimitEnabled;
    protected final int fContinuousCurrentLimitAmps;
    protected final int fPeakCurrentLimitAmps;
    protected final int fPeakCurrentDurationMilliseconds;
    protected final double fPercentScalar;
    protected final double fPositionScalar;
    protected final double fVelocityScalar;

    public Talon(Object name, Config config, InputValues inputValues) {
        super(name, config);

        fSharedInputValues = inputValues;

        fFeedbackDevice = config.getString("feedback_device", "");
        fHasEncoder = !fFeedbackDevice.isEmpty();
        fSensorInverted = config.getBoolean("sensor_inverted", false);
        fReadPosition = config.getBoolean("read_position", false);
        fReadVelocity = config.getBoolean("read_velocity", false);
        fReadCurrent = config.getBoolean("read_current", false);
        fReadTemperature = config.getBoolean("read_temperature", false);
        fCurrentLimitEnabled = config.getBoolean("current_limit_enabled", false);
        fContinuousCurrentLimitAmps = config.getInt("continuous_current_limit_amps", 0);
        fPeakCurrentLimitAmps = config.getInt("peak_current_limit_amps", 0);
        fPercentScalar = config.getDouble("percent_scalar", 1.0);
        fPositionScalar = config.getDouble("position_scalar", 1.0);
        fVelocityScalar = config.getDouble("velocity_scalar", 1.0);
        fPeakCurrentDurationMilliseconds = config.getInt("peak_current_duration_milliseconds", 0);
        fPositionInputName = config.getString("position_input_name", name.toString().replaceFirst("opn_", "ipn_") + "_position");
        fVelocityInputName = config.getString("velocity_input_name", name.toString().replaceFirst("opn_", "ipn_") + "_velocity");
        fCurrentInputName = config.getString("current_input_name", name.toString().replaceFirst("opn_", "ipn_") + "_current");
        fTemperatureInputName = config.getString("temperature_input_name", name.toString().replaceFirst("opn_", "ipn_") + "_temperature");

    }

    // Read the encoder's position
    protected void readEncoderPosition() {
        if (!fHasEncoder) {
            return;
        }
        fSharedInputValues.setNumeric(fPositionInputName, getSensorPosition());
    }

    // Read the encoder's velocity
    protected void readEncoderVelocity() {
        if (!fHasEncoder) {
            return;
        }
        fSharedInputValues.setNumeric(fVelocityInputName, getSensorVelocity());
    }

    protected void readMotorCurrent() {
        fSharedInputValues.setNumeric(fCurrentInputName, getMotorCurrent());
    }

    protected void readMotorTemperature() {
        fSharedInputValues.setNumeric(fTemperatureInputName, getMotorTemperature());
    }

    public abstract double getSensorPosition();

    public abstract double getSensorVelocity();

    public abstract double getMotorCurrent();

    public abstract double getMotorTemperature();

    public abstract void zeroSensor();
}
