package logon.tracer.factor;

import logon.tracer.AlarmLogWarnService;

import java.util.ArrayList;
import java.util.List;

public class AlarmLogWarnServiceFactory {
  private static List<AlarmLogWarnService> serviceList = new ArrayList<>();

  public AlarmLogWarnServiceFactory() {
  }

  public AlarmLogWarnServiceFactory(List<AlarmLogWarnService> alarmLogWarnServices) {
    serviceList = alarmLogWarnServices;
  }

  public static void setAlarmLogWarnService(AlarmLogWarnService alarmLogWarnService) {
    serviceList.add(alarmLogWarnService);
  }

  public static List<AlarmLogWarnService> getServiceList () {
    return serviceList;
  }
}
