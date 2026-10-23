package com.tallence.formeditor.workflow;

import com.coremedia.cap.content.ContentRepository;
import com.coremedia.translate.TranslatablePredicate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Registers the translatable predicate for the editorial values of {@code CMSection.layout}.
 * All {@link TranslatablePredicate} beans are combined by the translate configuration, so the
 * values become part of XLIFF exports (translation workflows, DeepL) without further configuration.
 */
@AutoConfiguration
public class FormWorkflowServerAutoConfiguration {

  @Bean
  public TranslatablePredicate formTranslatablePredicate(ContentRepository contentRepository) {
    return new FormTranslatablePredicate(contentRepository);
  }
}
