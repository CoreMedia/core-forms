package com.tallence.formeditor;

import com.tallence.formeditor.elements.FormElement;
import com.tallence.formeditor.parser.AbstractFormElementParser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@AutoConfiguration
@ComponentScan(basePackages = "com.tallence.formeditor.parser")
public class FormEditorConfiguration {

  @Bean
  public FormElementFactory formElementFactory(List<AbstractFormElementParser<? extends FormElement<?>>> parsers) {
    return new FormElementFactory(parsers);
  }

}
