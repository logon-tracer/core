package logon.tracer.utils;

import logon.tracer.context.InfoContext;
import logon.tracer.context.LogContext;
import logon.tracer.dto.MailContent;

import java.util.Objects;

public class ThrowableUtils {
  private static final String SEPARATOR = "\n";
  private static final String HTML_SEPARATOR = "<br />";

  public static String workWeixinContent(InfoContext context, Throwable throwable) {
    return defaultContent(context, throwable, SEPARATOR);
  }

  public static String dingtalkContent(InfoContext context, Throwable throwable) {
    return defaultContent(context, throwable, SEPARATOR);
  }

  public static MailContent mailSubjectContent(InfoContext context, Throwable throwable) {
    return new MailContent(context.getMessage(), defaultContent(context, throwable, HTML_SEPARATOR));
  }

  private static String defaultContent(InfoContext context, Throwable throwable, String separator) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(context.getMessage()).append(separator);
    if (!LogContext.getSimpleWarnInfo()) {
      stringBuilder.append("级别:").append(context.getLevel()).append(separator);
      if (Objects.nonNull(context.getThrowableName())) {
        stringBuilder.append("异常:").append(context.getThrowableName()).append(separator);
      }
      stringBuilder.append("线程:").append(context.getThreadName()).append(separator);
      stringBuilder.append("位置信息:").append(context.getClassName()).append(".").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : context.getFileName() != null && context.getLineNumber() >= 0 ? "(" + context.getFileName() + ":" + context.getLineNumber() + ")" : context.getFileName() != null ? "(" + context.getFileName() + ")" : "(Unknown Source)");
      stringBuilder.append(separator);
    }
    if (LogContext.getPrintStackTrace()) {
      stringBuilder.append(printTrace(throwable));
    }
    return stringBuilder.toString();
  }

  private static String printTrace(Throwable throwable) {
    if (Objects.isNull(throwable)) {
      return "";
    }
    StackTraceElement[] trace = throwable.getStackTrace();
    StringBuilder content = new StringBuilder();
    content.append(throwable.toString());
    for (StackTraceElement traceElement : trace) {
      content.append("\n    at ").append(traceElement);
    }
    return content.toString();
  }

  private static boolean isNativeMethod(int lineNumber) {
    return lineNumber == -2;
  }
}
