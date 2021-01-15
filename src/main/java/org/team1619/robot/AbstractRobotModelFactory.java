package org.team1619.robot;

import org.team1619.models.inputs.bool.robot.RobotControllerButton;
import org.team1619.models.inputs.bool.robot.RobotDigitalSensor;
import org.team1619.models.inputs.bool.robot.RobotJoystickButton;
import org.team1619.models.inputs.numeric.robot.RobotAnalogSensor;
import org.team1619.models.inputs.numeric.robot.RobotControllerAxis;
import org.team1619.models.inputs.numeric.robot.RobotJoystickAxis;
import org.team1619.models.inputs.vector.Odometry;
import org.team1619.models.inputs.vector.SwerveOdometry;
import org.team1619.models.inputs.vector.robot.RobotAcceleration;
import org.team1619.models.inputs.vector.robot.RobotCanCoder;
import org.team1619.models.inputs.vector.robot.RobotLimelight;
import org.team1619.models.inputs.vector.robot.RobotNavx;
import org.team1619.models.outputs.bool.robot.RobotSolenoidDouble;
import org.team1619.models.outputs.bool.robot.RobotSolenoidSingle;
import org.team1619.models.outputs.numeric.AbsoluteEncoderTalon;
import org.team1619.models.outputs.numeric.MotorGroup;
import org.team1619.models.outputs.numeric.robot.RobotRumble;
import org.team1619.models.outputs.numeric.robot.RobotServo;
import org.team1619.models.outputs.numeric.robot.RobotTalon;
import org.team1619.models.outputs.numeric.robot.RobotVictor;
import org.uacr.models.inputs.bool.InputBoolean;
import org.uacr.models.inputs.numeric.InputNumeric;
import org.uacr.models.inputs.vector.InputVector;
import org.uacr.models.outputs.bool.OutputBoolean;
import org.uacr.models.outputs.numeric.OutputNumeric;
import org.uacr.robot.AbstractModelFactory;
import org.uacr.shared.abstractions.*;
import org.uacr.utilities.Config;
import org.uacr.utilities.YamlConfigParser;
import org.uacr.utilities.injection.Inject;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

public class AbstractRobotModelFactory extends AbstractModelFactory {

    private static final Logger sLogger = LogManager.getLogger(AbstractRobotModelFactory.class);

    private final HardwareFactory fSharedHardwareFactory;

    @Inject
    public AbstractRobotModelFactory(HardwareFactory hardwareFactory, InputValues inputValues, OutputValues outputValues, RobotConfiguration robotConfiguration, ObjectsDirectory objectsDirectory) {
        super(inputValues, outputValues, robotConfiguration, objectsDirectory);

        fSharedHardwareFactory = hardwareFactory;
    }

    @Override
    public OutputNumeric createOutputNumeric(Object name, Config config, YamlConfigParser parser) {
        sLogger.trace("Creating output numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "talon":
                return new RobotTalon(name, config, fSharedHardwareFactory, fSharedInputValues);
            case "victor":
                return new RobotVictor(name, config, fSharedHardwareFactory);
            case "motor_group":
                return new MotorGroup(name, config, parser, this);
            case "absolute_encoder_talon":
                return new AbsoluteEncoderTalon(name, config, parser, this, fSharedInputValues);
            case "servo":
                return new RobotServo(name, config, fSharedHardwareFactory);
            case "rumble":
                return new RobotRumble(name, config, fSharedHardwareFactory);
            default:
                return super.createOutputNumeric(name, config, parser);
        }
    }

    @Override
    public OutputBoolean createOutputBoolean(Object name, Config config, YamlConfigParser parser) {
        sLogger.trace("Creating output boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "solenoid_single":
                return new RobotSolenoidSingle(name, config, fSharedHardwareFactory);
            case "solenoid_double":
                return new RobotSolenoidDouble(name, config, fSharedHardwareFactory);
            default:
                return super.createOutputBoolean(name, config, parser);
        }
    }

    @Override
    public InputBoolean createInputBoolean(Object name, Config config) {
        sLogger.trace("Creating input boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "joystick_button":
                return new RobotJoystickButton(name, config, fSharedHardwareFactory);
            case "controller_button":
                return new RobotControllerButton(name, config, fSharedHardwareFactory);
            case "digital_input":
                return new RobotDigitalSensor(name, config, fSharedHardwareFactory);
            default:
                return super.createInputBoolean(name, config);
        }
    }

    @Override
    public InputNumeric createInputNumeric(Object name, Config config) {
        sLogger.trace("Creating input numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "joystick_axis":
                return new RobotJoystickAxis(name, config, fSharedHardwareFactory);
            case "controller_axis":
                return new RobotControllerAxis(name, config, fSharedHardwareFactory);
            case "analog_sensor":
                return new RobotAnalogSensor(name, config, fSharedHardwareFactory);
            default:
                return super.createInputNumeric(name, config);
        }
    }

    @Override
    public InputVector createInputVector(Object name, Config config) {
        sLogger.trace("Creating input vector '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "accelerometer_input":
                return new RobotAcceleration(name, config, fSharedInputValues);
            case "odometry_input":
                return new Odometry(name, config, fSharedInputValues);
            case "swerve_odometry_input":
                return new SwerveOdometry(name, config, fSharedInputValues);
            case "limelight":
                return new RobotLimelight(name, config);
            case "navx":
                return new RobotNavx(name, config, fSharedHardwareFactory);
            case "cancoder":
                return new RobotCanCoder(name,config,fSharedHardwareFactory);
            default:
                return super.createInputVector(name, config);
        }
    }
}