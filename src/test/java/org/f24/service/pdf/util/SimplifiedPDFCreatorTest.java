package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Simplified;
import org.f24.service.pdf.impl.SimplifiedPDFCreator;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SimplifiedPDFCreatorTest {

    private SimplifiedPDFCreator pdfCreator;

    @Before
    public void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24simplified.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        F24Simplified form = new ObjectMapper().readValue(jsonString, F24Simplified.class);

        pdfCreator = new SimplifiedPDFCreator(form);
    }

    @Test
    public void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray()  {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }

}
