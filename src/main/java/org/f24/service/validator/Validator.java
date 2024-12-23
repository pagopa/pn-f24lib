package org.f24.service.validator;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.exception.ResourceException;

import java.io.IOException;

public interface Validator {

    void validate() throws ProcessingException, IOException, ResourceException;
    void validateWithoutTaxCode() throws ProcessingException, IOException, ResourceException;

}
