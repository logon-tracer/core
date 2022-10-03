package logon.tracer;

import logon.tracer.context.InfoContext;
import logon.tracer.context.LogContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseWarnService implements LogWarnService {
  private final Logger logger = LoggerFactory.getLogger(BaseWarnService.class);

  private int maxRetryTimes;

  private int retrySleepMillis;

  public BaseWarnService() {
    this(LogContext.getMaxRetryTimes(), LogContext.getRetrySleepMillis());
  }

  public BaseWarnService(int maxRetryTimes, int retrySleepMillis) {
    this.maxRetryTimes = maxRetryTimes;
    this.retrySleepMillis = retrySleepMillis;
  }

  public boolean send(InfoContext context, Throwable throwable) {
    int retryTimes = 0;
    do {
      try {
        doSend(context, throwable);
        return true;
      } catch (Exception e) {
        if (retryTimes + 1 > this.maxRetryTimes) {
          return false;
        }
        int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
        logger.warn("send warn message error, retry the {} time after {} ms", retryTimes + 1, sleepMillis);
        try {
          Thread.sleep(sleepMillis);
        } catch (InterruptedException ex) {
          Thread.currentThread().interrupt();
        }
      }
    } while (retryTimes++ < this.maxRetryTimes);
    return false;
  }

  protected abstract void doSend(InfoContext context, Throwable throwable) throws Exception;
}
