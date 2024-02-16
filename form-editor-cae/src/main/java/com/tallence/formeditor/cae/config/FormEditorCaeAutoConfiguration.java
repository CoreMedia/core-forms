package com.tallence.formeditor.cae.config;

import com.coremedia.blueprint.common.services.context.CurrentContextService;
import com.coremedia.springframework.customizer.Customize;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import com.tallence.formeditor.FormEditorConfiguration;
import com.tallence.formeditor.FormElementFactory;
import com.tallence.formeditor.cae.FormFreemarkerFacade;
import com.tallence.formeditor.cae.handler.ReCaptchaService;
import com.tallence.formeditor.cae.handler.ReCaptchaServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {
        "com.tallence.formeditor.cae"
})
@ImportResource(value = {
        "classpath:/framework/spring/blueprint-services.xml"
}, reader = ResourceAwareXmlBeanDefinitionReader.class)
@Import({
        FormEditorConfiguration.class
})
public class FormEditorCaeAutoConfiguration {

  @Bean
  public ReCaptchaService reCaptchaService(@Value("${google.reCaptcha.website-secret}") String websiteSecret,
                                           @Value("${google.reCaptcha.server-secret}") String serverSecret) {
    return new ReCaptchaServiceImpl(new ReCaptchaServiceImpl.ReCaptchaAuthentication(websiteSecret, serverSecret));
  }

  // TODO: Configure cache capacity for FormConfigCacheKey
  /*
  <bean id="formEditorCacheCapacityConfigurer" class="com.coremedia.cache.CacheCapacityConfigurer" init-method="init">
    <property name="cache" ref="cache"/>
    <property name="capacities">
      <map>
        <entry key="com.tallence.formeditor.cae.serializer.FormConfigCacheKey" value="${formConfigCacheKey.cache.capacity:100}"/>
      </map>
    </property>
  </bean>
   */

  @Customize("freemarkerSharedVariables")
  @Bean(autowireCandidate = false)
  public Map<String, FormFreemarkerFacade> formFreemarkerSharedVariablesCustomizer(
          FormElementFactory formElementFactory,
          ReCaptchaService reCaptchaService,
          CurrentContextService currentContextService) {
    FormFreemarkerFacade formFreemarkerFacade = new FormFreemarkerFacade(formElementFactory, reCaptchaService, currentContextService);
    return Map.of("formFreemarkerFacade", formFreemarkerFacade);
  }

  @Customize("freemarkerConfigurer.autoImports")
  @Bean(autowireCandidate = false)
  public Map<String, String> formFreemarkerConfigurerAutoImportsCustomizer() {
    return Map.of("form", "/lib/form/form.ftl");
  }

}
