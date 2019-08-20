/*
 * Copyright 2018 Tallence AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tallence.formeditor.studio.validator.field;

import com.coremedia.rest.validation.Issues;
import com.coremedia.rest.validation.Severity;
import com.tallence.formeditor.contentbeans.FormEditor;

import static com.tallence.formeditor.contentbeans.FormEditor.FORM_DATA;

abstract class AbstractFormValidator {

  void addErrorIssue(Issues issues, String formElementId, String propertyName, String errorCode, Object... objects) {
    String property = FORM_DATA + "." + FormEditor.FORM_ELEMENTS + "." + formElementId + "." + propertyName;
    issues.addIssue(Severity.ERROR, property, errorCode, objects);
  }
}
