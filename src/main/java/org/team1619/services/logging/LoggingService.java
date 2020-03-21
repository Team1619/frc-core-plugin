package org.team1619.services.logging;

import org.team1619.shared.abstractions.Dashboard;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.injection.Inject;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;
import org.uacr.utilities.services.ScheduledService;
import org.uacr.utilities.services.Scheduler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoggingService implements ScheduledService {
    private static final Logger sLogger = LogManager.getLogger(LoggingService.class);

    private final InputValues fSharedInputValues;
    private final OutputValues fSharedOutputValues;
    private final RobotConfiguration fRobotConfiguration;
    private final Dashboard fDashboard;
    private double fPreviousTime;
    private long FRAME_TIME_THRESHOLD;
    private long FRAME_CYCLE_TIME_THRESHOLD;

    private Set<String> fDesiredLogs = new HashSet<>();

    @Inject
    public LoggingService(InputValues inputValues, OutputValues outputValues, RobotConfiguration robotConfiguration, Dashboard dashboard) {
        fSharedInputValues = inputValues;
        fRobotConfiguration = robotConfiguration;
        fSharedOutputValues = outputValues;
        fDashboard = dashboard;
    }

    @Override
    public void startUp() throws Exception {
        sLogger.debug("Starting LoggingService");

        String valuesToLog = "Logging the following values: [";

        if (!fRobotConfiguration.categoryIsEmpty("log")) {
            Map<String, Object> logConfig = fRobotConfiguration.getCategory("log");
            for (String name : logConfig.keySet()) {
                if ((Boolean) logConfig.get(name)) {
                    fDesiredLogs.add(name);
                    valuesToLog += (name + ", ");
                }
            }
        }

        valuesToLog = valuesToLog.substring(0, valuesToLog.length() - 2) + "]";
        sLogger.trace(valuesToLog);

        fPreviousTime = System.currentTimeMillis();
        FRAME_TIME_THRESHOLD = fRobotConfiguration.getInt("global_timing", "frame_time_threshold_logging_service");
        FRAME_CYCLE_TIME_THRESHOLD = fRobotConfiguration.getInt("global_timing", "frame_cycle_time_threshold_info_thread");

        fDashboard.initialize();

        sLogger.debug("LoggingService started");
    }

    @Override
    public void runOneIteration() throws Exception {

        double frameStartTime = System.currentTimeMillis();

        for (String name : fDesiredLogs) {
            String type = name.substring(0, 4);
            switch (type) {
                case "ipn_":
                    fDashboard.putNumber(name, fSharedInputValues.getNumeric(name));
                    break;
                case "ipb_":
                    fDashboard.putBoolean(name, fSharedInputValues.getBoolean(name));
                    break;
                case "ipv_":
                    Map<String, Double> inputvector = fSharedInputValues.getVector(name);
                    for (String key : inputvector.keySet()) {
                        fDashboard.putNumber(key, inputvector.get(key));
                    }
                    break;
                case "ips_":
                    fDashboard.putString(name, fSharedInputValues.getString(name));
                    break;
                case "opn_":
                    fDashboard.putNumber(name, (double) fSharedOutputValues.getOutputNumericValue(name).get("value"));
                    break;
                case "opb_":
                    fDashboard.putBoolean(name, fSharedOutputValues.getBoolean(name));
                    break;
                default:
                    throw new RuntimeException("The value type could not be determed for '" + name + "'. Ensure that it follows naming convention and matches " +
                            "its name from its yaml file. ");
            }
        }


        //Check for auto selection
        if (fDashboard.autoSelectionRisingEdge()) {
            fDashboard.smartdashboardSetAuto();
        }


        // Check for delayed frames
        double currentTime = System.currentTimeMillis();
        double frameTime = currentTime - frameStartTime;
        double totalCycleTime = frameStartTime - fPreviousTime;
        fSharedInputValues.setNumeric("ipn_frame_time_logging_service", frameTime);
        fSharedInputValues.setNumeric("ipn_frame_cycle_time_info_thread", totalCycleTime);
        if (frameTime > FRAME_TIME_THRESHOLD) {
            sLogger.debug("********** Logging Service frame time = {}", frameTime);
        }
        if (totalCycleTime > FRAME_CYCLE_TIME_THRESHOLD) {
            sLogger.debug("********** Info thread frame cycle time = {}", totalCycleTime);
        }
        fPreviousTime = frameStartTime;
    }

    @Override
    public void shutDown() throws Exception {

    }

    @Override
    public Scheduler scheduler() {
        return new Scheduler(1000 / 60);
    }
}
