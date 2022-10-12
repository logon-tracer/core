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
    String mailHtml;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("错误原因:").append(context.getMessage()).append(separator);
    if (!AlarmLogContext.getSimpleWarnInfo()) {
      mailHtml = formatMailHtml(context, throwable);
/*      stringBuilder.append("级别:").append(context.getLevel()).append(separator);
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
        .append("方法名:").append(context.getMethodName()).append(isNativeMethod(context.getLineNumber()) ? "(Native Method)" : "(" + context.getLineNumber() + "行)");*/
    } else {
      mailHtml = stringBuilder.toString();
    }
    /*if (AlarmLogContext.getPrintStackTrace()) {
      stringBuilder.append(printTrace(throwable));
    }*/
    return mailHtml;
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

  private static StringBuilder getDefaultHtml() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(
      "    <div style=\"display: grid; font-size: 16px; grid-template-columns: 20%% 80%%; grid-auto-columns: 1fr; gap: 0px 10px; grid-auto-flow: row;\n" +
        "        grid-template-areas:\n" +
        "            'cause cause-text'\n" +
        "            'error-level error-level-text'\n" +
        "            'exc exc-text'\n" +
        "            'thread thread-text'\n" +
        "            'location location-empty'\n" +
        "            'package package-text'\n" +
        "            'down-arrow1 down-arrow1-empty'\n" +
        "            'file file-text'\n" +
        "            'down-arrow2 down-arrow2-empty'\n" +
        "            'classname classname-text'\n" +
        "            'down-arrow3 down-arrow3-empty'\n" +
        "            'method method-text'");
    if (AlarmLogContext.getPrintStackTrace()){
      stringBuilder.append(
        "            'stack-trace stack-trace-text';\">\n");
    } else {
      stringBuilder.append(";\">\n");
    }
    stringBuilder.append(
        "      <div style=\"grid-area: cause; margin-bottom: 10px; font-weight: bold; font-size: 18px;\">错误原因:</div>\n" +
        "      <div style=\"grid-area: cause-text; margin-bottom: 10px; color: #FC9768;\">%s</div>\n" +
        "      <div style=\"grid-area: error-level; margin-bottom: 10px; font-weight: bold; font-size: 18px;\">级别:</div>\n" +
        "      <div style=\"grid-area: error-level-text; margin-bottom: 10px; color: red; font-weight: bold;\">%s</div>\n" +
        "      <div style=\"grid-area: exc; margin-bottom: 10px; font-weight: bold; font-size: 18px;\">异常:</div>\n" +
        "      <div style=\"grid-area: exc-text; margin-bottom: 10px;\">%s</div>\n" +
        "      <div style=\"grid-area: thread; margin-bottom: 10px; font-weight: bold; font-size: 18px;\">线程:</div>\n" +
        "      <div style=\"grid-area: thread-text; margin-bottom: 10px;\">%s</div>\n" +
        "      <div style=\"grid-area: location; margin-bottom: 10px; font-weight: bold; font-size: 18px;\">位置信息:</div>\n" +
        "      <div style=\"grid-area: location-empty;\"></div>\n" +
        "      <div style=\"grid-area: package; margin: 0 0 10px 25%%; font-weight: bold; font-size: 18px;\">包名:</div>\n" +
        "      <div style=\"grid-area: package-text; margin-bottom: 10px;\">%s</div>\n" +
        "      <div style=\"grid-area: down-arrow1;\"></div>\n" +
        "      <div style=\"grid-area: down-arrow1-empty; margin-left: 20px;\">↓</div>\n" +
        "      <div style=\"grid-area: file; margin: 0 0 10px 25%%; font-weight: bold; font-size: 18px;\">文件名:</div>\n" +
        "      <div style=\"grid-area: file-text; margin-bottom: 10px;\">%s</div>\n" +
        "      <div style=\"grid-area: down-arrow2;\"></div>\n" +
        "      <div style=\"grid-area: down-arrow2-empty; margin-left: 20px;\">↓</div>\n" +
        "      <div style=\"grid-area: classname; margin: 0 0 10px 25%%; font-weight: bold; font-size: 18px;\">类名:</div>\n" +
        "      <div style=\"grid-area: classname-text; margin-bottom: 10px;\">%s</div>\n" +
        "      <div style=\"grid-area: down-arrow3;\"></div>\n" +
        "      <div style=\"grid-area: down-arrow3-empty; margin-left: 20px;\">↓</div>\n" +
        "      <div style=\"grid-area: method; margin: 0 0 10px 25%%; font-weight: bold; font-size: 18px;\">方法名:</div>\n" +
        "      <div style=\"grid-area: method-text; margin-bottom: 10px;\">%s</div>"
      );
    if (AlarmLogContext.getPrintStackTrace()) {
      stringBuilder.append(
        "      <div style=\"grid-area: stack-trace; margin: 0 0 10px 25%%; font-weight: bold; font-size: 18px;\">堆栈信息:</div>\n" +
        "      <div style=\"grid-area: stack-trace-text; margin-bottom: 10px;\">%s</div>"
      );
    }
    stringBuilder.append(
      "</div>"
    );
    return stringBuilder;
  }

  private static String formatMailHtml(AlarmInfoContext context, Throwable throwable) {
    Object[] args = {
      context.getMessage(),
      context.getLevel(),
      context.getThrowableName(),
      context.getThreadName(),
      context.getClassName().substring(0, context.getClassName().lastIndexOf(".")),
      context.getFileName() != null ? context.getFileName() : "(Unknown Source)",
      context.getClassName(),
      context.getMethodName() + (isNativeMethod(context.getLineNumber()) ? "(Native Method)" : "(" + context.getLineNumber() + "行)"),
      AlarmLogContext.getPrintStackTrace() ? printTrace(throwable) : null
    };
    return String.format(getDefaultHtml().toString(), args);
  }
}
