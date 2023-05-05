package org.f24.service.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Standard;
import org.f24.service.pdf.impl.StandardPDFCreator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StandardPDFCreatorTest {

    private StandardPDFCreator pdfCreator;

    @Before
    public void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24standard.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        F24Standard form = new ObjectMapper().readValue(jsonString, F24Standard.class);

        pdfCreator = new StandardPDFCreator(form);
    }

    @Test
    public void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray()  {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }

}
