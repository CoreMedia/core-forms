package com.tallence.formeditor.workflow;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import com.coremedia.translate.TranslatablePredicate;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Predicate to determine if a value of a form element is translatable.
 * <p>
 * The predicate checks if the value path is of the form:
 * <pre>
 * formData › formElements ›  element › name
 * </pre>
 * and if the value is of type {@code STRING}.
 * <p>
  * This predicate is combined (OR) with the default predicates by
 * {@code com.coremedia.translate.item.TranslatablePredicateConfiguration}.
 */
public class FormTranslatablePredicate implements TranslatablePredicate {

  private static final Logger LOG = LoggerFactory.getLogger(FormTranslatablePredicate.class);

  static final String CONTENT_TYPE = "FormEditor";
  static final String FORM_DATA = "formData";
  static final String NAME = "name";

  /** formData › formElements ›  element › &lt;key&gt; */
  private static final int VALUE_PATH_LENGTH = 4;

  @Nullable
  private final CapPropertyDescriptor layoutDescriptor;

  public FormTranslatablePredicate(@NonNull ContentRepository contentRepository) {
    ContentType contentType = contentRepository.getContentType(CONTENT_TYPE);
    layoutDescriptor = contentType == null ? null : contentType.getDescriptor(FORM_DATA);
    if (layoutDescriptor == null) {
      LOG.warn("Content type {} with property {} not found, CMSection values will not be translated.", CONTENT_TYPE, FORM_DATA);
    }
  }

  @Override
  public boolean isTranslatable(@NonNull List<? extends CapPropertyDescriptor> descriptors) {
    if (layoutDescriptor == null
            || descriptors.size() != VALUE_PATH_LENGTH
            || !layoutDescriptor.equals(descriptors.get(0))
            || !NAME.equals(descriptors.get(3).getName())) {
      return false;
    }

    CapPropertyDescriptor value = descriptors.get(VALUE_PATH_LENGTH - 1);
    boolean translatable = switch (value.getType()) {
      case STRING -> true;
      default -> false;
    };
    LOG.debug("CMSection layout value {} ({}) translatable: {}", value.getName(), value.getType(), translatable);
    return translatable;
  }



  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + CONTENT_TYPE + "." + FORM_DATA + "." + NAME+".*}";
  }
}
