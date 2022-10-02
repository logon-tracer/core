package logon.tracer.enums;


import lombok.Getter;

@Getter
public enum WarnMethodEnum {

  /**
   * mail
   */
  MAIL("");

  private String className;

  WarnMethodEnum(String className) {
    this.className = className;
  }
}
