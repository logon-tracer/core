package logon.tracer.autoconfigure;

import logon.tracer.MailWarnService;
import logon.tracer.context.AlarmLogContext;
import logon.tracer.context.AlarmMessageContext;
import logon.tracer.factor.AlarmLogWarnServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Slf4j
@Configuration
@EnableConfigurationProperties(AlarmLogConfig.class)
public class AlarmLogAutoConfiguration {
  @Configuration
  @ConditionalOnProperty(name = "spring.alarm-log.warn.mail.enabled", havingValue = "true")
  @EnableConfigurationProperties(MailConfig.class)
  static class MailWarnServiceMethod {

    @Bean
    @ConditionalOnMissingBean(MailWarnService.class)
    public MailWarnService mailWarnService(final MailConfig mailConfig) {
      MailWarnService mailWarnService = new MailWarnService(mailConfig.getSmtpHost(), mailConfig.getSmtpPort(), mailConfig.getTo(), mailConfig.getFrom(), mailConfig.getUsername(), mailConfig.getPassword());
      mailWarnService.setSsl(mailConfig.getSsl());
      mailWarnService.setDebug(mailConfig.getDebug());
      return mailWarnService;
    }

    @Autowired
    void setDataChangedListener(MailWarnService mailWarnService) {
      AlarmLogWarnServiceFactory.setAlarmLogWarnService(mailWarnService);
    }
  }


  /**
   * The code position is important, in order after BaseWarnService.
   */
  @Configuration
  static class AlarmLogInit {

    @Autowired
    void setAlarmLogConfig(AlarmLogConfig alarmLogConfig) {
      Optional.ofNullable(alarmLogConfig.getDoWarnException()).ifPresent(AlarmLogContext::addDoWarnExceptionList);
      Optional.ofNullable(alarmLogConfig.getWarnExceptionExtend()).ifPresent(AlarmLogContext::setWarnExceptionExtend);
      Optional.ofNullable(alarmLogConfig.getPrintStackTrace()).ifPresent(AlarmLogContext::setPrintStackTrace);
      Optional.ofNullable(alarmLogConfig.getSimpleWarnInfo()).ifPresent(AlarmLogContext::setSimpleWarnInfo);
      Optional.ofNullable(alarmLogConfig.getMaxRetryTimes()).ifPresent(AlarmLogContext::setMaxRetryTimes);
      Optional.ofNullable(alarmLogConfig.getRetrySleepMillis()).ifPresent(AlarmLogContext::setRetrySleepMillis);
    }

    @Autowired
    void setAlarmMessageContext(ObjectProvider<AlarmMessageContext> alarmMessageContext) {
      alarmMessageContext.ifAvailable(AlarmLogContext::setAlarmMessageContext);
    }
  }
}
