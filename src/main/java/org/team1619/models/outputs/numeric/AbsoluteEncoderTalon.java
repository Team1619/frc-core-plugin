package org.team1619.models.outputs.numeric;

import org.uacr.models.exceptions.ConfigurationInvalidTypeException;
import org.uacr.models.outputs.numeric.OutputNumeric;
import org.uacr.robot.AbstractModelFactory;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.YamlConfigParser;

import java.util.Set;

public class AbsoluteEncoderTalon extends OutputNumeric {

    private final InputValues fInputValues;
    private final Talon fTalon;
    private final String fAbsolutePositionInput;
    private final double fMinAbsolutePosition;
    private final double fMaxAbsolutePosition;
    private final double fCountsPerRev;
    private final double fMaxRotationDistance;

    private boolean fIsZeroing;
    private double fPositionOffset;

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

        if (fMinAbsolutePosition >= fMaxAbsolutePosition) {
            throw new RuntimeException("min_absolute_position must be less than max_absolute_position");
        }

        fCountsPerRev = fMaxAbsolutePosition - fMinAbsolutePosition;
        fMaxRotationDistance = fCountsPerRev / 2;

        fIsZeroing = false;
        fPositionOffset = 0.0;
    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        if(fIsZeroing) {
            double talonPosition = fTalon.getSensorPosition();
            if (Math.abs(talonPosition) > 0.1) {
                fTalon.setHardware("percent", 0.0, "none");
               // fTalon.processFlags("zero");
            } else {
                fPositionOffset = talonPosition - fInputValues.getVector(fAbsolutePositionInput).getOrDefault("absolute_position", 0.0);
                fIsZeroing = false;
            }
        } else {
            if ("absolute_position".equals(outputType)) {
                double requestedPosition = rangeEncoderPosition(outputValue);

                double relativePosition = fTalon.getSensorPosition();
                double rangedRelativePosition = rangeEncoderPosition(relativePosition);
                double relativePositionZeroDistance = relativePosition - rangedRelativePosition;

                double target = rangeEncoderPosition(requestedPosition + fPositionOffset) + relativePositionZeroDistance;

                if (target - relativePosition > fMaxRotationDistance) {
                    target -= fCountsPerRev;
                } else if (relativePosition - target > fMaxRotationDistance) {
                    target += fCountsPerRev;
                }

                fTalon.setHardware("position", target, profile);
            } else {
                fTalon.setHardware(outputType, outputValue, profile);
            }
        }
    }

    @Override
    public void processFlags(Set<String> flags) {
        if(flags.contains("zero")) {
           fIsZeroing = true;
        } else {
            fTalon.processFlags(flags);
        }
    }

    private double rangeEncoderPosition(double position) {
        return ((position - fMinAbsolutePosition) % fCountsPerRev) + fMinAbsolutePosition;
    }
}
