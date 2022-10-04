package logon.tracer.context;

import logon.tracer.dto.AlarmLogSimpleConfig;
import logon.tracer.dto.AlarmMailContent;
import logon.tracer.utils.ThrowableUtils;

public class DefaultAlarmMessageContext implements AlarmMessageContext {

  @Override
  public AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
    return ThrowableUtils.mailSubjectContent(context, throwable);
  }
}
