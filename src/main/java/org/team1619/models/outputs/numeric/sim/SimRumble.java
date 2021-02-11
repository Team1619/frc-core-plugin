package org.team1619.models.outputs.numeric.sim;

import org.team1619.models.outputs.numeric.Rumble;
import org.uacr.utilities.Config;

import java.util.Set;

/**
 * SimRumble extends Rumble, and acts like xbox controller rumble motors in sim mode
 */

public class SimRumble extends Rumble {

    private double mOutput;
    private double mAdjustedOutput;
    private String mRumbleSide;

    public SimRumble(Object name, Config config) {
        super(name, config);

        mOutput = 0.0;
        mAdjustedOutput = 0.0;
        mRumbleSide = "none";
    }

    @Override
    public void processFlags(Set<String> flags) {

    }

    @Override
    public void setHardware(String outputType, double outputValue, String profile) {
        mAdjustedOutput = outputValue;
        if (mRumbleSide.equals("right")) {
            mOutput = mAdjustedOutput;
            mRumbleSide = "right";
        } else if (mRumbleSide.equals("left")) {
            mOutput = mAdjustedOutput;
            mRumbleSide = "left";
        }
    }
}