package com.tallence.formeditor.cae.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "core-forms.cae")
public class FormEditorCaeConfigurationProperties {

  private String googleReCaptchaWebsiteSecret = "";
  private String googleReCaptchaServerSecret = "";

  public String getGoogleReCaptchaWebsiteSecret() {
    return googleReCaptchaWebsiteSecret;
  }

  public void setGoogleReCaptchaWebsiteSecret(String googleReCaptchaWebsiteSecret) {
    this.googleReCaptchaWebsiteSecret = googleReCaptchaWebsiteSecret;
  }

  public String getGoogleReCaptchaServerSecret() {
    return googleReCaptchaServerSecret;
  }

  public void setGoogleReCaptchaServerSecret(String googleReCaptchaServerSecret) {
    this.googleReCaptchaServerSecret = googleReCaptchaServerSecret;
  }
}
