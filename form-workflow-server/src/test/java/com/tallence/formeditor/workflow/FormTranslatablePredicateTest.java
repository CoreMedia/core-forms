package com.tallence.formeditor.workflow;

import com.coremedia.cap.common.CapPropertyDescriptor;
import com.coremedia.cap.common.CapPropertyDescriptorType;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FormTranslatablePredicateTest {

  private CapPropertyDescriptor formdata;
  private FormTranslatablePredicate predicate;

  @BeforeEach
  void setUp() {
    formdata = descriptor("formData", CapPropertyDescriptorType.STRUCT);
    ContentType contentType = mock(ContentType.class);
    when(contentType.getDescriptor("formData")).thenReturn(formdata);
    ContentRepository repository = mock(ContentRepository.class);
    when(repository.getContentType("FormEditor")).thenReturn(contentType);
    predicate = new FormTranslatablePredicate(repository);
  }

  private static CapPropertyDescriptor descriptor(String name, CapPropertyDescriptorType type) {
    CapPropertyDescriptor descriptor = mock(CapPropertyDescriptor.class);
    when(descriptor.getName()).thenReturn(name);
    when(descriptor.getType()).thenReturn(type);
    return descriptor;
  }


  @Test
  void stringValueIsTranslatable() {
    assertTrue(predicate.isTranslatable(valuePath(descriptor("name", CapPropertyDescriptorType.STRING))));
    assertTrue(predicate.isTranslatable(valuePath(descriptor("hint", CapPropertyDescriptorType.STRING))));
  }

  @Test
  void stringGroupElementsValueIsTranslatable() {
    List<CapPropertyDescriptor> fields = new ArrayList();
    fields.add(descriptor("groupElements", CapPropertyDescriptorType.STRUCT));
    fields.add(descriptor("50 und älter", CapPropertyDescriptorType.STRUCT));
    assertTrue(predicate.isTranslatable(valuePath(fields)));
  }

  private List<CapPropertyDescriptor> valuePath(CapPropertyDescriptor value) {
    return List.of(formdata,
      descriptor("formElements", CapPropertyDescriptorType.STRUCT),
      descriptor("1192266228", CapPropertyDescriptorType.STRUCT),
      value);
  }

  private List<CapPropertyDescriptor> valuePath(List<CapPropertyDescriptor> value) {
    List<CapPropertyDescriptor> formElements = new ArrayList<>(List.of(formdata,
      descriptor("formElements", CapPropertyDescriptorType.STRUCT),
      descriptor("1192266228", CapPropertyDescriptorType.STRUCT)));
    formElements.addAll(value);
    return formElements;
  }

}
