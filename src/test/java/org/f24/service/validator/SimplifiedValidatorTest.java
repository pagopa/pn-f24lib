package org.f24.service.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.SimplifiedValidator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SimplifiedValidatorTest {

    private SimplifiedValidator validator;
    private F24Simplified form;

    @Before
    public void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24simplified.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Simplified.class);

        validator = (SimplifiedValidator) ValidatorFactory.createValidator(form);
    }

    // TaxPayer Section
    @Test
    public void givenInvalidIdCode_whenValidateTaxPayer_thenThrowException() {
        form.getTaxPayer().setIdCode("Si");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().setIdCode(".Y");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().setIdCode("TI2");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidDocumentCode_whenValidateTaxPayer_thenThrowException() {
        form.getTaxPayer().setDocumentCode("123456789108");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().setDocumentCode("1234567891u");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().setDocumentCode("12345:78910");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidOfficeCode_whenValidateTaxPayer_thenThrowException() {
        form.getTaxPayer().setOfficeCode("UYO8");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().setOfficeCode("YuI");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // PaymentReasonSection Section
    @Test
    public void givenInvalidOperationId_whenValidatePaymentReasonSection_thenThrowException() {
        form.getPaymentReasonSection().setOperationId("1234567890111123139");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().setOperationId("1234568901i1123139");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenEmptyPaymentReasonList_whenValidatePaymentReasonSection_thenThrowException() {
        form.setPaymentReasonSection(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidSection_whenValidatePaymentReasonRecord_thenThrowException() throws ResourceException, IOException, ProcessingException {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setSection(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setSection("AE");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setSection("er");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidTributeCode_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setTaxTypeCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setTaxTypeCode("TU9k");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setTaxTypeCode("TU.9P");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidInstitutionCode_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setMunicipalityCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setMunicipalityCode("190Y");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidNumberOfBuildings_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setNumberOfProperties("1");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setNumberOfProperties("J87");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidMonth_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setMonth("123");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setMonth("09.5");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidDeduction_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setDeduction("01234567");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setDeduction("8.001234");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidYear_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setYear("0192");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setYear("124");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidDebitAmount_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setDebitAmount("1920331123423974");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());

        form.getPaymentReasonSection().getReasonRecordList().get(0).setDebitAmount("124.08");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidCreditAmount_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setDebitAmount("123.01");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());
    }

    @Test
    public void givenDebitAndCredit_whenValidatePaymentReasonRecord_thenThrowException() {
        form.getPaymentReasonSection().getReasonRecordList().get(0).setDebitAmount("101");
        form.getPaymentReasonSection().getReasonRecordList().get(0).setCreditAmount("202");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());
    }

}
