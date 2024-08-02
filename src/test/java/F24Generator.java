import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Elid;
import org.f24.dto.form.F24Excise;
import org.f24.dto.form.F24Simplified;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreatorFactory;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


public class F24Generator {

    @Test
    public void generateSimplified() throws IOException, ResourceException {
        Date start = new Date();
        String simplifiedJson = "src\\test\\resources\\input\\f24simplified.json";
        String simplifiedString = new String(Files.readAllBytes(Paths.get(simplifiedJson)));

        F24Simplified f24Simplified = new ObjectMapper().readValue(simplifiedString, F24Simplified.class);


        byte[] bytes = Assertions.assertDoesNotThrow(() -> PDFCreatorFactory.createPDFCreator(f24Simplified).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24simplified_test.pdf"), bytes);

        System.out.println("pages simplified: " + PDFCreatorFactory.createPDFCreator(f24Simplified).getPagesAmount());
        System.out.println("Ending simplified in " + (new Date().getTime() - start.getTime()) + " ms");
        Assertions.assertNotNull(bytes);
        Assertions.assertNotEquals(0, bytes.length);
    }

    @Test
    public void generateStandard() throws IOException, ResourceException {
        Date start = new Date();
        String standardJson = "src\\test\\resources\\input\\f24standard.json";
        String standardString = new String(Files.readAllBytes(Paths.get(standardJson)));

        F24Standard f24Standard = new ObjectMapper().readValue(standardString, F24Standard.class);

        byte[] bytes = Assertions.assertDoesNotThrow(() -> PDFCreatorFactory.createPDFCreator(f24Standard).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24standard_test.pdf"), bytes);

        System.out.println("pages standard: " + PDFCreatorFactory.createPDFCreator(f24Standard).getPagesAmount());
        System.out.println("Ending standard in " + (new Date().getTime() - start.getTime()) + " ms");
        Assertions.assertNotNull(bytes);
        Assertions.assertNotEquals(0, bytes.length);
    }

    @Test
    public void generateExcise() throws IOException, ResourceException {
        Date start = new Date();
        String exciseJson = "src\\test\\resources\\input\\f24excise.json";
        String exciseString = new String(Files.readAllBytes(Paths.get(exciseJson)));

        F24Excise f24Excise = new ObjectMapper().readValue(exciseString, F24Excise.class);

        byte[] bytes = Assertions.assertDoesNotThrow(() -> PDFCreatorFactory.createPDFCreator(f24Excise).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24excise_test.pdf"), bytes);

        System.out.println("pages excise: " + PDFCreatorFactory.createPDFCreator(f24Excise).getPagesAmount());
        System.out.println("Ending excise in " + (new Date().getTime() - start.getTime()) + " ms");
        Assertions.assertNotNull(bytes);
        Assertions.assertNotEquals(0, bytes.length);
    }

    @Test
    public void generateElid() throws IOException, ResourceException {
        Date start = new Date();
        String elidJson = "src\\test\\resources\\input\\f24elide.json";
        String elidString = new String(Files.readAllBytes(Paths.get(elidJson)));

        F24Elid f24Elid = new ObjectMapper().readValue(elidString, F24Elid.class);

        byte[] bytes = Assertions.assertDoesNotThrow(() -> PDFCreatorFactory.createPDFCreator(f24Elid).createPDF());
        Files.write(Path.of("src\\test\\resources\\output\\f24elid_test.pdf"), bytes);

        System.out.println("pages elid: " + PDFCreatorFactory.createPDFCreator(f24Elid).getPagesAmount());
        System.out.println("Ending elid in " + (new Date().getTime() - start.getTime()) + " ms");
        Assertions.assertNotNull(bytes);
        Assertions.assertNotEquals(0, bytes.length);
    }
}
