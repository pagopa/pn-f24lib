package org.f24;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.F24PDFGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class F24Generator {

    public static void main(String[] args) throws ParseException, IOException {
        F24PDFGenerator f24PDFGenerator = new F24PDFGenerator();

        Header header = new Header("MARIO ROSSI", "TEST", "AQ");
        PersonalData personalData = new PersonalData("Rossi", "Mario", new SimpleDateFormat("dd/MM/yyyy").parse("20/01/1994"), 1, "Avezzano", "AQ");
        Contributor contributor = new Contributor("RSSMRA80A01A515B", "AAA", "AAA", personalData, "CSSMRA80A01A515A", "AAAAAA");
        List<PaymentMotiveRecord> records = new ArrayList<>();
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.223", "2022", "10.896", null));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "20.014", null));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", null, "5.02"));
        PaymentMotiveSection paymentMotiveSection = new PaymentMotiveSection("ASDFGHJKLERTYUIOPL", records);
        PaymentDetails paymentDetails = new PaymentDetails(new Date(), "AAA", "AAA", "AAAAAA", "AAA", null, "AAA", "AZAZAZAZAZ");

        F24Simplified form = new F24Simplified(header, contributor, null, paymentDetails, paymentMotiveSection);
        Files.write(Path.of("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf"), f24PDFGenerator.generatePDF(form));
    }

}
