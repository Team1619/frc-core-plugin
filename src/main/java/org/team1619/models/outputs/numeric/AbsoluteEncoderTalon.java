package org.team1619.models.outputs.numeric;

import org.uacr.models.exceptions.ConfigurationInvalidTypeException;
import org.uacr.models.outputs.numeric.OutputNumeric;
import org.uacr.robot.AbstractModelFactory;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.YamlConfigParser;

public class AbsoluteEncoderTalon extends OutputNumeric {

    private final InputValues fInputValues;
    private final Talon fTalon;
    private final String fAbsolutePositionInput;
    private final double fMinAbsolutePosition;
    private final double fMaxAbsolutePosition;
    private final double fCountsPerRev;

    public AbsoluteEncoderTalon(Object name, Config config, YamlConfigParser parser, AbstractModelFactory modelFactory, InputValues inputValues) {
        super(name, config);

        fInputValues = inputValues;

        String talon = config.getString("talon");
        OutputNumeric motor = modelFactory.createOutputNumeric(talon, parser.getConfig(talon), parser);
        if (!(motor instanceof Talon)) {
            throw new ConfigurationInvalidTypeException("Talon", "master", motor);
        }
        fTalon = (Talon) motor;

        fAbsolutePositionInput = config.getString("absolute_position_input");

        fMinAbsolutePosition = config.getDouble("min_absolute_position", -180);
        fMaxAbsolutePosition = config.getDouble("max_absolute_position", 180);

        if(fMinAbsolutePosition >= fMaxAbsolutePosition) {
            throw new RuntimeException("min_absolute_position must be less than max_absolute_position");
        }

        fCountsPerRev = fMaxAbsolutePosition - fMinAbsolutePosition;
    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        if("absolute_position".equals(outputType)) {
            double requestedPosition = rangeEncoderPosition(outputValue);

            double absolutePosition = fInputValues.getNumeric(fAbsolutePositionInput);

            double relativePosition = fTalon.getSensorPosition();
            double rangedRelativePosition = rangeEncoderPosition(relativePosition);
            double relativePositionZero = relativePosition - rangedRelativePosition;

            double delta = relativePosition - absolutePosition;


        } else {
            fTalon.setHardware(outputType, outputValue, profile);
        }
    }

    @Override
    public void processFlag(String flag) {

    }

    private double rangeEncoderPosition(double position) {
        return ((position - fMinAbsolutePosition) % fCountsPerRev) + fMinAbsolutePosition;
    }
}
