package com.tallence.formeditor.workflow;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.common.CapPropertyDescriptorType;
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
  static final String HINT = "hint";
  static final String GROUPS = "groupElements";

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
    if (layoutDescriptor == null || descriptors.size() < 4) {
      return false;
    }

    // Verify root is formData
    if (!layoutDescriptor.equals(descriptors.get(0))) {
      return false;
    }

    String propertyName = descriptors.get(3).getName();

    // 1. Mandatory name and hint properties
    if (NAME.equals(propertyName) || HINT.equals(propertyName)) {
      boolean translatable = descriptors.size() == 4
        && descriptors.get(3).getType() == CapPropertyDescriptorType.STRING;
      LOG.debug("Form element property '{}' translatable: {}", propertyName, translatable);
      return translatable;
    }

    // 2. Optional groupElements struct properties
    if (GROUPS.equals(propertyName)) {
      // Path targets the group option key level (e.g., "50 und älter", "Computer")
      boolean translatable = descriptors.size() == 5
        && descriptors.get(4).getType() == CapPropertyDescriptorType.STRUCT;
      LOG.debug("Group element option translatable: {}", translatable);
      return translatable;
    }

    return false;
  }



  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + CONTENT_TYPE + "." + FORM_DATA + "." + NAME+".*}";
  }
}
