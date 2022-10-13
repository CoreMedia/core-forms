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

package com.tallence.formeditor.parser;

import com.coremedia.cap.struct.Struct;
import com.tallence.formeditor.elements.UsersMail;
import com.tallence.formeditor.validator.UsersMailValidator;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import static com.coremedia.cap.util.CapStructUtil.*;
import static java.util.Optional.ofNullable;

/**
 * Parser for elements of type {@link UsersMail}
 */
@Component
public class UsersMailParser extends AbstractFormElementParser<UsersMail> {

  private static final String COPY_TYPE = "copyType";
  private static final String LEGACY_COPY = "copy";

  public static final String parserKey = "UsersMail";

  @Override
  public UsersMail instantiateType(Struct elementData) {
    return new UsersMail();
  }

  @Override
  public void parseSpecialFields(UsersMail formElement, Struct elementData) {

    String copyType = getString(elementData, COPY_TYPE);
    if (StringUtils.isNotBlank(copyType)) {
      formElement.setCopyBoxOption(UsersMail.CopyBoxOption.valueOf(copyType));
    } else if (elementData.get(LEGACY_COPY) != null && elementData.getBoolean(LEGACY_COPY)) {
      formElement.setCopyBoxOption(UsersMail.CopyBoxOption.CHECKBOX);
    }

    ofNullable(getSubstruct(elementData, FORM_DATA_VALIDATOR)).ifPresent(validator -> {
      UsersMailValidator usersMailValidator = new UsersMailValidator();

      usersMailValidator.setMandatory(getBoolean(validator, FORM_VALIDATOR_MANDATORY));

      formElement.setValidator(usersMailValidator);
    });
  }

  @Override
  public String getParserKey() {
    return parserKey;
  }
}
