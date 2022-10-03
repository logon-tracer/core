package logon.tracer.context;

import logon.tracer.dto.LogSimpleConfig;
import logon.tracer.dto.MailContent;
import logon.tracer.utils.ThrowableUtils;

public class DefaultMessageContext implements MessageContext{
  /**
   * Customize the content sent to mail.
   *
   * @param context   The alarm log info.
   * @param throwable The throwable that was caught.
   * @param config    The config context.
   * @return Content sent to mail.
   */
  public MailContent mailContent(InfoContext context, Throwable throwable, LogSimpleConfig config) {
    return ThrowableUtils.mailSubjectContent(context, throwable);
  }
}
