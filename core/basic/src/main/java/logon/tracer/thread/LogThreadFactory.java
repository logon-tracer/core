package logon.tracer.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LogThreadFactory implements ThreadFactory {

  private static final ThreadGroup THREAD_GROUP = new ThreadGroup("alarmLog");

  private static final AtomicInteger THREAD_NUMBER = new AtomicInteger(1);

  private String prefix;

  private int newPriority;

  private boolean daemon;

  private LogThreadFactory() {
  }

  private LogThreadFactory(String prefix) {
    this.prefix = prefix;
  }

  public LogThreadFactory(String prefix, int newPriority, boolean daemon) {
    this.prefix = prefix;
    this.newPriority = newPriority;
    this.daemon = daemon;
  }

  public static LogThreadFactory create(String prefix) {
    return create(prefix, false, Thread.NORM_PRIORITY);
  }

  public static LogThreadFactory create(String prefix, boolean daemon) {
    return create(prefix, daemon, Thread.NORM_PRIORITY);
  }

  public static LogThreadFactory create(String prefix, boolean daemon, int newPriority) {
    return new LogThreadFactory(prefix, newPriority, daemon);
  }

  public Thread newThread(Runnable r) {
    Thread thread = new Thread(THREAD_GROUP, r, THREAD_GROUP.getName() + "-" + prefix + "-" + THREAD_NUMBER.getAndIncrement());
    thread.setDaemon(daemon);
    thread.setPriority(newPriority);
    return thread;
  }
}
