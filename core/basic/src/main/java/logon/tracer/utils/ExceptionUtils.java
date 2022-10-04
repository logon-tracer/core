package logon.tracer.utils;

import logon.tracer.exception.AlarmLogDoWarnException;
import logon.tracer.exception.AlarmLogException;
import logon.tracer.exception.AlarmLogRuntimeException;

import java.util.List;

public class ExceptionUtils {
  public static boolean doWarnExceptionInstance(Throwable throwable) {
    return throwable instanceof AlarmLogDoWarnException
      || throwable instanceof AlarmLogException
      || throwable instanceof AlarmLogRuntimeException;
  }

  public static boolean doWarnExceptionName(Throwable warnExceptionClass, List<String> doWarnExceptionList) {
    return doWarnExceptionList.contains(warnExceptionClass.getClass().getName());
  }

  public static boolean doWarnExceptionExtend(Throwable warnExceptionClass, List<Class<? extends Throwable>> doExtendWarnExceptionList) {
    for (Class<?> aClass : doExtendWarnExceptionList) {
      if (aClass.isAssignableFrom(warnExceptionClass.getClass())) {
        return true;
      }
    }
    return false;
  }
}
