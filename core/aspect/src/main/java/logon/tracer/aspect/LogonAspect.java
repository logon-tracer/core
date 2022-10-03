package logon.tracer.aspect;

import logon.tracer.context.InfoContext;
import logon.tracer.context.LogContext;
import logon.tracer.factor.LogWarnServiceFactory;
import logon.tracer.thread.LogThreadFactory;
import logon.tracer.utils.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Aspect
public class LogonAspect {

  public static final String POINTCUT_SIGN =
    "@within(com.future94.alarm.log.aspect.Logon) || @annotation(com.future94.alarm.log.aspect.Logon)";

  private ExecutorService executorService = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), LogThreadFactory.create("logon-tracer-aspect-", false));

  @Pointcut(POINTCUT_SIGN)
  public void pointcut() {

  }

  @AfterThrowing(value = "pointcut()", throwing = "ex")
  public void doRetryProcess(JoinPoint joinPoint, Throwable ex) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Logon logonMethod = signature.getMethod().getAnnotation(Logon.class);
    Logon logonClass = signature.getMethod().getDeclaringClass().getAnnotation(Logon.class);
    if (doWarnProcess(logonMethod, ex)
      || doWarnProcess(logonClass, ex)
      || LogContext.doWarnException(ex)
      || ExceptionUtils.doWarnExceptionInstance(ex)) {
      String threadName = Thread.currentThread().getName();
      StackTraceElement stackTraceElement = ex.getStackTrace()[0];
      executorService.execute(() -> LogWarnServiceFactory.getServiceList().forEach(logWarnService -> logWarnService.send(
        InfoContext.builder()
          .message(ex.getMessage())
          .throwableName(ex.getClass().getName())
          .loggerName(joinPoint.getSignature().getDeclaringTypeName())
          .threadName(threadName)
          .fileName(stackTraceElement.getFileName())
          .lineNumber(stackTraceElement.getLineNumber())
          .methodName(stackTraceElement.getMethodName())
          .className(stackTraceElement.getClassName())
          .build()
        , ex)));
    }
    throw ex;
  }

  private boolean doWarnProcess(Logon logon, Throwable ex) {
    if (Objects.isNull(logon)) {
      return false;
    }
    Class<? extends Throwable>[] doExtendWarnExceptionClasses = logon.doWarnException();
    if (logon.warnExceptionExtend()) {
      return ExceptionUtils.doWarnExceptionExtend(ex, Arrays.asList(doExtendWarnExceptionClasses));
    } else {
      List<String> doWarnExceptionList = new ArrayList<>(doExtendWarnExceptionClasses.length);
      for (Class<? extends Throwable> exceptionClass : doExtendWarnExceptionClasses) {
        doWarnExceptionList.add(exceptionClass.getName());
      }
      return ExceptionUtils.doWarnExceptionName(ex, doWarnExceptionList);
    }
  }
}
