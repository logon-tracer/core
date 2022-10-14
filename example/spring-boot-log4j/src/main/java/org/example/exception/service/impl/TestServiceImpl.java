package org.example.exception.service.impl;

import logon.tracer.aspect.Alarm;
import org.example.exception.service.TestService;
import org.springframework.stereotype.Service;

@Service
@Alarm(doWarnException = Exception.class, warnExceptionExtend = true)
public class TestServiceImpl implements TestService {

  @Override
  public String test1() {
      int num = 10 / 0;
      System.out.println(num);
    return "aaaa";
  }
}

