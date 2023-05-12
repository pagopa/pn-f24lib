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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.f24.service.pdf.util.FieldEnum.*;

public class StandardPDFCreatorTest {

    private StandardPDFCreator pdfCreator;
    private CreatorHelper helper = new CreatorHelper();
    List<Record> recordList;
    F24Standard form;

    @Before
    public void setup() throws IOException {
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
        pdfCreator.loadDoc("templates" + "/ModF24IMU2013.pdf");
    }

    @Test
    public void shouldFillTaxPayer() throws ResourceException {
        pdfCreator.setIndex(0);
        TaxPayer taxPayer = this.form.getTaxPayer();

        assertNotNull(taxPayer);

        pdfCreator.setField(TAX_CODE.getName(), taxPayer.getTaxCode());
        pdfCreator.setField(RELATIVE_PERSON_TAX_CODE.getName(), "BGCDJV27L49N524I");
        pdfCreator.setField(ID_CODE.getName(), taxPayer.getIdCode());

        if (taxPayer.getIsNotTaxYear())
            pdfCreator.setField(IS_NOT_TAX_YEAR.getName(), "X");

        assertEquals(pdfCreator.getField(TAX_CODE.getName()).getValueAsString(), taxPayer.getTaxCode());
        assertEquals(pdfCreator.getField(RELATIVE_PERSON_TAX_CODE.getName()).getValueAsString(), "BGCDJV27L49N524I");
        assertEquals(pdfCreator.getField(ID_CODE.getName()).getValueAsString(), taxPayer.getIdCode());

        assertEquals(pdfCreator.getField(IS_NOT_TAX_YEAR.getName()).getValueAsString(), "X");
    }

