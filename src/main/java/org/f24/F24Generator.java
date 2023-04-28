package org.f24;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Standard;
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
        String json = "{\r\n  \"header\": {\r\n    \"delegationTo\": \"string\",\r\n    \"agency\": \"string\",\r\n    \"province\": \"MB\"\r\n  },\r\n  \"contributor\": {\r\n    \"taxCode\": \"BGCDJV27L49N524I\",\r\n    \"ifCalendarYear\": true,\r\n    \"personData\": {\r\n      \"personalData\": {\r\n        \"surname\": \"SEGPJUDFMQKMD\",\r\n        \"name\": \"CFVCF\",\r\n        \"dateOfBirth\": \"11-88-9282\",\r\n        \"sex\": \"F\",\r\n        \"municipalityOfBirth\": \"BBZDDH\",\r\n        \"province\": \"GA\"\r\n      },\r\n      \"taxResidence\": {\r\n        \"municipality\": \"DFGRHWPNLXKQY\",\r\n        \"province\": \"QY\",\r\n        \"address\": \"Z1VIEXJS7FWMNU\"\r\n      }\r\n    },\r\n    \"companyData\": {\r\n      \"name\": \"PFRIQGOWASCGSHVUXCCOTRFLFNLY\",\r\n      \"taxResidence\": {\r\n        \"municipality\": \"FWQOJKCXAVTCWKZAMDKMQ\",\r\n        \"province\": \"RJ\",\r\n        \"address\": \"O0VMCEJ8I\"\r\n      }\r\n    },\r\n    \"receiverTaxCode\": \"TDITLC46H54C619Q\",\r\n    \"idCode\": \"9W\",\r\n    \"actCode\": \"14441450837\",\r\n    \"officeCode\": \"6BU\"\r\n  },\r\n  \"paymentDetails\": {\r\n    \"dateOfPayment\": \"35-76-9568\",\r\n    \"company\": \"string\",\r\n    \"cabCode\": \"string\",\r\n    \"checkNumber\": \"string\",\r\n    \"abiCode\": \"string\",\r\n    \"ibanCode\": \"string\",\r\n    \"bank\": true\r\n  },\r\n  \"treasurySection\": {\r\n    \"taxList\": [\r\n      {\r\n        \"tributeCode\": \"B9AD\",\r\n        \"installment\": \"88MP\",\r\n        \"reportingYear\": \"2617\",\r\n        \"debitAmount\": \"73.09\",\r\n        \"creditAmount\": \"103343538433097.29\"\r\n      }\r\n    ],\r\n    \"officeCode\": \"FRH\",\r\n    \"actCode\": \"29542848644\"\r\n  },\r\n  \"inpsSection\": {\r\n    \"inpsRecordList\": [\r\n      {\r\n        \"locationCode\": \"5670\",\r\n        \"contributionReason\": \"HHW\",\r\n        \"inpsCode\": \"32W95Mf\",\r\n        \"reportingPeriod\": {\r\n          \"startDate\": \"403120\",\r\n          \"endDate\": \"084198\"\r\n        },\r\n        \"debitAmount\": \"0.84\",\r\n        \"creditAmount\": \"0.59\"\r\n      }\r\n    ]\r\n  },\r\n  \"regionSection\": {\r\n    \"regionRecordList\": [\r\n      {\r\n        \"regionCode\": \"60\",\r\n        \"tributeCode\": \"ZEMO\",\r\n        \"installment\": \"A\",\r\n        \"reportingYear\": \"2306\",\r\n        \"debitAmount\": \"90149226260836.26\",\r\n        \"creditAmount\": \"0.42\"\r\n      }\r\n    ]\r\n  },\r\n  \"imuSection\": {\r\n    \"operationId\": \"A0VU5WBKUUJPCBE8AK\",\r\n    \"imuRecordList\": [\r\n      {\r\n        \"municipalityCode\": \"99YO\",\r\n        \"activeRepentance\": true,\r\n        \"variedBuildings\": true,\r\n        \"advancePayment\": true,\r\n        \"balance\": true,\r\n        \"numberOfBuildings\": \"433\",\r\n        \"tributeCode\": \"0943\",\r\n        \"installment\": \"43CV\",\r\n        \"reportingYear\": \"2432\",\r\n        \"debitAmount\": \"600291666.95\",\r\n        \"creditAmount\": \"0.55\"\r\n      }\r\n    ],\r\n    \"deduction\": \"9709187.77\"\r\n  },\r\n  \"securitySection\": {\r\n    \"inailRecords\": [\r\n      {\r\n        \"locationCode\": \"69605\",\r\n        \"companyCode\": \"47858729\",\r\n        \"bankAccount\": \"54\",\r\n        \"referenceNumber\": \"792244\",\r\n        \"reason\": \"B\",\r\n        \"debitAmount\": \"0.60\",\r\n        \"creditAmount\": \"0.13\"\r\n      }\r\n    ],\r\n    \"socialSecurityRecordList\": [\r\n      {\r\n        \"institutionCode\": \"8739\",\r\n        \"locationCode\": \"B6COP\",\r\n        \"contributionReason\": \"G1D\",\r\n        \"positionCode\": \"693850961\",\r\n        \"period\": {\r\n          \"startDate\": \"150401\",\r\n          \"endDate\": \"671726\"\r\n        },\r\n        \"debitAmount\": \"0.36\",\r\n        \"creditAmount\": \"0.19\"\r\n      }\r\n    ]\r\n  },\r\n  \"ibanCode\": \"string\"\r\n}";
        F24Standard f24Form = new ObjectMapper().readValue(json, F24Standard.class);
        Validator validator = ValidatorFactory.createValidator(f24Form);
        validator.validate();

        Files.write(Path.of("D:\\key-partner\\output1.pdf"), PDFCreatorFactory.createPDFCreator(f24Form).createPDF());
    }

}
