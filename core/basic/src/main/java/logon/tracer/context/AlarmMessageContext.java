package logon.tracer.context;

import logon.tracer.dto.AlarmMailContent;
import logon.tracer.dto.AlarmLogSimpleConfig;

public interface AlarmMessageContext {

  /**
   * Customize the content sent to mail.
   * @param context   The alarm log info.
   * @param throwable     The throwable that was caught.
   * @param config    The config context.
   * @return Content sent to mail.
   */
  AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config);
}
