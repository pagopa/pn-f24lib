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
        String json = "{\r\n  \"header\": {\r\n    \"delegationTo\": \"delegation\",\r\n    \"agency\": \"agency\",\r\n    \"province\": \"MI\"\r\n  },\r\n  \"taxPayer\": {\r\n    \"taxCode\": \"RSSMLB80A01A200D\",\r\n    \"personData\": {\r\n      \"personalData\": {\r\n        \"surname\": \"ROSSI\",\r\n        \"name\": \"MARIO ALBERTO\",\r\n        \"birthDate\": \"01-01-1980\",\r\n        \"sex\": \"M\",\r\n        \"birthPlace\": \"MILANO\",\r\n        \"birthProvince\": \"MI\"\r\n      }\r\n    }\r\n  },\r\n  \"paymentReasonSection\": {\r\n    \"reasonRecordList\": [\r\n      {\r\n        \"section\": \"ER\",\r\n        \"taxTypeCode\": \"TSC1\",\r\n        \"institutionCode\": \"1211\",\r\n        \"reconsideration\": false,\r\n        \"propertiesChanges\": false,\r\n        \"advancePayment\": false,\r\n        \"fullPayment\": false,\r\n        \"month\": \"0101\",\r\n        \"deduction\": \"604\",\r\n        \"year\": \"2020\",\r\n        \"debitAmount\": \"604\"\r\n      }\r\n    ]\r\n  }\r\n}";
        F24Simplified f24Form = new ObjectMapper().readValue(json, F24Simplified.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("F24-PDF\\src\\test\\resources\\output\\f24simplified2.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }

}
