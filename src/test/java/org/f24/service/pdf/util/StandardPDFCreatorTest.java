package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.f24.dto.component.Record;
import org.f24.dto.component.InailRecord;
import org.f24.dto.component.InpsRecord;
import org.f24.dto.component.InpsSection;
import org.f24.dto.component.LocalTaxRecord;
import org.f24.dto.component.LocalTaxSection;
import org.f24.dto.component.RegionRecord;
import org.f24.dto.component.RegionSection;
import org.f24.dto.component.SocialSecurityRecord;
import org.f24.dto.component.SocialSecuritySection;
import org.f24.dto.component.Tax;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.TreasurySection;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.StandardPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.f24.service.pdf.util.FieldEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardPDFCreatorTest {

    private StandardPDFCreator pdfCreator;
    List<Record> recordList;
    F24Standard form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24standard.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Standard.class);

        recordList = new ArrayList<Record>(Arrays.asList(
                new Record("1000", "0", "1000"),
                new Record("0", "800", "800"),
                new Record("1200", "0", "1200"),
                new Record("500", "0", "500"),
                new Record("1000", "0", "1000"),
                new Record("0", "800", "800"),
                new Record("1200", "0", "1200"),
                new Record("500", "0", "500")));

        pdfCreator = new StandardPDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24IMU.pdf");
    }

    @Test
    void shouldFillTaxPayer() throws ResourceException {
        pdfCreator.setIndex(0);
        TaxPayer taxPayer = this.form.getTaxPayer();

        assertNotNull(taxPayer);

        pdfCreator.setField(TAX_CODE.getName(), taxPayer.getTaxCode());
        pdfCreator.setField(RELATIVE_PERSON_TAX_CODE.getName(), "BGCDJV27L49N524I");
        pdfCreator.setField(ID_CODE.getName(), taxPayer.getIdCode());

        if (taxPayer.getIsNotTaxYear())
            pdfCreator.setField(IS_NOT_TAX_YEAR.getName(), "X");

        assertEquals(taxPayer.getTaxCode(), pdfCreator.getField(TAX_CODE.getName()).getValueAsString());
        assertEquals("BGCDJV27L49N524I", pdfCreator.getField(RELATIVE_PERSON_TAX_CODE.getName()).getValueAsString());
        assertEquals(taxPayer.getIdCode(), pdfCreator.getField(ID_CODE.getName()).getValueAsString());

        assertEquals("X", pdfCreator.getField(IS_NOT_TAX_YEAR.getName()).getValueAsString());
    }

    @Test
    void shouldFillPaymentReasonRecordCheckboxes() throws ResourceException {
        pdfCreator.setIndex(0);
        pdfCreator.setField(RECONSIDERATION.getName() + 1, "X");
        pdfCreator.setField(PROPERTIES_CHANGED.getName() + 1, "X");
        pdfCreator.setField(ADVANCE_PAYMENT.getName() + 1, "X");
        pdfCreator.setField(FULL_PAYMENT.getName() + 1, "X");
        pdfCreator.setField(NUMBER_OF_PROPERTIES.getName() + 1, "X");

        assertEquals("X", pdfCreator.getField(RECONSIDERATION.getName() + 1).getValueAsString());
        assertEquals("X", pdfCreator.getField(PROPERTIES_CHANGED.getName() + 1).getValueAsString());
        assertEquals("X", pdfCreator.getField(ADVANCE_PAYMENT.getName() + 1).getValueAsString());
        assertEquals("X", pdfCreator.getField(FULL_PAYMENT.getName() + 1).getValueAsString());
        assertEquals("X", pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + 1).getValueAsString());
    }

    @Test
    void shouldFillInpsSection() throws ResourceException {
        pdfCreator.setIndex(0);
        InpsSection inpsSection = form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();
        String sectionId = "2";

        assertNotNull(inpsSection);
        assertNotNull(inpsRecordList);

        inpsRecordList = pdfCreator.paginateList(0, 4, inpsRecordList);

        int index = 1;
        for (InpsRecord inpsRecord : inpsRecordList) {
            pdfCreator.setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
            pdfCreator.setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
            pdfCreator.setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());

            index++;
        }

        index = 1;
        for (InpsRecord inpsRecord : inpsRecordList) {
            assertEquals(inpsRecord.getOfficeCode(),
                    pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(inpsRecord.getContributionReason(),
                    pdfCreator.getField(CONTRIBUTION_REASON.getName() + sectionId + index).getValueAsString());
            assertEquals(inpsRecord.getInpsCode(),
                    pdfCreator.getField(INPS_CODE.getName() + sectionId + index).getValueAsString());

            index++;
        }
    }

    @Test
    void shouldFillLocalTaxSection() throws ResourceException {
        pdfCreator.setIndex(0);
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();
        List<LocalTaxRecord> localTaxRecordList = localTaxSection.getLocalTaxRecordList();
        String sectionId = "4";

        assertNotNull(localTaxSection);
        assertNotNull(localTaxRecordList);

        localTaxRecordList = pdfCreator.paginateList(0, 4, localTaxRecordList);

        int index = 1;
        for (LocalTaxRecord localTaxRecord : localTaxRecordList) {
            pdfCreator.setField(YEAR.getName() + sectionId + index, localTaxRecord.getYear());
            pdfCreator.setField(INSTALLMENT.getName() + sectionId + index, localTaxRecord.getInstallment());
            pdfCreator.setField(TAX_TYPE_CODE.getName() + sectionId + index, localTaxRecord.getTaxTypeCode());
            pdfCreator.setField(MUNICIPALITY_CODE.getName() + sectionId + index, localTaxRecord.getMunicipalityCode());

            pdfCreator.setField(RECONSIDERATION.getName() + index, "X");

            pdfCreator.setField(PROPERTIES_CHANGED.getName() + index, "X");

            pdfCreator.setField(ADVANCE_PAYMENT.getName() + index, "X");

            pdfCreator.setField(FULL_PAYMENT.getName() + index, "X");

            pdfCreator.setField(NUMBER_OF_PROPERTIES.getName() + index, localTaxRecord.getNumberOfProperties());

            index++;
        }

        index = 1;

        for (LocalTaxRecord localTaxRecord : localTaxRecordList) {
            assertEquals(localTaxRecord.getYear(),
                    pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString());
            assertEquals(localTaxRecord.getInstallment(),
                    pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString());
            assertEquals(localTaxRecord.getTaxTypeCode(),
                    pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(localTaxRecord.getMunicipalityCode(),
                    pdfCreator.getField(MUNICIPALITY_CODE.getName() + sectionId + index).getValueAsString());

            assertEquals("X", pdfCreator.getField(RECONSIDERATION.getName() + index).getValueAsString());

            assertEquals("X", pdfCreator.getField(PROPERTIES_CHANGED.getName() + index).getValueAsString());

            assertEquals("X", pdfCreator.getField(ADVANCE_PAYMENT.getName() + index).getValueAsString());

            assertEquals("X", pdfCreator.getField(FULL_PAYMENT.getName() + index).getValueAsString());

            assertEquals(localTaxRecord.getNumberOfProperties(),
                    pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + index).getValueAsString());

            index++;
        }

    }

    @Test
    void shouldFillTreasurySection() throws ResourceException {
        pdfCreator.setIndex(0);
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();
        String sectionId = "1";

        assertNotNull(treasurySection);
        assertNotNull(taxList);

        taxList = pdfCreator.paginateList(0, 6, taxList);

        int index = 1;
        for (Tax taxRecord : taxList) {
            pdfCreator.setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
            pdfCreator.setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
            pdfCreator.setField(YEAR.getName() + sectionId + index, taxRecord.getYear());

            index++;
        }

        index = 1;

        for (Tax taxRecord : taxList) {
            assertEquals(taxRecord.getTaxTypeCode(),
                    pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(taxRecord.getInstallment(),
                    pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString());
            assertEquals(taxRecord.getYear(),
                    pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString());

            index++;
        }

        pdfCreator.setField(OFFICE_CODE.getName() + sectionId, treasurySection.getOfficeCode());
        pdfCreator.setField(DOCUMENT_CODE.getName() + sectionId, treasurySection.getDocumentCode());

        assertEquals(treasurySection.getOfficeCode(), pdfCreator.getField(OFFICE_CODE.getName()  + sectionId).getValueAsString());
        assertEquals(treasurySection.getDocumentCode(),
                pdfCreator.getField(DOCUMENT_CODE.getName()  + sectionId).getValueAsString());

    }

    @Test
    void shouldFillTreasurySectionCodes() throws ResourceException {
        pdfCreator.setIndex(0);
        TreasurySection treasurySection = this.form.getTreasurySection();
        String sectionId = "1";

        assertNotNull(treasurySection);

        pdfCreator.setField(OFFICE_CODE.getName() + sectionId, treasurySection.getOfficeCode());
        pdfCreator.setField(DOCUMENT_CODE.getName() + sectionId, treasurySection.getDocumentCode());

        assertEquals(treasurySection.getOfficeCode(),
                pdfCreator.getField(OFFICE_CODE.getName()  + sectionId).getValueAsString());
        assertEquals(treasurySection.getDocumentCode(),
                pdfCreator.getField(DOCUMENT_CODE.getName()  + sectionId).getValueAsString());
    }

    @Test
    void shouldFillSocialSecurity() throws ResourceException {
        pdfCreator.setIndex(0);
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();
        String sectionId = "6";

        assertNotNull(socSecurity);
        assertNotNull(socSecurityList);

        socSecurityList = pdfCreator.paginateList(0, 2, socSecurityList);

        int index = 1;
        for (SocialSecurityRecord socSecRecord : socSecurityList) {
            pdfCreator.setField(MUNICIPALITY_CODE.getName() + sectionId, socSecRecord.getMunicipalityCode());
            pdfCreator.setField(OFFICE_CODE.getName() + sectionId + index, socSecRecord.getOfficeCode());
            pdfCreator.setField(CONTRIBUTION_REASON.getName() + sectionId + index,
                    socSecRecord.getContributionReason());
            pdfCreator.setField(POSITION_CODE.getName() + sectionId + index, socSecRecord.getPositionCode());

            index++;
        }

        index = 1;
        for (SocialSecurityRecord socSecRecord : socSecurityList) {
            assertEquals(socSecRecord.getMunicipalityCode(),
                    pdfCreator.getField(MUNICIPALITY_CODE.getName() + sectionId).getValueAsString());
            assertEquals(socSecRecord.getOfficeCode(),
                    pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(socSecRecord.getContributionReason(),
                    pdfCreator.getField(CONTRIBUTION_REASON.getName() + sectionId + index).getValueAsString());
            assertEquals(socSecRecord.getPositionCode(),
                    pdfCreator.getField(POSITION_CODE.getName() + sectionId + index).getValueAsString());

            index++;
        }
    }

    @Test
    void shouldFillInail() throws ResourceException {
        pdfCreator.setIndex(0);
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();
        String sectionId = "5";

        assertNotNull(socSecurity);
        assertNotNull(inailRecordList);

        inailRecordList = pdfCreator.paginateList(0, 3, inailRecordList);

        int index = 1;
        for (InailRecord inailRecord : inailRecordList) {
            pdfCreator.setField(OFFICE_CODE.getName() + sectionId + index, inailRecord.getOfficeCode());
            pdfCreator.setField(COMPANY_CODE.getName() + sectionId + index, inailRecord.getCompanyCode());
            pdfCreator.setField(CONTROL_CODE.getName() + sectionId + index, inailRecord.getControlCode());
            pdfCreator.setField(REFERENCE_NUMBER.getName() + sectionId + index, inailRecord.getReferenceNumber());
            pdfCreator.setField(REASON.getName() + sectionId + index, inailRecord.getReason());

            index++;
        }

        index = 1;
        for (InailRecord inailRecord : inailRecordList) {
            assertEquals(inailRecord.getOfficeCode(),
                    pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(inailRecord.getCompanyCode(),
                    pdfCreator.getField(COMPANY_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(inailRecord.getControlCode(),
                    pdfCreator.getField(CONTROL_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(inailRecord.getReferenceNumber(),
                    pdfCreator.getField(REFERENCE_NUMBER.getName() + sectionId + index).getValueAsString());
            assertEquals(inailRecord.getReason(),
                    pdfCreator.getField(REASON.getName() + sectionId + index).getValueAsString());

            index++;
        }
    }

    @Test
    void shouldFillRegionSection() throws ResourceException {
        pdfCreator.setIndex(0);
        RegionSection regionSection = this.form.getRegionSection();
        List<RegionRecord> regionRecordsList = regionSection.getRegionRecordList();
        String sectionId = "3";

        assertNotNull(regionRecordsList);
        assertNotNull(regionSection);

        int index = 1;
        for (RegionRecord regionRecord : regionRecordsList) {
            pdfCreator.setField(YEAR.getName() + sectionId + index, regionRecord.getYear());
            pdfCreator.setField(INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
            pdfCreator.setField(TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
            pdfCreator.setField(REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

            index++;
        }

        index = 1;
        for (RegionRecord regionRecord : regionRecordsList) {
            assertEquals(regionRecord.getYear(),
                    pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString());
            assertEquals(regionRecord.getInstallment(),
                    pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString());
            assertEquals(regionRecord.getTaxTypeCode(),
                    pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(regionRecord.getRegionCode(),
                    pdfCreator.getField(REGION_CODE.getName() + sectionId + index).getValueAsString());

            index++;
        }

    }

    @Test
    void shouldFillSectionTotal() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(pdfCreator.getTotalAmount(recordList));

        Integer total = pdfCreator.getTotalAmount(recordList);
        String parsedTotal = pdfCreator.getMoney(total);
        pdfCreator.setField(TOTAL_AMOUNT.getName() + sectionId, parsedTotal);

        assertEquals(parsedTotal, pdfCreator.getField(TOTAL_AMOUNT.getName() + sectionId).getValueAsString());
    }

    @Test
    void shouldFillSectionDebit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(pdfCreator.getDebitTotal(recordList));

        Integer debitTotal = pdfCreator.getDebitTotal(recordList);
        String parsedTotal = pdfCreator.getMoney(debitTotal);
        pdfCreator.setField(TOTAL_DEBIT.getName() + sectionId, parsedTotal);

        assertEquals(parsedTotal, pdfCreator.getField(TOTAL_DEBIT.getName() + sectionId).getValueAsString());
    }

    @Test
    void shouldFillSectionCredit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(pdfCreator.getCreditTotal(recordList));

        Integer creditTotal = pdfCreator.getCreditTotal(recordList);
        String parsedTotal = pdfCreator.getMoney(creditTotal);
        pdfCreator.setField(TOTAL_CREDIT.getName() + sectionId, parsedTotal);

        assertEquals(parsedTotal, pdfCreator.getField(TOTAL_CREDIT.getName() + sectionId).getValueAsString());
    }

    @Test
    void shouldFillRecordCredit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "4";

        recordList = recordList.subList(0, 4);

        int index = 1;
        for (Record record : recordList) {
            String recordCredit = record.getCreditAmount();
            String parsedCredit = pdfCreator.getMoney(Integer.parseInt(recordCredit));
            pdfCreator.setField(CREDIT_AMOUNT.getName() + sectionId + index, parsedCredit);

            assertEquals(parsedCredit,
                    pdfCreator.getField(CREDIT_AMOUNT.getName() + sectionId + index).getValueAsString());

            index++;
        }

    }

    @Test
    void shouldFillRecordDebit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "4";

        recordList = recordList.subList(0, 4);

        int index = 1;
        for (Record record : recordList) {
            String recordCredit = record.getDebitAmount();
            String parsedDebit = pdfCreator.getMoney(Integer.parseInt(recordCredit));
            pdfCreator.setField(DEBIT_AMOUNT.getName() + sectionId + index, parsedDebit);

            assertEquals(parsedDebit,
                    pdfCreator.getField(DEBIT_AMOUNT.getName() + sectionId + index).getValueAsString());

            index++;
        }

    }

    @Test
    void shouldFillMultiField() throws ResourceException {
        pdfCreator.setIndex(0);

        String[] splittedCreditAmount = pdfCreator.splitField(1235.456);
        pdfCreator.setField(DEDUCTION.getName() + "Int", splittedCreditAmount[0]);
        pdfCreator.setField(DEDUCTION.getName() + "Dec", splittedCreditAmount[1]);

        assertEquals(splittedCreditAmount[0], pdfCreator.getField(DEDUCTION.getName() + "Int").getValueAsString());
        assertEquals(splittedCreditAmount[1], pdfCreator.getField(DEDUCTION.getName() + "Dec").getValueAsString());
    }

    @Test
    void shouldFillMultidate() throws ResourceException {
        pdfCreator.setIndex(0);
        String date = "202034";

        String monthPart = date.substring(0, 2);
        String yearPart = date.substring(2, date.length() - 1);

        pdfCreator.setField(START_DATE.getName() + "Month" + 6 + 1, monthPart);
        pdfCreator.setField(START_DATE.getName() + "Year" + 6 + 1, yearPart);

        assertEquals(monthPart, pdfCreator.getField(START_DATE.getName() + "Month" + 6 + 1).getValueAsString());
        assertEquals(yearPart, pdfCreator.getField(START_DATE.getName() + "Year" + 6 + 1).getValueAsString());
    }

    @Test
    void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }
}
