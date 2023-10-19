package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;
import org.f24.service.validator.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExciseValidatorTest {
    private ExciseValidator validator;
    private F24Excise form;

    @BeforeEach
    void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24excise.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Excise.class);

        validator = (ExciseValidator) ValidatorFactory.createValidator(form);
    }

    // Treasury Section
    @Test
    void givenInvalidOfficeCode_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().setOfficeCode("iY9");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidDocumentCode_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().setDocumentCode("239-53231");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setTaxTypeCode("KY.L");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidInstallment_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setInstallment("0l28");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidDebitAmount_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setDebitAmount("1.68");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidCreditAmount_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setCreditAmount("0,96554");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenDebitAndCredit_whenValidateTreasurySection_thenThrowException() {
        if(form.getTreasurySection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setDebitAmount("987642");
            form.getTreasurySection().getTaxList().get(0).setCreditAmount("75");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    // InpsSection
    @Test
    void givenInvalidOfficeCode_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).setOfficeCode("12I");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidContributionReason_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).setContributionReason("TAFKI");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidInpsCode_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).setInpsCode("21jd/sdfghjklkhgfdsfghjkljhgfdsfgdgdfgdfg");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidPeriodStartDate_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).getPeriod().setStartDate("12-10-1357");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidPeriodEndDate_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).getPeriod().setEndDate("12-1987");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenDebitAndCredit_whenValidateInpsSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getInpsSection().getInpsRecordList().get(0).setDebitAmount("340");
            form.getInpsSection().getInpsRecordList().get(0).setCreditAmount("08");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    // RegionSection
    @Test
    void givenInvalidRegionCode_whenValidateRegionRecord_thenThrowException() {
        if(form.getRegionSection() != null) { 
            form.getRegionSection().getRegionRecordList().get(0).setRegionCode("908");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateRegionRecord_thenThrowException() {
        if(form.getRegionSection() != null) { 
            form.getRegionSection().getRegionRecordList().get(0).setTaxTypeCode("765325");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidInstallment_whenValidateRegionRecord_thenThrowException() {
        if(form.getRegionSection() != null) { 
            form.getRegionSection().getRegionRecordList().get(0).setInstallment("98907");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidYear_whenValidateRegionRecord_thenThrowException() {
        if(form.getRegionSection() != null) { 
            form.getRegionSection().getRegionRecordList().get(0).setYear("207O");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenDebitAndCredit_whenValidateRegionSection_thenThrowException() {
        if(form.getRegionSection() != null) { 
            form.getRegionSection().getRegionRecordList().get(0).setDebitAmount("90");
            form.getRegionSection().getRegionRecordList().get(0).setCreditAmount("17");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    // Local Tax Section (Old Imu)
    @Test
    void givenInvalidMunicipalityCode_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setMunicipalityCode("99YZasdsad");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidReconsideration_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setReconsideration(null);
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidPropertiesChanges_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setPropertiesChanges(null);
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidAdvancePayment_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setAdvancePayment(null);
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidFullPayment_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setFullPayment(null);
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidNumberOfProperties_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setNumberOfProperties("9876");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setTaxTypeCode("981245");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidInstallment_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setInstallment("c6");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidYear_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setYear("20.9");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void given_DebitAndCredit_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setDebitAmount("346");
            form.getLocalTaxSection().getLocalTaxRecordList().get(0).setCreditAmount("942");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    // Treasury section

    @Test
    void givenInvalidDebitAmount_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setDebitAmount("0.08");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidCreditAmount_whenValidateLocalTaxSection_thenThrowException() {
        if(form.getLocalTaxSection() != null) { 
            form.getTreasurySection().getTaxList().get(0).setCreditAmount("10l");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    //Excise section
    @Test
    void givenInvalidMunicipality_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setMunicipality("MI6");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidProvince_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setProvince("MIU");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidIdCode_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setIdCode("895WG");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidTaxTypeCode_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setTaxTypeCode("1246U");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidInstallment_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setInstallment("12,");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidMonth_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setMonth("111");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidYear_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setYear("237178");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidDebit_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().getExciseTaxList().get(0).setDebitAmount("123456789012356790-");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidOfficeCode_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().setOfficeCode("1238");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }

    @Test
    void givenInvalidDocumentCode_whenValidateExciseSection_thenThrowException() {
        if(form.getInpsSection() != null) { 
            form.getExciseSection().setDocumentCode("0");
            assertThrows(ResourceException.class, () -> validator.validate());
        }
    }


}
