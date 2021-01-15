package org.team1619.models.inputs.vector.robot;

import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import org.team1619.models.inputs.vector.Encoder;
import org.uacr.utilities.Config;
import org.uacr.shared.abstractions.HardwareFactory;

import java.util.Map;

public class RobotCanCoder extends Encoder {

    private final CANCoder canCoder;
    private CANCoderConfiguration canCoderConfiguration = new CANCoderConfiguration();

    public RobotCanCoder(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);
        canCoder = hardwareFactory.get(CANCoder.class, deviceNumber);
    }

    @Override
    public void initialize() {
        canCoderConfiguration.unitString = "degrees";
        canCoderConfiguration.initializationStrategy = (bootToAbsolutePosition) ? SensorInitializationStrategy.BootToAbsolutePosition : SensorInitializationStrategy.BootToZero;
        canCoderConfiguration.absoluteSensorRange = (sensorRange360) ? AbsoluteSensorRange.Unsigned_0_to_360 : AbsoluteSensorRange.Signed_PlusMinus180;
        canCoderConfiguration.sensorDirection = (inverted) ? true : false;
        canCoderConfiguration.magnetOffsetDegrees = magnetOffset;
        canCoder.configAllSettings(canCoderConfiguration);
    }

    @Override
    protected Map<String, Double> readHardware() {
        double position = canCoder.getPosition() * positionScalar;
        double velocity = canCoder.getVelocity() * velocityScalar;
        return Map.of("position", position, "velocity", velocity);
    }

    @Override
    protected void zeroEncoder() {

    }
}

