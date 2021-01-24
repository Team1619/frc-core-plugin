package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.Lists;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;
import org.uacr.utilities.purepursuit.Pose2d;
import org.uacr.utilities.purepursuit.Vector;

import java.util.ArrayList;
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

    private static final Logger LOGGER = LogManager.getLogger(SwerveOdometry.class);

    protected final Config config;
    protected final InputValues sharedInputValues;
    private final String navx;
    private final List<String> modulePositionInputs;
    private final List<String> moduleAngleInputs;

    private Map<String, Double> navxValues;
    private List<Double> lastModulePositions;
    private List<Double> lastModuleAngles;
    private double heading;

    private Pose2d currentPosition;

    public SwerveOdometry(Object name, Config config, InputValues inputValues) {
        super(name, config);

        this.config = config;
        sharedInputValues = inputValues;
        navxValues = new HashMap<>();

        navx = config.getString("navx");

        modulePositionInputs = Lists.of(config.getString("front_right_position"), config.getString("front_left_position"),
                config.getString("back_left_position"), config.getString("back_right_position"));

        moduleAngleInputs = Lists.of(config.getString("front_right_angle"), config.getString("front_left_angle"),
                config.getString("back_left_angle"), config.getString("back_right_angle"));

        lastModulePositions = new ArrayList<>();
        lastModulePositions.addAll(List.of(0.0, 0.0, 0.0, 0.0));
        lastModuleAngles = new ArrayList<>();
        lastModuleAngles.addAll(List.of(0.0, 0.0, 0.0, 0.0));

        currentPosition = new Pose2d();
        heading = 0;
    }

    @Override
    public void initialize() {
        getModuleVector(0);
        getModuleVector(1);
        getModuleVector(2);
        getModuleVector(3);
    }

    @Override
    public void update() {
        heading = getHeading();

        currentPosition = new Pose2d(currentPosition.add(new Vector(getModuleVector(0).add(getModuleVector(1)).add(getModuleVector(2)).add(getModuleVector(3))).scale(0.25).rotate(heading)), heading);
    }

    public void zeroPosition() {
        currentPosition = new Pose2d();
        initialize();
    }

    public Vector getModuleVector(int module) {
        return new Vector(getModuleDistance(module), getModuleAngle(module));
    }

    public double getModuleDistance(int module) {
        double position = sharedInputValues.getNumeric(modulePositionInputs.get(module));
        return position - lastModulePositions.set(module, position);
    }

    public double getModuleAngle(int module) {
        double angle = sharedInputValues.getVector(moduleAngleInputs.get(module)).getOrDefault("absolute_position", 0.0);
        return (angle + lastModuleAngles.set(module, angle)) / 2;
    }

    @Override
    public Map<String, Double> get() {
        return Map.of("x", currentPosition.getX(), "y", currentPosition.getY(), "heading", currentPosition.getHeading());
    }

    private double getHeading() {
        navxValues = sharedInputValues.getVector(navx);
        double heading = navxValues.getOrDefault("angle", 0.0);

        //Inverts the heading to so that positive angle is counterclockwise, this makes trig functions work properly
        heading = -heading;

        return heading;
    }

    @Override
    public void processFlag(String flag) {
        if ("zero".equals(flag)) {
            zeroPosition();
            LOGGER.debug("Odometry Input -> Zeroed");
        }
    }
}
