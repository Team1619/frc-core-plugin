package org.team1619.robot;

import org.team1619.services.logging.LoggingService;
import org.team1619.shared.concretions.sim.SimDashboard;
import org.uacr.robot.RobotCore;
import org.uacr.services.webdashboard.WebDashboardService;
import org.uacr.utilities.services.Service;

import java.util.List;

public abstract class FrcSimRobot extends RobotCore {

    @Override
    protected List<Service> createInfoServices() {
        return List.of(
                new LoggingService(fInputValues, fOutputValues, fRobotConfiguration, new SimDashboard()),
                new WebDashboardService(fEventBus, fFms, fInputValues, fOutputValues, fRobotConfiguration));
    }
}
