package logon.tracer;

import logon.tracer.context.AlarmInfoContext;

public interface AlarmLogWarnService {

  boolean send(AlarmInfoContext context, Throwable throwable);

}
