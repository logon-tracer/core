package logon.tracer;

import logon.tracer.context.InfoContext;

public interface LogWarnService {

  boolean send(InfoContext context, Throwable throwable);

}
