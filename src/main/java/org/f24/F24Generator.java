package org.f24;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreatorFactory;
import org.f24.service.validator.Validator;
import org.f24.service.validator.ValidatorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;

public class F24Generator {

    public static void main(String[] args) throws IOException, ResourceException, ProcessingException, ParseException {
        String json = "";
        F24Simplified f24Form = new ObjectMapper().readValue(json, F24Simplified.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }

}
