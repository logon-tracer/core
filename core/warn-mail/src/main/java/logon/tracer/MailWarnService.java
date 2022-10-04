package logon.tracer;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;
import logon.tracer.context.AlarmInfoContext;
import logon.tracer.context.AlarmLogContext;
import logon.tracer.dto.AlarmMailContent;

public class MailWarnService extends BaseWarnService{

  private final String smtpHost;

  private final String smtpPort;

  private final String to;

  private final String from;

  private final String username;

  private final String password;

  private Boolean ssl = true;

  private Boolean debug = false;

  public MailWarnService(String smtpHost, String smtpPort, String to, String from, String username, String password) {
    this.smtpHost = smtpHost;
    this.smtpPort = smtpPort;
    this.to = to;
    this.from = from;
    this.username = username;
    this.password = password;
  }

  public Boolean getSsl() {
    return ssl;
  }

  public void setSsl(Boolean ssl) {
    this.ssl = ssl;
  }

  public Boolean getDebug() {
    return debug;
  }

  public void setDebug(Boolean debug) {
    this.debug = debug;
  }

  @Override
  protected void doSend(AlarmInfoContext context, Throwable throwable) throws Exception {
    System.out.println("////////////////");
    System.out.println(smtpHost);
    System.out.println("////////////////");
    MailSSLSocketFactory sf = new MailSSLSocketFactory();
    sf.setTrustAllHosts(true);
    Properties props = new Properties();
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.smtp.host", smtpHost);
    props.setProperty("mail.smtp.port", smtpPort);
    props.put("mail.smtp.ssl.enable", true);
    props.put("mail.smtp.ssl.socketFactory", sf);
    Session session = Session.getInstance(props);
    session.setDebug(false);
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(from));
    for (String toUser : to.split(",")) {
      msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toUser));
    }
    AlarmMailContent alarmMailContent = AlarmLogContext.getAlarmMessageContext().mailContent(context, throwable, AlarmLogContext.getSimpleConfig());
    msg.setSubject(alarmMailContent.getSubject(), "UTF-8");
    msg.setContent(alarmMailContent.getContent(), "text/html;charset=UTF-8");
    msg.setSentDate(new Date());
    Transport transport = session.getTransport();
    transport.connect(username, password);
    transport.sendMessage(msg, msg.getAllRecipients());
    transport.close();
  }
}
