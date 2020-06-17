package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.Lists;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;
import org.uacr.utilities.purepursuit.Pose2d;
import org.uacr.utilities.purepursuit.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SwerveOdometryInput is a Inputvector which uses the navx and drive encoders,
 * to track the robots current position relative to its starting point
 *
 * @author Matthew Oates
 */

public class SwerveOdometry extends InputVector {

    private static final Logger sLogger = LogManager.getLogger(SwerveOdometry.class);

    protected final Config fConfig;
    protected final InputValues fSharedInputValues;
    private final String fNavx;
    private final List<String> fModuleSpeedInputs;
    private final List<String> fModuleAngleInputs;

    private Map<String, Double> mNavxValues;
    private double mHeading = 0;

    private Pose2d mCurrentPosition;

    public SwerveOdometry(Object name, Config config, InputValues inputValues) {
        super(name, config);

        fConfig = config;
        fSharedInputValues = inputValues;
        mNavxValues = new HashMap<>();

        fNavx = config.getString("navx");

        fModuleSpeedInputs = Lists.of(config.getString("front_right_speed"), config.getString("front_left_speed"),
                config.getString("back_left_speed"), config.getString("back_right_speed"));

        fModuleAngleInputs = Lists.of(config.getString("front_right_angle"), config.getString("front_left_angle"),
                config.getString("back_left_angle"), config.getString("back_right_angle"));

        mCurrentPosition = new Pose2d();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        if (!fSharedInputValues.getBoolean("ipb_swerve_odometry_has_been_zeroed")) {
            zeroPosition();
            fSharedInputValues.setBoolean("ipb_swerve_odometry_has_been_zeroed", true);
            sLogger.debug("Odometry Input -> Zeroed");
            return;
        }

        mHeading = getHeading();

        mCurrentPosition = new Pose2d(mCurrentPosition.add(new Vector(getModuleVector(0).add(getModuleVector(1)).add(getModuleVector(2)).add(getModuleVector(3))).scale(0.1).rotate(-mHeading)), mHeading);
    }

    public void zeroPosition() {
        mCurrentPosition = new Pose2d();
    }

    public Vector getModuleVector(int module) {
        return new Vector(getModuleSpeed(module), getModuleAngle(module));
    }

    public double getModuleSpeed(int module) {
        return fSharedInputValues.getNumeric(fModuleSpeedInputs.get(module));
    }

    public double getModuleAngle(int module) {
        return fSharedInputValues.getNumeric(fModuleAngleInputs.get(module));
    }

    @Override
    public Map<String, Double> get() {
        return Map.of("x", mCurrentPosition.getX(), "y", mCurrentPosition.getY(), "heading", mCurrentPosition.getHeading());
    }

    private double getHeading() {
        mNavxValues = fSharedInputValues.getVector(fNavx);
        double heading = mNavxValues.getOrDefault("angle", 0.0);

        if (heading > 180) heading = -(360 - heading);

        //Inverts the heading to so that positive angle is counterclockwise, this makes trig functions work properly
        heading = -heading;

        return heading;
    }

    @Override
    public void processFlag(String flag) {

    }
}
