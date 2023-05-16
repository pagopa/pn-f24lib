package org.f24.service.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.StandardValidator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertThrows;

public class StandardValidatorTest {

    private StandardValidator validator;
    private F24Standard form;

    @Before
    public void setup() throws IOException, ResourceException {
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

    // InpsSection
    @Test
    public void givenInvalidOfficeCode_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setOfficeCode("ABC");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidContributionReason_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setContributionReason("123");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidInpsCode_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setInpsCode("123456789zxcvbnmqwerty");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPeriodStartDate_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).getPeriod().setStartDate("date");
        ;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPeriodEndDate_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).getPeriod().setEndDate("date");
        ;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenDebitAndCredit_whenValidateInpsSection_thenThrowException() {
        form.getInpsSection().getInpsRecordList().get(0).setDebitAmount("101");
        form.getInpsSection().getInpsRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    // RegionSection
    @Test
    public void givenInvalidRegionCode_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setRegionCode("ABC");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidTaxTypeCode_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setTaxTypeCode("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidInstallment_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setInstallment("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidYear_whenValidateRegionRecord_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setYear("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenDebitAndCredit_whenValidateRegionSection_thenThrowException() {
        form.getRegionSection().getRegionRecordList().get(0).setDebitAmount("101");
        form.getRegionSection().getRegionRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    //Local Tax Section (Old Imu)
    @Test
    public void givenInvalidMunicipalityCode_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setMunicipalityCode("adasdasd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }
    
    @Test
    public void givenInvalidReconsideration_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setReconsideration(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPropertiesChanges_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setPropertiesChanges(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidAdvancePayment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setAdvancePayment(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidFullPayment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setFullPayment(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidNumberOfProperties_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setNumberOfProperties("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidTaxTypeCode_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setTaxTypeCode("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidInstallment_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setInstallment("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidYear_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setYear("ABCDF");
        assertThrows(ResourceException.class, () -> validator.validate());
    }
    
    @Test
    public void given_DebitAndCredit_whenValidateLocalTaxSection_thenThrowException() {
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setDebitAmount("101");
        form.getLocalTaxSection().getLocalTaxRecordList().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    //INAIL section
    @Test
    public void givenInvalidOfficeCode_whenValidateInailSection_thenThrowException() throws ProcessingException, IOException, ResourceException {
        form.getSocialSecuritySection().getInailRecords().get(0).setOfficeCode("abcd1");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidCompanyCode_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setCompanyCode("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidControlCode_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setControlCode("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidReferenceNumber_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setReferenceNumber("abcd");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidReason_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setReason(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void given_DebitAndCredit_whenValidateInailSection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setDebitAmount("101");
        form.getSocialSecuritySection().getInailRecords().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    //Social Security section
    @Test
    public void givenInvalMunicipalityCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setMunicipalityCode("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidOfficeCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setOfficeCode("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidContributionReason_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setContributionReason("abCD");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPositionCode_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).setPositionCode("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPeriodStartDate_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).getPeriod().setStartDate("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidPeriodEndDate_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getSocialSecurityRecordList().get(0).getPeriod().setEndDate("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void given_DebitAndCredit_whenValidateSocialSecuritySection_thenThrowException() {
        form.getSocialSecuritySection().getInailRecords().get(0).setDebitAmount("101");
        form.getSocialSecuritySection().getInailRecords().get(0).setCreditAmount("101");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    //IBAN code
    @Test
    @Ignore
    public void givenInvalidIBAN_whenValidateStandartPdf_thenThrowException() {
        form.setIbanCode("abcd");;
        assertThrows(ResourceException.class, () -> validator.validate());
    }
}
