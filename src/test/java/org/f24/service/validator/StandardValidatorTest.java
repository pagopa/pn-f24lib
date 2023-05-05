package org.f24.service.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.StandardValidator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertThrows;

public class StandardValidatorTest {

    private StandardValidator validator;
    private F24Standard form;

    @Before
    public void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24standard.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Standard.class);

        validator = (StandardValidator) ValidatorFactory.createValidator(form);
    }

    // Treasury Section
    @Test
    public void givenInvalidOfficeCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().setOfficeCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().setOfficeCode("YuI");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidDocumentCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().setDocumentCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().setDocumentCode("1234567891u");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidTaxTypeCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setTaxTypeCode("14TYi");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setTaxTypeCode("12/U");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidInstallment_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setInstallment("579u");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setInstallment("57987");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidDebitAmount_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setDebitAmount("0.94");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setDebitAmount("2112345678901234");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidCreditAmount_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setCreditAmount("0,4");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenDebitAndCredit_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setDebitAmount("101");
        form.getTreasurySection().getTaxList().get(0).setCreditAmount("103");
        assertThrows(ResourceException.class, () -> validator.validate());
    }
}
