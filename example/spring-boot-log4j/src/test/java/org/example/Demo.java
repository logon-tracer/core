package org.example;

import logon.tracer.MailWarnService;
import logon.tracer.context.AlarmInfoContext;
import logon.tracer.helper.AlarmLogHelper;
import logon.tracer.helper.PrintLogHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo {

  @Test
  public void send() {
    AlarmInfoContext alarmInfoContext = null;
    try{
      int num = 10 / 0;
      System.out.println(num);
    } catch (Exception ex){
      StackTraceElement stackTraceElement = ex.getStackTrace()[0];
      alarmInfoContext = AlarmInfoContext.builder()
        .message(ex.getMessage())
        .throwableName(ex.getClass().getName())
        .loggerName("log4j")
        .threadName(Thread.currentThread().getName())
        .fileName(stackTraceElement.getFileName())
        .lineNumber(stackTraceElement.getLineNumber())
        .methodName(stackTraceElement.getMethodName())
        .className(stackTraceElement.getClassName())
        .build();
    }
    MailWarnService warnService = new MailWarnService(
      "smtp.163.com",
      "465",
      "2959698930@qq.com",
      "15557422216@163.com",
      "15557422216@163.com",
      "PSAVOVLHPJNOIYZY");
    warnService.send(alarmInfoContext, new Exception());
  }

  @Test
  public void text2() {
    PrintLogHelper printLogInstance = AlarmLogHelper.getPrintLogInstance(true);
    printLogInstance.error("124215");
  }
}
