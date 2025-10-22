package com.tallence.formeditor.validator;

import com.tallence.formeditor.elements.FormElement;

import java.util.List;

public class PageElementValidator implements Validator<List<FormElement<Object>>> {

  @Override
  public List<ValidationFieldError> validate(List<FormElement<Object>> value) {
    throw new IllegalStateException("Page elements cannot be validated. Flatten all elements first and skip the page elements.");
  }

  @Override
  public boolean isMandatory() {
    return false;
  }
}
