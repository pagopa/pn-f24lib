package org.f24;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.F24PDFGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class F24Generator {

    public static void main(String[] args) throws ParseException {
        F24PDFGenerator f24PDFGenerator = new F24PDFGenerator();

        Header header = new Header("MARIO ROSSI", "TEST", "AQ");
        PersonalData personalData = new PersonalData("", "Mario Rossi", new SimpleDateFormat("dd/MM/yyyy").parse("20/01/1994"), 1, "Avezzano", "AQ");
        Contributor contributor = new Contributor("RSSMRA80A01A515B", "AAA", "AAA", personalData, "CSSMRA80A01A515A", "AAAAAA");
        List<PaymentMotiveRecord> records = new ArrayList<>();
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));
        records.add(new PaymentMotiveRecord("AAA", "CCC", "AAA", null, "1", "true", null, "1", "01", "202.22", "2022", "10.01", "110.02"));

        PaymentMotiveSection paymentMotiveSection = new PaymentMotiveSection("ASDFGHJKLERTYUIOPL", records);

        F24Simplified form = new F24Simplified(header, contributor, null, null, paymentMotiveSection);
        f24PDFGenerator.generatePDF(form);
    }

}
