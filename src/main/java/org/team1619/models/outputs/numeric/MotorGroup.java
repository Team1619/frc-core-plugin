package org.team1619.models.outputs.numeric;

import org.uacr.models.exceptions.ConfigurationInvalidTypeException;
import org.uacr.models.outputs.numeric.OutputNumeric;
import org.uacr.robot.ModelFactory;
import org.uacr.utilities.Config;
import org.uacr.utilities.YamlConfigParser;

import java.util.HashSet;
import java.util.Set;

/**
 * Motor group holds a reference to a master CTRE motor, and all its follower CTRE motors.
 * A motor group acts just like a regular motor to the framework, but sets all the followers into follower mode,
 * and passes all setHardware calls to the master motor.
 */

public class MotorGroup extends OutputNumeric {

    private final CTREMotor fMaster;
    private final Set<CTREMotor> fSlaves = new HashSet<>();

    public MotorGroup(Object name, Config config, YamlConfigParser parser, ModelFactory modelFactory) {
        super(name, config);

        String master = config.getString("master");
        OutputNumeric motor = modelFactory.createOutputNumeric(master, parser.getConfig(master), parser);
        if (!(motor instanceof CTREMotor)) {
            throw new ConfigurationInvalidTypeException("Talon", "master", motor);
        }
        fMaster = (CTREMotor) motor;

        for (Object slaveName : config.getList("followers")) {
            motor = modelFactory.createOutputNumeric(slaveName, parser.getConfig(slaveName), parser);
            if (!(motor instanceof CTREMotor)) {
                throw new ConfigurationInvalidTypeException("Motor", "follower", motor);
            }

            CTREMotor slave = (CTREMotor) motor;
            slave.setHardware("follower", fMaster.getDeviceNumber(), "none");
            fSlaves.add(slave);
        }
    }

    @Override
    public void processFlag(String flag) {
        fMaster.processFlag(flag);
    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        fMaster.setHardware(outputType, outputValue, profile);

        // Uncomment this to read position, velocity, or current on follower motors
        for (CTREMotor slave : fSlaves) {
            slave.setHardware("follower", fMaster.getDeviceNumber(), "none");
        }
    }
}
