package org.team1619.models.inputs.vector.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.team1619.models.inputs.vector.Navx;
import org.uacr.shared.abstractions.HardwareFactory;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Map;

public class RobotNavx extends Navx {

    private static final Logger sLogger = LogManager.getLogger(RobotNavx.class);

    private final AHRS fNavx;

    private double mNavxYaw;
    private double mNavxRoll;
    private double mNavxPitch;
    private double mNavxCompass;
    private double mNavxAngle;
    private double mNavxFusedHeading;
    private double mNavxAccelX;
    private double mNavxAccelY;
    private double mNavxAccelZ;


    public RobotNavx(Object name, Config config, HardwareFactory hardwareFactory) {
        super(name, config);

        fNavx = hardwareFactory.get(AHRS.class, SPI.Port.kMXP);
        fNavx.zeroYaw();

        mNavxYaw = 0.0;
        mNavxRoll = 0.0;
        mNavxPitch = 0.0;
        mNavxCompass = 0.0;
        mNavxAngle = 0.0;
        mNavxFusedHeading = 0.0;
        mNavxAccelX = 0.0;
        mNavxAccelY = 0.0;
        mNavxAccelX = 0.0;
    }

    @Override
    protected void zeroYaw() {
        sLogger.debug("RobotNavxInput -> Zeroing yaw");
        fNavx.zeroYaw();
    }

    @Override
    protected Map<String, Double> readHardware() {

        // Inverted
        mNavxYaw = mIsInverted.get("yaw") ? fNavx.getYaw() * -1 : fNavx.getYaw();
        mNavxRoll = mIsInverted.get("roll") ? fNavx.getRoll() * -1 : fNavx.getRoll();
        mNavxPitch = mIsInverted.get("pitch") ? fNavx.getPitch() * -1 : fNavx.getPitch();
        mNavxCompass = mIsInverted.get("compass") ? 360 - fNavx.getCompassHeading() : fNavx.getCompassHeading();
        mNavxAngle = mIsInverted.get("angle") ? fNavx.getAngle() * -1 : fNavx.getAngle();
        mNavxFusedHeading = mIsInverted.get("fused_heading") ? 360 - fNavx.getFusedHeading() : fNavx.getFusedHeading();
        mNavxAccelX = mIsInverted.get("accel_x") ? fNavx.getRawAccelX() * -1 : fNavx.getRawAccelX();
        mNavxAccelY = mIsInverted.get("accel_y") ? fNavx.getRawAccelY() * -1 : fNavx.getRawAccelY();
        mNavxAccelZ = mIsInverted.get("accel_z") ? fNavx.getRawAccelZ() * -1 : fNavx.getRawAccelZ();

        //Radians
        mNavxYaw = mIsRaidans.get("yaw") ? mNavxYaw * Math.PI / 180 : mNavxYaw;
        mNavxRoll = mIsRaidans.get("roll") ? mNavxRoll * Math.PI / 180 : mNavxRoll;
        mNavxPitch = mIsRaidans.get("pitch") ? mNavxPitch * Math.PI / 180 : mNavxPitch;
        mNavxCompass = mIsRaidans.get("compass") ? mNavxCompass * Math.PI / 180 : mNavxCompass;
        mNavxAngle = mIsRaidans.get("angle") ? mNavxAngle * Math.PI / 180 : mNavxAngle;
        mNavxFusedHeading = mIsRaidans.get("fused_heading") ? mNavxFusedHeading * Math.PI / 180 : mNavxFusedHeading;

        return Map.of("yaw", mNavxYaw, "roll", mNavxRoll, "pitch", mNavxPitch, "compass", mNavxCompass, "angle", mNavxAngle, "fused_heading", mNavxFusedHeading, "accel_x", mNavxAccelX, "accel_y", mNavxAccelY, "accel_z", mNavxAccelZ);
    }
}
