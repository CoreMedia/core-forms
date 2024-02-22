package com.tallence.formeditor.cae.config;

import com.coremedia.blueprint.common.services.context.CurrentContextService;
import com.coremedia.springframework.customizer.Customize;
import com.coremedia.springframework.xml.ResourceAwareXmlBeanDefinitionReader;
import com.tallence.formeditor.FormEditorConfiguration;
import com.tallence.formeditor.FormElementFactory;
import com.tallence.formeditor.cae.FormFreemarkerFacade;
import com.tallence.formeditor.cae.handler.ReCaptchaService;
import com.tallence.formeditor.cae.handler.ReCaptchaServiceImpl;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import java.util.Map;

@AutoConfiguration
@ComponentScan(basePackages = {
        "com.tallence.formeditor.cae"
})
@ImportResource(value = {
        "classpath:/framework/spring/blueprint-services.xml"
}, reader = ResourceAwareXmlBeanDefinitionReader.class)
@Import({
        FormEditorConfiguration.class
})
@EnableConfigurationProperties({
        FormEditorCaeConfigurationProperties.class
})
public class FormEditorCaeAutoConfiguration {

  @Bean
  public ReCaptchaService reCaptchaService(FormEditorCaeConfigurationProperties formEditorCaeConfigurationProperties) {
    return new ReCaptchaServiceImpl(new ReCaptchaServiceImpl.ReCaptchaAuthentication(formEditorCaeConfigurationProperties.getGoogleReCaptchaWebsiteSecret(), formEditorCaeConfigurationProperties.getGoogleReCaptchaServerSecret()));
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

  @Bean
  public FormFreemarkerFacade formFreemarkerFacade(FormElementFactory formElementFactory,
                                                   ReCaptchaService reCaptchaService,
                                                   CurrentContextService currentContextService ) {
    return new FormFreemarkerFacade(formElementFactory, reCaptchaService, currentContextService);
  }

  @Customize("freemarkerSharedVariables")
  @Bean(autowireCandidate = false)
  public Map<String, FormFreemarkerFacade> formFreemarkerSharedVariablesCustomizer(@NonNull FormFreemarkerFacade formFreemarkerFacade) {
    return Map.of("formFreemarkerFacade", formFreemarkerFacade);
  }

  @Customize("freemarkerConfigurer.autoImports")
  @Bean(autowireCandidate = false)
  public Map<String, String> formFreemarkerConfigurerAutoImportsCustomizer() {
    return Map.of("form", "/lib/form/form.ftl");
  }

}
