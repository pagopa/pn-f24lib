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
        String json = "{\r\n  \"header\": {\r\n    \"delegationTo\": \"string\",\r\n    \"agency\": \"string\",\r\n    \"province\": \"HP\"\r\n  },\r\n  \"contributor\": {\r\n    \"taxCode\": \"RSSMLB80A01A200D\",\r\n    \"personData\": {\r\n      \"personalData\": {\r\n        \"surname\": \"ROSSI\",\r\n        \"name\": \"MARIO ALBERTO\",\r\n        \"dateOfBirth\": \"01-01-1980\",\r\n        \"sex\": \"M\",\r\n        \"municipalityOfBirth\": \"MILANO\",\r\n        \"province\": \"MI\"\r\n      }\r\n    }\r\n  },\r\n  \"paymentMotiveSection\": {\r\n    \"operationId\": \"EHU4HZH6FSV0PA5I5C\",\r\n    \"motiveRecordList\": [\r\n      {\r\n        \"section\": \"ER\",\r\n        \"tributeCode\": \"TSC1\",\r\n        \"institutionCode\": \"1111\",\r\n        \"activeRepentance\": false,\r\n        \"variedBuildings\": false,\r\n        \"advancePayment\": false,\r\n        \"balance\": false,\r\n        \"month\": \"0101\",\r\n        \"deduction\": \"0.01\",\r\n        \"reportingYear\": \"2020\",\r\n        \"debitAmount\": \"604\"\r\n      },\r\n      {\r\n        \"section\": \"ER\",\r\n        \"tributeCode\": \"TSC2\",\r\n        \"institutionCode\": \"1111\",\r\n        \"activeRepentance\": false,\r\n        \"variedBuildings\": false,\r\n        \"advancePayment\": false,\r\n        \"balance\": false,\r\n        \"month\": \"0101\",\r\n        \"deduction\": \"0.00\",\r\n        \"reportingYear\": \"2020\",\r\n        \"creditAmount\": \"001\"\r\n      }\r\n    ]\r\n  }\r\n}";
        F24Simplified f24Form = new ObjectMapper().readValue(json, F24Simplified.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }

}
