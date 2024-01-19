import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.*;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreatorFactory;
import org.f24.service.validator.Validator;
import org.f24.service.validator.ValidatorFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;


public class F24Generator {

    public static void main(String[] args) throws IOException, ResourceException, ProcessingException {
        //String simplifiedJson = "src/test/resources/input/f24simplified.json";
        String simplifiedJson = "src\\test\\resources\\input\\f24simplified.json";
        String simplifiedString = new String(Files.readAllBytes(Paths.get(simplifiedJson)));

        F24Simplified f24Simplified = new ObjectMapper().readValue(simplifiedString, F24Simplified.class);
        Validator simplifiedValidator = ValidatorFactory.createValidator(f24Simplified);
        simplifiedValidator.validate();

        //Files.write(Path.of("src/test/resources/output/simplified.pdf"), PDFCreatorFactory.createPDFCreator(f24Simplified).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24simplified.pdf"), PDFCreatorFactory.createPDFCreator(f24Simplified).createPDF());

        //String standardJson = "src/test/resources/input/f24standard.json";
        String standardJson = "src\\test\\resources\\input\\f24standard.json";
        String standardString = new String(Files.readAllBytes(Paths.get(standardJson)));

        F24Standard f24Standard = new ObjectMapper().readValue(standardString, F24Standard.class);
        Validator standardValidator = ValidatorFactory.createValidator(f24Standard);
        standardValidator.validate();

        //Files.write(Path.of("src/test/resources/output/standard.pdf"), PDFCreatorFactory.createPDFCreator(f24Standard).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24standard.pdf"), PDFCreatorFactory.createPDFCreator(f24Standard).createPDF());

        //String exciseJson = "src/test/resources/input/f24excise.json";
        String exciseJson = "src\\test\\resources\\input\\f24excise.json";
        String exciseString = new String(Files.readAllBytes(Paths.get(exciseJson)));

        F24Excise f24Excise = new ObjectMapper().readValue(exciseString, F24Excise.class);
        Validator exciseValidator = ValidatorFactory.createValidator(f24Excise);
        exciseValidator.validate();

        //Files.write(Path.of("src/test/resources/output/excise.pdf"), PDFCreatorFactory.createPDFCreator(f24Excise).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24excise.pdf"), PDFCreatorFactory.createPDFCreator(f24Excise).createPDF());

        //String elidJson = "src/test/resources/input/f24elide.json";
        String elidJson = "src\\test\\resources\\input\\f24elide.json";
        String elidString = new String(Files.readAllBytes(Paths.get(elidJson)));

        F24Elid f24Elid = new ObjectMapper().readValue(elidString, F24Elid.class);
        Validator elidValidator = ValidatorFactory.createValidator(f24Elid);
        elidValidator.validate();

        //Files.write(Path.of("src/test/resources/output/elid.pdf"), PDFCreatorFactory.createPDFCreator(f24Elid).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24elid.pdf"), PDFCreatorFactory.createPDFCreator(f24Elid).createPDF());

        System.out.println("pages elid: "+ PDFCreatorFactory.createPDFCreator(f24Elid).getPagesAmount());
        System.out.println("pages excise: "+ PDFCreatorFactory.createPDFCreator(f24Excise).getPagesAmount());
        System.out.println("pages simplified: "+ PDFCreatorFactory.createPDFCreator(f24Simplified).getPagesAmount());
        System.out.println("pages standard: "+ PDFCreatorFactory.createPDFCreator(f24Standard).getPagesAmount());
    }

}
