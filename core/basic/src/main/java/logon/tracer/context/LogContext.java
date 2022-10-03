package logon.tracer.context;

import logon.tracer.dto.LogSimpleConfig;
import logon.tracer.utils.ExceptionUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogContext {
  private static final Logger logger = LoggerFactory.getLogger(LogContext.class);

  @Getter
  @Setter
  private static int maxRetryTimes = 3;

  @Getter
  @Setter
  private static int retrySleepMillis = 1000;

  @Getter
  @Setter
  private static Boolean printStackTrace = false;

  @Getter
  @Setter
  private static Boolean simpleWarnInfo = false;

  private static Boolean warnExceptionExtend = false;

  private static List<String> doWarnExceptionList = new ArrayList<>();

  private static List<Class<? extends Throwable>> doExtendWarnExceptionList = new ArrayList<>();

  @Getter
  @Setter
  private static MessageContext alarmMessageContext = new DefaultMessageContext();

  private static LogSimpleConfig simpleConfig;

  public static LogSimpleConfig getSimpleConfig () {
    if (Objects.isNull(simpleConfig)) {
      simpleConfig = LogSimpleConfig.builder().printStackTrace(printStackTrace).simpleWarnInfo(simpleWarnInfo).build();
    }
    return simpleConfig;
  }

  public static Boolean getWarnExceptionExtend() {
    return warnExceptionExtend;
  }

  public static void setWarnExceptionExtend(Boolean warnExceptionExtend) {
    LogContext.warnExceptionExtend = warnExceptionExtend;
    if (warnExceptionExtend && !LogContext.doWarnExceptionList.isEmpty()) {
      genExtendWarnExceptionList();
    }
  }

  public static List<String> getDoWarnExceptionList() {
    return doWarnExceptionList;
  }

  public static void addDoWarnExceptionList(List<String> doWarnExceptionList) {
    LogContext.doWarnExceptionList.addAll(doWarnExceptionList);
    if (LogContext.warnExceptionExtend) {
      genExtendWarnExceptionList(doWarnExceptionList);
    }
  }

  public static void setDoWarnExceptionList(List<String> doWarnExceptionList) {
    LogContext.doWarnExceptionList = doWarnExceptionList;
    if (LogContext.warnExceptionExtend) {
      genExtendWarnExceptionList();
    }
  }

  public static boolean doWarnException(Throwable warnExceptionClass) {
    return LogContext.warnExceptionExtend ? ExceptionUtils.doWarnExceptionExtend(warnExceptionClass, LogContext.doExtendWarnExceptionList) : ExceptionUtils.doWarnExceptionName(warnExceptionClass, LogContext.doWarnExceptionList);
  }

  @SuppressWarnings("unchecked")
  private static void genExtendWarnExceptionList() {
    for (String className : LogContext.doWarnExceptionList) {
      try {
        LogContext.doExtendWarnExceptionList.add((Class<? extends Throwable>) Class.forName(className));
      } catch (ClassNotFoundException e) {
        logger.error("init LogContext classNotFoundException, className [{}]", className);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static void genExtendWarnExceptionList(List<String> doWarnExceptionList) {
    for (String className : doWarnExceptionList) {
      try {
        LogContext.doExtendWarnExceptionList.add((Class<? extends Throwable>) Class.forName(className));
      } catch (ClassNotFoundException e) {
        logger.error("init LogContext classNotFoundException, className [{}]", className);
      }
    }
  }
}
