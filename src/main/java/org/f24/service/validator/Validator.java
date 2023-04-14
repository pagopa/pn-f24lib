package org.f24.service.validator;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import java.io.IOException;

public interface Validator {

    void validate() throws ProcessingException, IOException;

}
