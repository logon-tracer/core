package logon.tracer.utils;

import logon.tracer.exception.LogDoWarnException;
import logon.tracer.exception.LogException;
import logon.tracer.exception.LogRuntimeException;

import java.util.List;

public class ExceptionUtils {
  public static boolean doWarnExceptionInstance(Throwable throwable) {
    return throwable instanceof LogDoWarnException
      || throwable instanceof LogException
      || throwable instanceof LogRuntimeException;
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
