package org.f24.service.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.StandardValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class StandardValidatorTest {

    private StandardValidator validator;
    private F24Standard form;

    @BeforeEach
    void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24standard.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Standard.class);

        validator = (StandardValidator) ValidatorFactory.createValidator(form);
    }

    // Treasury Section
    @Test
    void givenInvalidOfficeCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().setOfficeCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().setOfficeCode("YuI");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidDocumentCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().setDocumentCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().setDocumentCode("1234567891u");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setTaxTypeCode("14TYi");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setTaxTypeCode("12/U");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidInstallment_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setInstallment("579u");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setInstallment("57987");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidDebitAmount_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setDebitAmount("0.94");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTreasurySection().getTaxList().get(0).setDebitAmount("2112345678901234");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidCreditAmount_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setCreditAmount("0,4");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenDebitAndCredit_whenValidateTreasurySection_thenThrowException() {
        form.getTreasurySection().getTaxList().get(0).setDebitAmount("101");
        form.getTreasurySection().getTaxList().get(0).setCreditAmount("103");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // InpsSection
    @Test
    void givenInvalidOfficeCode_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setOfficeCode("ABC");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidContributionReason_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setContributionReason("123");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidInpsCode_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setInpsCode("123456789zxcvbnmqwerty");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPeriodStartDate_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).getPeriod().setStartDate("date");
        ;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPeriodEndDate_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).getPeriod().setEndDate("date");
        ;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenDebitAndCredit_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setDebitAmount("101");
        form.getInpsSection().getInpsRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // RegionSection
    @Test
    void givenInvalidRegionCode_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setRegionCode("ABC");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setTaxTypeCode("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidInstallment_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setInstallment("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidYear_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setYear("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenDebitAndCredit_whenValidateRegionSection_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setDebitAmount("101");
        form.getRegionSection().getRegionRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // Local Tax Section (Old Imu)
    @Test
    void givenInvalidMunicipalityCode_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setMunicipalityCode("adasdasd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidReconsideration_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setReconsideration(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPropertiesChanges_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setPropertiesChanges(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidAdvancePayment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setAdvancePayment(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidFullPayment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setFullPayment(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidNumberOfProperties_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setNumberOfProperties("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setTaxTypeCode("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidInstallment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setInstallment("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidYear_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setYear("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void given_DebitAndCredit_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setDebitAmount("101");
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // INAIL section
    @Test
    void givenInvalidOfficeCode_whenValidateInailSection_thenThrowException()
            throws ProcessingException, IOException, ResourceException {
        form.getSocialSecuritySection().getInailRecords().get(0).setOfficeCode("abcd1");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidCompanyCode_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setCompanyCode("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidControlCode_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setControlCode("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidReferenceNumber_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setReferenceNumber("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidReason_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setReason(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void given_DebitAndCredit_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setDebitAmount("101");
        form.getSocialSecuritySection().getInailRecords().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // Social Security section
    @Test
    void givenInvalMunicipalityCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setMunicipalityCode("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidOfficeCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setOfficeCode("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidContributionReason_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setContributionReason("abCD");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPositionCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setPositionCode("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPeriodStartDate_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).getPeriod().setStartDate("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidPeriodEndDate_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).getPeriod().setEndDate("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void given_DebitAndCredit_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setDebitAmount("101");
        form.getSocialSecuritySection().getInailRecords().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // IBAN code
    @Test
    @Disabled("IBAN code is not present in json scheme")
    void givenInvalidIBAN_whenValidateStandartPdf_thenThrowException() {
        form.setIbanCode("abcd");

        assertThrows(ResourceException.class, () -> validator.validate());
    }
}
