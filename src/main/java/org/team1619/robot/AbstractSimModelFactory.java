package org.team1619.robot;

import org.team1619.models.inputs.bool.sim.SimControllerButton;
import org.team1619.models.inputs.bool.sim.SimDigitalSensor;
import org.team1619.models.inputs.numeric.sim.SimAnalogSensor;
import org.team1619.models.inputs.numeric.sim.SimAxis;
import org.team1619.models.inputs.vector.Odometry;
import org.team1619.models.inputs.vector.SwerveOdometry;
import org.team1619.models.inputs.vector.robot.RobotCanCoder;
import org.team1619.models.inputs.vector.sim.SimAcceleration;
import org.team1619.models.inputs.vector.sim.SimCanCoder;
import org.team1619.models.inputs.vector.sim.SimLimelight;
import org.team1619.models.inputs.vector.sim.SimNavx;
import org.team1619.models.outputs.bool.sim.SimSolenoidDouble;
import org.team1619.models.outputs.bool.sim.SimSolenoidSingle;
import org.team1619.models.outputs.numeric.MotorGroup;
import org.team1619.models.outputs.numeric.sim.SimRumble;
import org.team1619.models.outputs.numeric.sim.SimServo;
import org.team1619.models.outputs.numeric.sim.SimTalon;
import org.team1619.models.outputs.numeric.sim.SimVictor;
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

public class AbstractSimModelFactory extends AbstractModelFactory {

    private static final Logger sLogger = LogManager.getLogger(AbstractSimModelFactory.class);

    private final EventBus fSharedEventBus;
    private final HardwareFactory fSharedHardwareFactory;

    @Inject
    public AbstractSimModelFactory(EventBus eventBus, HardwareFactory hardwareFactory, InputValues inputValues, OutputValues outputValues, RobotConfiguration robotConfiguration, ObjectsDirectory objectsDirectory) {
        super(inputValues, outputValues, robotConfiguration, objectsDirectory);
        fSharedEventBus = eventBus;
        fSharedHardwareFactory = hardwareFactory;
    }

    @Override
    public OutputNumeric createOutputNumeric(Object name, Config config, YamlConfigParser parser) {
        sLogger.trace("Creating output numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "talon":
                return new SimTalon(name, config, fSharedHardwareFactory, fSharedEventBus, fSharedInputValues);
            case "victor":
                return new SimVictor(name, config, fSharedHardwareFactory);
            case "motor_group":
                return new MotorGroup(name, config, parser, this);
            case "servo":
                return new SimServo(name, config);
            case "rumble":
                return new SimRumble(name, config);
            default:
                return super.createOutputNumeric(name, config, parser);
        }
    }

    @Override
    public OutputBoolean createOutputBoolean(Object name, Config config, YamlConfigParser parser) {
        sLogger.trace("Creating output boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());
        switch (config.getType()) {
            case "solenoid_single":
                return new SimSolenoidSingle(name, config);
            case "solenoid_double":
                return new SimSolenoidDouble(name, config);
            default:
                return super.createOutputBoolean(name, config, parser);
        }
    }

    @Override
    public InputBoolean createInputBoolean(Object name, Config config) {
        sLogger.trace("Creating input boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "joystick_button":
                return new SimControllerButton(fSharedEventBus, name, config);
            case "controller_button":
                return new SimControllerButton(fSharedEventBus, name, config);
            case "digital_input":
                return new SimDigitalSensor(fSharedEventBus, name, config);
            default:
                return super.createInputBoolean(name, config);
        }
    }

    @Override
    public InputNumeric createInputNumeric(Object name, Config config) {
        sLogger.trace("Creating input numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "joystick_axis":
                return new SimAxis(fSharedEventBus, name, config);
            case "controller_axis":
                return new SimAxis(fSharedEventBus, name, config);
            case "analog_sensor":
                return new SimAnalogSensor(fSharedEventBus, name, config);
            default:
                return super.createInputNumeric(name, config);
        }
    }

    @Override
    public InputVector createInputVector(Object name, Config config) {
        sLogger.trace("Creating input vector '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

        switch (config.getType()) {
            case "accelerometer_input":
                return new SimAcceleration(fSharedEventBus, name, config, fSharedInputValues);
            case "odometry_input":
                return new Odometry(name, config, fSharedInputValues);
            case "swerve_odometry_input":
                return new SwerveOdometry(name, config, fSharedInputValues);
            case "limelight":
                return new SimLimelight(fSharedEventBus, name, config);
            case "navx":
                return new SimNavx(fSharedEventBus, name, config);
            case "cancoder":
                return new SimCanCoder(name, config);
            default:
                return super.createInputVector(name, config);
        }
    }
}