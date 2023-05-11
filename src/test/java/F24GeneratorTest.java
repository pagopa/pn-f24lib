import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Excise;
import org.f24.dto.form.F24Form;
import org.f24.dto.form.F24Simplified;
import org.f24.dto.form.F24Standard;
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
        String simplifiedJson = "src/test/resources/input/f24simplified.json";
        //String simplifiedJson = "F24-PDF\\src\\test\\resources\\input\\f24simplified.json";
        String simplifiedString = new String(Files.readAllBytes(Paths.get(simplifiedJson)));

        F24Simplified f24Simplified = new ObjectMapper().readValue(simplifiedString, F24Simplified.class);
        Validator simplifiedValidator = ValidatorFactory.createValidator(f24Simplified);
        simplifiedValidator.validate();

        Files.write(Path.of("src/test/resources/output/f24simplified.pdf"), PDFCreatorFactory.createPDFCreator(f24Simplified).createPDF());
        //Files.write(Path.of("F24-PDF\\src\\test\\resources\\output\\f24simplified.pdf"), PDFCreatorFactory.createPDFCreator(f24Simplified).createPDF());

        String standardJson = "src/test/resources/input/f24standard.json";
        //String standardJson = "F24-PDF\\src\\test\\resources\\input\\f24standard.json";
        String standardString = new String(Files.readAllBytes(Paths.get(standardJson)));

        F24Standard f24Standard = new ObjectMapper().readValue(standardString, F24Standard.class);
        Validator standardValidator = ValidatorFactory.createValidator(f24Standard);
        standardValidator.validate();

        Files.write(Path.of("src/test/resources/output/f24standard.pdf"), PDFCreatorFactory.createPDFCreator(f24Standard).createPDF());
        //Files.write(Path.of("F24-PDF\\src\\test\\resources\\output\\f24standard.pdf"), PDFCreatorFactory.createPDFCreator(f24Standard).createPDF());

        String exciseJson = "src/test/resources/input/f24excise.json";
        //String standardJson = "F24-PDF\\src\\test\\resources\\input\\f24excise.json";
        String exciseString = new String(Files.readAllBytes(Paths.get(exciseJson)));

        F24Excise f24Excise = new ObjectMapper().readValue(exciseString, F24Excise.class);
        Validator exciseValidator = ValidatorFactory.createValidator(f24Excise);
        exciseValidator.validate();

        Files.write(Path.of("src/test/resources/output/f24excise1.pdf"), PDFCreatorFactory.createPDFCreator(f24Excise).createPDF());
        //Files.write(Path.of("F24-PDF\\src\\test\\resources\\output\\f24excise.pdf"), PDFCreatorFactory.createPDFCreator(f24Excise).createPDF());
    }

}
