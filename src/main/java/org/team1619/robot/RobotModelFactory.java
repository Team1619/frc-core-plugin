package org.team1619.robot;

import org.team1619.models.inputs.bool.robot.RobotControllerButton;
import org.team1619.models.inputs.bool.robot.RobotDigitalSensor;
import org.team1619.models.inputs.bool.robot.RobotJoystickButton;
import org.team1619.models.inputs.numeric.robot.RobotAnalogSensor;
import org.team1619.models.inputs.numeric.robot.RobotControllerAxis;
import org.team1619.models.inputs.numeric.robot.RobotJoystickAxis;
import org.team1619.models.inputs.vector.Odometry;
import org.team1619.models.inputs.vector.robot.RobotAcceleration;
import org.team1619.models.inputs.vector.robot.RobotLimelight;
import org.team1619.models.inputs.vector.robot.RobotNavx;
import org.team1619.models.outputs.bool.robot.RobotSolenoidDouble;
import org.team1619.models.outputs.bool.robot.RobotSolenoidSingle;
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
import org.uacr.robot.ModelFactory;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.ObjectsDirectory;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.YamlConfigParser;
import org.uacr.utilities.injection.Inject;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

public abstract class RobotModelFactory extends ModelFactory {

	private static final Logger sLogger = LogManager.getLogger(RobotModelFactory.class);

	@Inject
	public RobotModelFactory(InputValues inputValues, OutputValues outputValues, RobotConfiguration robotConfiguration, ObjectsDirectory objectsDirectory) {
		super(inputValues, outputValues, robotConfiguration, objectsDirectory);
	}

	@Override
	public OutputNumeric createOutputNumeric(Object name, Config config, YamlConfigParser parser) {
		sLogger.trace("Creating output numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

		switch (config.getType()) {
			case "talon":
				return new RobotTalon(name, config, fSharedObjectDirectory, fSharedInputValues);
			case "victor":
				return new RobotVictor(name, config, fSharedObjectDirectory);
			case "motor_group":
				return new MotorGroup(name, config, parser, this);
			case "servo":
				return new RobotServo(name, config);
			case "rumble":
				return new RobotRumble(name, config);
			default:
				return super.createOutputNumeric(name, config, parser);
		}
	}

	@Override
	public OutputBoolean createOutputBoolean(Object name, Config config, YamlConfigParser parser) {
		sLogger.trace("Creating output boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

		switch (config.getType()) {
			case "solenoid_single":
				return new RobotSolenoidSingle(name, config);
			case "solenoid_double":
				return new RobotSolenoidDouble(name, config);
			default:
				return super.createOutputBoolean(name, config, parser);
		}
	}

	@Override
	public InputBoolean createInputBoolean(Object name, Config config) {
		sLogger.trace("Creating input boolean '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

		switch (config.getType()) {
			case "joystick_button":
				return new RobotJoystickButton(name, config);
			case "controller_button":
				return new RobotControllerButton(name, config);
			case "digital_input":
				return new RobotDigitalSensor(name, config);
			default:
				return super.createInputBoolean(name, config);
		}
	}

	@Override
	public InputNumeric createInputNumeric(Object name, Config config) {
		sLogger.trace("Creating input numeric '{}' of type '{}' with config '{}'", name, config.getType(), config.getData());

		switch (config.getType()) {
			case "joystick_axis":
				return new RobotJoystickAxis(name, config);
			case "controller_axis":
				return new RobotControllerAxis(name, config);
			case "analog_sensor":
				return new RobotAnalogSensor(name, config);
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
			case "limelight":
				return new RobotLimelight(name, config);
			case "navx":
				return new RobotNavx(name, config);
			default:
				return super.createInputVector(name, config);
		}
	}

}