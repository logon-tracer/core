package logon.tracer.helper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlarmLogHelper {
  public static PrintLogHelper getPrintLogInstance(boolean alarm) {
    return new PrintLogHelper(alarm);
  }

  public static PrintLogHelper getPrintLogInstance() {
    return new PrintLogHelper(true);
  }
}
