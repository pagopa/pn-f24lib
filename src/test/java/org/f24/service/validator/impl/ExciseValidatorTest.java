package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Excise;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.validator.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExciseValidatorTest {
    private ExciseValidator validator;
    private F24Excise form;

    @BeforeEach
    void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24excise.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Excise.class);

        validator = (ExciseValidator) ValidatorFactory.createValidator(form);
    }


}
