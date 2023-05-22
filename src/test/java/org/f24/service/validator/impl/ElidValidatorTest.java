package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Elid;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;
import org.f24.service.validator.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElidValidatorTest {
    private ElidValidator validator;
    private F24Elid form;

    @BeforeEach
    void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24elide.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Elid.class);

        validator = (ElidValidator) ValidatorFactory.createValidator(form);
    }

    // Treasury and Other Section
    @Test
    void givenInvalidType_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().getTreasuryRecords().get(0).setType("AS");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidIdElements_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().getTreasuryRecords().get(0).setIdElements("1124568");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().getTreasuryRecords().get(0).setTaxTypeCode("r.iy");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidYear_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().getTreasuryRecords().get(0).setYear("20OO");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidDebitAmount_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().getTreasuryRecords().get(0).setDebitAmount("O2643");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidOfficeCode_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().setOfficeCode("QWER");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidDocumentCode_whenValidateTreasuryAndOtherSection_thenThrowException() {
        form.getTreasuryAndOtherSection().setDocumentCode("123456789");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

}
