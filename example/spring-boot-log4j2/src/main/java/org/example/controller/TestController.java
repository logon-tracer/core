package org.example.controller;

import logon.tracer.aspect.Alarm;
import logon.tracer.helper.AlarmLogHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.exception.TestAspectException;
import org.example.exception.TestExtendsException;
import org.example.exception.TestExtendsRuntimeException;
import org.example.exception.TestImplException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Alarm(doWarnException = Exception.class, warnExceptionExtend = false)
public class TestController {
  private static Logger logger = LogManager.getLogger(TestController.class);

  @GetMapping("/test1")
  public void test1() {
    logger.error("test1", new TestImplException());
  }

  @GetMapping("/test2")
  public void test2() throws TestExtendsException {
    throw new TestExtendsException();
  }

  @GetMapping("/test3")
  public void test3() {
    throw new TestExtendsRuntimeException();
  }

  @GetMapping("/test4")
  @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
  public void test4() {
    throw new TestAspectException();
  }
}
