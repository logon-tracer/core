package org.example.config;

import logon.tracer.context.AlarmInfoContext;
import logon.tracer.context.AlarmMessageContext;
import logon.tracer.dto.AlarmLogSimpleConfig;
import logon.tracer.dto.AlarmMailContent;
import org.springframework.stereotype.Component;

//@Component
public class CustomAlarmMessageContext1 implements AlarmMessageContext {
  *//**
   * Customize the content sent to mail.
   *
   * @param context   The alarm log info.
   * @param throwable The throwable that was caught.
   * @param config    The config context.
   * @return Content sent to mail.
   *//*
  @Override
  public AlarmMailContent mailContent(AlarmInfoContext context, Throwable throwable, AlarmLogSimpleConfig config) {
    return new AlarmMailContent(context.getMessage(), context.getClassName());
  }
}*/
