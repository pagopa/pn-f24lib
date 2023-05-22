package org.f24.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Elid;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreatorFactory;
import org.f24.service.pdf.impl.ElidPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFServiceTest {

    private F24Elid form;
    private PDFService service = new PDFService();

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24elide.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Elid.class);
    }

    @Test
    void givenElidData_whenCreatePDF_thenCreatedByteArray() throws ResourceException {
        byte[] generatedPDF = service.generatePDF(form);

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }
}
