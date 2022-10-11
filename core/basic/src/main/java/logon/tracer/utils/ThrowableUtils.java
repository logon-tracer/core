package logon.tracer.utils;

import logon.tracer.context.AlarmInfoContext;
import logon.tracer.context.AlarmLogContext;
import logon.tracer.dto.AlarmMailContent;

import java.util.Objects;

public class ThrowableUtils {
  private static final String SEPARATOR = "\n";
  private static final String HTML_SEPARATOR = "<br />";

  public static AlarmMailContent mailSubjectContent(AlarmInfoContext context, Throwable throwable) {
    return new AlarmMailContent(context.getMessage(), defaultContent(context, throwable, HTML_SEPARATOR));
  }

  private static String defaultContent(AlarmInfoContext context, Throwable throwable, String separator) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("错误原因:").append(context.getMessage()).append(separator);
    if (!AlarmLogContext.getSimpleWarnInfo()) {
      stringBuilder.append("级别:").append(context.getLevel()).append(separator);
      if (Objects.nonNull(context.getThrowableName())) {
        stringBuilder.append("异常:").append(context.getThrowableName()).append(separator);
      }
      stringBuilder.append("线程:").append(context.getThreadName()).append(separator);
      stringBuilder.append("位置信息:").append(separator)
        .append("包名:").append(context.getClassName(), 0, context.getClassName().lastIndexOf(".")).append(separator)
        .append("&emsp;&emsp;&emsp;&emsp;").append("↓").append(separator)
        .append("文件名:").append(context.getFileName() != null ? context.getFileName() : "(Unknown Source)").append(separator)
        .append("&emsp;&emsp;&emsp;&emsp;").append("↓").append(separator)
        .append("类名:").append(context.getClassName()).append(separator)
        .append("&emsp;&emsp;&emsp;&emsp;").append("↓").append(separator)
        .append("方法名:").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : "(" + context.getLineNumber() + "行)");
    }
    if (AlarmLogContext.getPrintStackTrace()) {
      stringBuilder.append(printTrace(throwable));
    }
    return stringBuilder.toString();
  }

/*  private static String defaultContent1(AlarmInfoContext context, Throwable throwable, String separator) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(context.getMessage()).append(separator)
      .append("级别:").append(context.getLevel()).append(separator)
      .append("异常:").append(context.getThrowableName()).append(separator)
      .append("线程:").append(context.getThreadName()).append(separator)
      .append("位置信息:").append(context.getClassName()).append(".").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : context.getFileName() != null && context.getLineNumber() >= 0 ? "(" + context.getFileName() + ":" + context.getLineNumber() + ")" : context.getFileName() != null ? "(" + context.getFileName() + ")" : "(Unknown Source)");
    if (AlarmLogContext.getSimpleWarnInfo()) {
      return stringBuilder.toString();
    }
    return null;
  }*/

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
