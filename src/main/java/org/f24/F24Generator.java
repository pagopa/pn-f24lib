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
        String json = "{\r\n  \"header\": {\r\n    \"delegationTo\": \"string\",\r\n    \"agency\": \"string\",\r\n    \"province\": \"HP\"\r\n  },\r\n  \"contributor\": {\r\n    \"taxCode\": \"SCTGLC94A20A515S\",\r\n    \"ifCalendarYear\": true,\r\n    \"personData\": {\r\n      \"personalData\": {\r\n        \"surname\": \"SCATENA\",\r\n        \"name\": \"GIANLUCA\",\r\n        \"dateOfBirth\": \"20-01-1994\",\r\n        \"sex\": \"M\",\r\n        \"municipalityOfBirth\": \"VIVRQKLMJQVWNSNLCKMP\",\r\n        \"province\": \"LO\"\r\n      },\r\n      \"taxResidence\": {\r\n        \"municipality\": \"QMJYDUREAZLWSDLLFZXSVUHYANX\",\r\n        \"province\": \"JV\",\r\n        \"address\": \"TI2BBDFVGHQQWX27K5P5QHYX\"\r\n      }\r\n    },\r\n    \"companyData\": {\r\n      \"name\": \"AUEINPHCCU\",\r\n      \"taxResidence\": {\r\n        \"municipality\": \"UWXFEKEVWOOEMVAUXRCWFIQSJKVMGLFUSGDSQ\",\r\n        \"province\": \"GF\",\r\n        \"address\": \"9M75DEIXDVS1GWL C162A46KIP\"\r\n      }\r\n    },\r\n    \"receiverTaxCode\": \"PQVLDS32B71P370J\",\r\n    \"idCode\": \"8D\",\r\n    \"actCode\": \"23910383685\",\r\n    \"officeCode\": \"7U0\"\r\n  },\r\n  \"paymentDetails\": {\r\n    \"dateOfPayment\": \"62-56-7798\",\r\n    \"company\": \"string\",\r\n    \"cabCode\": \"string\",\r\n    \"checkNumber\": \"string\",\r\n    \"abiCode\": \"string\",\r\n    \"ibanCode\": \"string\",\r\n    \"bank\": true\r\n  },\r\n  \"paymentMotiveSection\": {\r\n    \"operationId\": \"EHU4HZH6FSV0PA5I5C\",\r\n    \"motiveRecordList\": [\r\n      {\r\n        \"section\": \"MC\",\r\n        \"tributeCode\": \"FH1K\",\r\n        \"institutionCode\": \"7275\",\r\n        \"activeRepentance\": true,\r\n        \"variedBuildings\": true,\r\n        \"advancePayment\": true,\r\n        \"balance\": true,\r\n        \"numberOfBuildings\": \"503\",\r\n        \"month\": \"13\",\r\n        \"deduction\": \"0.31\",\r\n        \"reportingYear\": \"2762\",\r\n        \"debitAmount\": \"0.78\",\r\n        \"creditAmount\": \"74255849811.72\"\r\n      }\r\n    ]\r\n  }\r\n}";
        F24Simplified f24Form = new ObjectMapper().readValue(json, F24Simplified.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }

}
