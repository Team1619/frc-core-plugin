package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.outputs.numeric.Rumble;
import org.uacr.utilities.Config;

/**
 * SimRumble extends Rumble, and acts like xbox controller rumble motors in sim mode
 */

public class SimRumble extends Rumble {

    private double fOutput;
    private String fRumbleSide;
    private double fAdjustedOutput;

    public SimRumble(Object name, Config config) {
        super(name, config);
        fOutput = 0.0;
        fAdjustedOutput = 0.0;
        fRumbleSide = "none";
    }

    @Override
    public void processFlag(String flag) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        fAdjustedOutput = outputValue;
        if (fRumbleSide.equals("right")) {
            fOutput = fAdjustedOutput;
            fRumbleSide = "right";
        } else if (fRumbleSide.equals("left")) {
            fOutput = fAdjustedOutput;
            fRumbleSide = "left";
        }
    }
}