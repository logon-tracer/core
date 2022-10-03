package logon.tracer.factor;

import logon.tracer.LogWarnService;

import java.util.ArrayList;
import java.util.List;

public class LogWarnServiceFactory {
  private static List<LogWarnService> serviceList = new ArrayList<>();

  public LogWarnServiceFactory() {
  }

  public LogWarnServiceFactory(List<LogWarnService> logWarnServices) {
    serviceList = logWarnServices;
  }

  public static void setAlarmLogWarnService(LogWarnService logWarnService) {
    serviceList.add(logWarnService);
  }

  public static List<LogWarnService> getServiceList () {
    return serviceList;
  }
}
