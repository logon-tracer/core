package org.example.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.exception.TestAspectException;
import org.example.exception.TestExtendsException;
import org.example.exception.TestExtendsRuntimeException;
import org.example.exception.TestImplException;
import org.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  private static Logger logger = LogManager.getLogger(TestController.class);
  @Autowired
  private TestService testService;

  @GetMapping("/test1")
  public void test1() {
    logger.error("test1", new TestImplException());
  }

  @GetMapping("/test2")
  public void test2() throws TestExtendsException {
    logger.error("test2", new TestExtendsException());
  }

  @GetMapping("/test3")
  public void test3() {
    logger.error("test3", new TestExtendsRuntimeException());
  }

  @GetMapping("/test4")
//  @Alarm(doWarnException = TestAspectException.class, warnExceptionExtend = false)
  public void test4() {
    testService.test1();
    logger.error("test4", new TestAspectException());
  }
}
