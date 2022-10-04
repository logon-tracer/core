package org.example.config;

import logon.tracer.context.AlarmInfoContext;
import logon.tracer.context.DefaultAlarmMessageContext;
import logon.tracer.dto.AlarmLogSimpleConfig;
import logon.tracer.dto.AlarmMailContent;
import org.springframework.stereotype.Component;

@Component
public class CustomAlarmMessageContext extends DefaultAlarmMessageContext {

}
