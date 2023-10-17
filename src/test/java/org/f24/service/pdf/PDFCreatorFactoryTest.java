package org.f24.service.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Elid;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.ElidPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFCreatorFactoryTest {

    private F24Elid form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24elide.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Elid.class);
    }

    @Test
    void givenElidData_whenCreatePDFCreator_thenElidPDFCreatorCreated() throws ResourceException {
        assertTrue(PDFCreatorFactory.createPDFCreator(form) instanceof ElidPDFCreator);
    }

}
