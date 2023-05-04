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
import java.nio.file.Paths;
import java.text.ParseException;


public class F24GeneratorTest {

    public static void main(String[] args) throws IOException, ResourceException, ProcessingException, ParseException {
        String jsonFile = "src/test/resources/input/f24simplified.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));

        F24Simplified f24Form = new ObjectMapper().readValue(jsonString, F24Simplified.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("src/test/resources/output/f24simplified.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }
}