    @Test
    public void shouldFillPaymentReasonRecordCheckboxes() throws ResourceException {
        pdfCreator.setIndex(0);
        pdfCreator.setField(RECONSIDERATION.getName() + 1, "X");
        pdfCreator.setField(PROPERTIES_CHANGED.getName() + 1, "X");
        pdfCreator.setField(ADVANCE_PAYMENT.getName() + 1, "X");
        pdfCreator.setField(FULL_PAYMENT.getName() + 1, "X");
        pdfCreator.setField(NUMBER_OF_PROPERTIES.getName() + 1, "X");

        assertEquals(pdfCreator.getField(RECONSIDERATION.getName() + 1).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(PROPERTIES_CHANGED.getName() + 1).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(ADVANCE_PAYMENT.getName() + 1).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(FULL_PAYMENT.getName() + 1).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + 1).getValueAsString(), "X");
    }

    @Test
    public void shouldFillInpsSection() throws ResourceException {
        pdfCreator.setIndex(0);
        InpsSection inpsSection = form.getInpsSection();
        List<InpsRecord> inpsRecordList = inpsSection.getInpsRecordList();
        String sectionId = "2";

        assertNotNull(inpsSection);
        assertNotNull(inpsRecordList);

        inpsRecordList = helper.paginateList(0, 4, inpsRecordList);

        int index = 1;
        for (InpsRecord inpsRecord : inpsRecordList) {
            pdfCreator.setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
            pdfCreator.setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
            pdfCreator.setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());

            index++;
        }

        index = 1;
        for (InpsRecord inpsRecord : inpsRecordList) {
            assertEquals(pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString(),
                    inpsRecord.getOfficeCode());
            assertEquals(pdfCreator.getField(CONTRIBUTION_REASON.getName() + sectionId + index).getValueAsString(),
                    inpsRecord.getContributionReason());
            assertEquals(pdfCreator.getField(INPS_CODE.getName() + sectionId + index).getValueAsString(),
                    inpsRecord.getInpsCode());

            index++;
        }
    }

    @Test
    public void shouldFillLocalTaxSection() throws ResourceException {
        pdfCreator.setIndex(0);
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();
        List<LocalTaxRecord> localTaxRecordList = localTaxSection.getLocalTaxRecordList();
        String sectionId = "4";

        assertNotNull(localTaxSection);
        assertNotNull(localTaxRecordList);

        localTaxRecordList = helper.paginateList(0, 4, localTaxRecordList);

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
            assertEquals(pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString(),
                    localTaxRecord.getYear());
            assertEquals(pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString(),
                    localTaxRecord.getInstallment());
            assertEquals(pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString(),
                    localTaxRecord.getTaxTypeCode());
            assertEquals(pdfCreator.getField(MUNICIPALITY_CODE.getName() + sectionId + index).getValueAsString(),
                    localTaxRecord.getMunicipalityCode());

            assertEquals(pdfCreator.getField(RECONSIDERATION.getName() + index).getValueAsString(), "X");

            assertEquals(pdfCreator.getField(PROPERTIES_CHANGED.getName() + index).getValueAsString(), "X");

            assertEquals(pdfCreator.getField(ADVANCE_PAYMENT.getName() + index).getValueAsString(), "X");

            assertEquals(pdfCreator.getField(FULL_PAYMENT.getName() + index).getValueAsString(), "X");

            assertEquals(pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + index).getValueAsString(),
                    localTaxRecord.getNumberOfProperties());

            index++;
        }

    }

    @Test
    public void shouldFillTreasurySection() throws ResourceException {
        pdfCreator.setIndex(0);
        TreasurySection treasurySection = this.form.getTreasurySection();
        List<Tax> taxList = treasurySection.getTaxList();
        String sectionId = "1";

        assertNotNull(treasurySection);
        assertNotNull(taxList);

        taxList = helper.paginateList(0, 6, taxList);

        int index = 1;
        for (Tax taxRecord : taxList) {
            pdfCreator.setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
            pdfCreator.setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
            pdfCreator.setField(YEAR.getName() + sectionId + index, taxRecord.getYear());

            index++;
        }

        index = 1;

        for (Tax taxRecord : taxList) {
            assertEquals(pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString(),
                    taxRecord.getTaxTypeCode());
            assertEquals(pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString(),
                    taxRecord.getInstallment());
            assertEquals(pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString(),
                    taxRecord.getYear());

            index++;
        }

        pdfCreator.setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
        pdfCreator.setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

        assertEquals(pdfCreator.getField(OFFICE_CODE.getName()).getValueAsString(), treasurySection.getOfficeCode());
        assertEquals(pdfCreator.getField(DOCUMENT_CODE.getName()).getValueAsString(),
                treasurySection.getDocumentCode());

    }

    @Test
    public void shouldFillSocialSecurity() throws ResourceException {
        pdfCreator.setIndex(0);
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<SocialSecurityRecord> socSecurityList = socSecurity.getSocialSecurityRecordList();
        String sectionId = "6";

        assertNotNull(socSecurity);
        assertNotNull(socSecurityList);

        socSecurityList = helper.paginateList(0, 2, socSecurityList);

        int index = 1;
        for (SocialSecurityRecord socSecRecord : socSecurityList) {
            pdfCreator.setField(INSTITUTION_CODE.getName() + sectionId, socSecRecord.getInstitutionCode());
            pdfCreator.setField(OFFICE_CODE.getName() + sectionId + index, socSecRecord.getOfficeCode());
            pdfCreator.setField(CONTRIBUTION_REASON.getName() + sectionId + index,
                    socSecRecord.getContributionReason());
            pdfCreator.setField(POSITION_CODE.getName() + sectionId + index, socSecRecord.getPositionCode());

            index++;
        }

        index = 1;
        for (SocialSecurityRecord socSecRecord : socSecurityList) {
            assertEquals(pdfCreator.getField(INSTITUTION_CODE.getName() + sectionId).getValueAsString(),
                    socSecRecord.getInstitutionCode());
            assertEquals(pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString(),
                    socSecRecord.getOfficeCode());
            assertEquals(pdfCreator.getField(CONTRIBUTION_REASON.getName() + sectionId + index).getValueAsString(),
                    socSecRecord.getContributionReason());
            assertEquals(pdfCreator.getField(POSITION_CODE.getName() + sectionId + index).getValueAsString(),
                    socSecRecord.getPositionCode());

            index++;
        }
    }

    @Test
    public void shouldFillInail() throws ResourceException {
        pdfCreator.setIndex(0);
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();
        List<InailRecord> inailRecordList = socSecurity.getInailRecords();
        String sectionId = "5";

        assertNotNull(socSecurity);
        assertNotNull(inailRecordList);

        inailRecordList = helper.paginateList(0, 3, inailRecordList);

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
            assertEquals(pdfCreator.getField(OFFICE_CODE.getName() + sectionId + index).getValueAsString(),
                    inailRecord.getOfficeCode());
            assertEquals(pdfCreator.getField(COMPANY_CODE.getName() + sectionId + index).getValueAsString(),
                    inailRecord.getCompanyCode());
            assertEquals(pdfCreator.getField(CONTROL_CODE.getName() + sectionId + index).getValueAsString(),
                    inailRecord.getControlCode());
            assertEquals(pdfCreator.getField(REFERENCE_NUMBER.getName() + sectionId + index).getValueAsString(),
                    inailRecord.getReferenceNumber());
            assertEquals(pdfCreator.getField(REASON.getName() + sectionId + index).getValueAsString(),
                    inailRecord.getReason());

            index++;
        }
    }

    @Test
    public void shouldFillRegionSection() throws ResourceException {
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
            assertEquals(pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString(),
                    regionRecord.getYear());
            assertEquals(pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString(),
                    regionRecord.getInstallment());
            assertEquals(pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString(),
                    regionRecord.getTaxTypeCode());
            assertEquals(pdfCreator.getField(REGION_CODE.getName() + sectionId + index).getValueAsString(),
                    regionRecord.getRegionCode());

            index++;
        }

    }

    @Test
    public void shouldFillSectionTotal() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(helper.getTotalAmount(recordList));

        Integer total = helper.getTotalAmount(recordList);
        String parsedTotal = helper.getMoney(total);
        pdfCreator.setField(TOTAL_AMOUNT.getName() + sectionId, parsedTotal);

        assertEquals(pdfCreator.getField(TOTAL_AMOUNT.getName() + sectionId).getValueAsString(), parsedTotal);
    }

    @Test
    public void shouldFillSectionDebit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(helper.getDebitTotal(recordList));

        Integer debitTotal = helper.getDebitTotal(recordList);
        String parsedTotal = helper.getMoney(debitTotal);
        pdfCreator.setField(TOTAL_DEBIT.getName() + sectionId, parsedTotal);

        assertEquals(pdfCreator.getField(TOTAL_DEBIT.getName() + sectionId).getValueAsString(), parsedTotal);
    }

    @Test
    public void shouldFillSectionCredit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "5";

        assertNotNull(helper.getCreditTotal(recordList));

        Integer creditTotal = helper.getCreditTotal(recordList);
        String parsedTotal = helper.getMoney(creditTotal);
        pdfCreator.setField(TOTAL_CREDIT.getName() + sectionId, parsedTotal);

        assertEquals(pdfCreator.getField(TOTAL_CREDIT.getName() + sectionId).getValueAsString(), parsedTotal);
    }

    @Test
    public void shouldFillRecordCredit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "4";

        recordList = recordList.subList(0, 4);

        int index = 1;
        for (Record record : recordList) {
            String recordCredit = record.getCreditAmount();
            String parsedCredit = helper.getMoney(Integer.parseInt(recordCredit));
            pdfCreator.setField(CREDIT_AMOUNT.getName() + sectionId + index, parsedCredit);

            assertEquals(pdfCreator.getField(CREDIT_AMOUNT.getName() + sectionId + index).getValueAsString(), parsedCredit);

            index++;
        }

    }

    @Test
    public void shouldFillRecordDebit() throws ResourceException {
        pdfCreator.setIndex(0);
        String sectionId = "4";

        recordList = recordList.subList(0, 4);

        int index = 1;
        for (Record record : recordList) {
            String recordCredit = record.getDebitAmount();
            String parsedDebit = helper.getMoney(Integer.parseInt(recordCredit));
            pdfCreator.setField(DEBIT_AMOUNT.getName() + sectionId + index, parsedDebit);

            assertEquals(pdfCreator.getField(DEBIT_AMOUNT.getName() + sectionId + index).getValueAsString(), parsedDebit);

            index++;
        }

    }

    @Test
    public void shouldFillMultiField() throws ResourceException {
        pdfCreator.setIndex(0);

        String[] splittedCreditAmount = helper.splitField(1235.456);
        pdfCreator.setField(DEDUCTION.getName() + "Int", splittedCreditAmount[0]);
        pdfCreator.setField(DEDUCTION.getName() + "Dec", splittedCreditAmount[1]);

        assertEquals(pdfCreator.getField(DEDUCTION.getName() + "Int").getValueAsString(), splittedCreditAmount[0]);
        assertEquals(pdfCreator.getField(DEDUCTION.getName() + "Dec").getValueAsString(), splittedCreditAmount[1]);
    }

    @Test
    public void shouldFillMultidate() throws ResourceException {
        pdfCreator.setIndex(0);
        String date = "202034";

        String monthPart = date.substring(0, 2);
        String yearPart = date.substring(2, date.length() - 1);

        pdfCreator.setField(START_DATE.getName() + "Month" + 6 + 1, monthPart);
        pdfCreator.setField(START_DATE.getName() + "Year" + 6 + 1, yearPart);
    }

    @Test
    public void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }
}
