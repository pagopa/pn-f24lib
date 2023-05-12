package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.f24.dto.component.PaymentReasonRecord;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.SimplifiedPDFCreator;
import org.junit.Before;
import org.junit.Test;
import static org.f24.service.pdf.util.FieldEnum.*;

import java.io.*;
import java.lang.module.ResolutionException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

public class SimplifiedPDFCreatorTest {

    private SimplifiedPDFCreator pdfCreator;
    private F24Simplified form;
    private CreatorHelper helper;

    @Before
    public void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24simplified.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Simplified.class);
        helper = new CreatorHelper();

        pdfCreator = new SimplifiedPDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24Semplificato.pdf");
    }

    @Test
    public void shouldFillTaxPayer() throws ResourceException {
        pdfCreator.setIndex(0);
        TaxPayer taxPayer = form.getTaxPayer();

        assertNotNull(taxPayer);

        if (taxPayer.getTaxCode() == null) {
            taxPayer.setTaxCode("AXDDXA12A23Z234F");
        }
        pdfCreator.setField(TAX_CODE.getName(), taxPayer.getTaxCode());
        assertEquals(pdfCreator.getField(TAX_CODE.getName()).getValueAsString(), taxPayer.getTaxCode());

        if (taxPayer.getOfficeCode() == null) {
            taxPayer.setOfficeCode("AZ3");
        }
        pdfCreator.setField(OFFICE_CODE.getName(), taxPayer.getOfficeCode());
        assertEquals(pdfCreator.getField(OFFICE_CODE.getName()).getValueAsString(), taxPayer.getOfficeCode());

        if (taxPayer.getDocumentCode() == null) {
            taxPayer.setDocumentCode("stringchars");
        }
        pdfCreator.setField(DOCUMENT_CODE.getName(), taxPayer.getDocumentCode());
        assertEquals(pdfCreator.getField(DOCUMENT_CODE.getName()).getValueAsString(), taxPayer.getDocumentCode());

        if (taxPayer.getRelativePersonTaxCode() == null) {
            taxPayer.setRelativePersonTaxCode("AXDDXA12A23Z234F");
        }
        pdfCreator.setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
        assertEquals(pdfCreator.getField(RELATIVE_PERSON_TAX_CODE.getName()).getValueAsString(),
                taxPayer.getRelativePersonTaxCode());

        if (taxPayer.getIdCode() == null) {
            taxPayer.setIdCode("AZ9");
        }
        pdfCreator.setField(ID_CODE.getName(), taxPayer.getIdCode());
        assertEquals(pdfCreator.getField(ID_CODE.getName()).getValueAsString(), taxPayer.getIdCode());
    }

    @Test
    public void shouldFillPaymentReasonSection() throws ResourceException {
        pdfCreator.setIndex(0);
        List<PaymentReasonRecord> paymentReasonRecordList = this.form.getPaymentReasonSection().getReasonRecordList();

        assertNotNull(paymentReasonRecordList);

        if (form.getPaymentReasonSection().getOperationId() == null) {
            form.getPaymentReasonSection().setOperationId("AAAAAAAAAA01234567");
        }
        pdfCreator.setField(OPERATION_ID.getName(), form.getPaymentReasonSection().getOperationId());
        assertEquals(pdfCreator.getField(OPERATION_ID.getName()).getValueAsString(),
                form.getPaymentReasonSection().getOperationId());

        helper.paginateList(0, 10, paymentReasonRecordList);

        int index = 1;
        for (PaymentReasonRecord paymentReasonRecord : paymentReasonRecordList) {
            if (paymentReasonRecord.getSection() == null) {
                paymentReasonRecord.setSection("paymentReason");
            }
            pdfCreator.setField(SECTION.getName() + index, paymentReasonRecord.getSection());
            assertEquals(pdfCreator.getField(SECTION.getName() + index).getValueAsString(),
                    paymentReasonRecord.getSection());

            if (paymentReasonRecord.getTaxTypeCode() == null) {
                paymentReasonRecord.setTaxTypeCode("12AZ");
            }
            pdfCreator.setField(TAX_TYPE_CODE.getName() + index, paymentReasonRecord.getTaxTypeCode());
            assertEquals(pdfCreator.getField(TAX_TYPE_CODE.getName() + index).getValueAsString(),
                    paymentReasonRecord.getTaxTypeCode());

            if (paymentReasonRecord.getInstitutionCode() == null) {
                paymentReasonRecord.setInstitutionCode("1234");
            }
            pdfCreator.setField(INSTITUTION_CODE.getName() + index, paymentReasonRecord.getInstitutionCode());
            assertEquals(pdfCreator.getField(INSTITUTION_CODE.getName() + index).getValueAsString(),
                    paymentReasonRecord.getInstitutionCode());

            shouldFillPaymentReasonCheckboxes(paymentReasonRecord, index);

            index++;
        }
    }

    private void shouldFillPaymentReasonCheckboxes(PaymentReasonRecord paymentReasonRecord, int index)
            throws ResourceException {
        pdfCreator.setField(RECONSIDERATION.getName() + index, "X");
        pdfCreator.setField(PROPERTIES_CHANGED.getName() + index, "X");
        pdfCreator.setField(ADVANCE_PAYMENT.getName() + index, "X");
        pdfCreator.setField(FULL_PAYMENT.getName() + index, "X");
        pdfCreator.setField(NUMBER_OF_PROPERTIES.getName() + index, "12");

        assertEquals(pdfCreator.getField(RECONSIDERATION.getName() + index).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(PROPERTIES_CHANGED.getName() + index).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(ADVANCE_PAYMENT.getName() + index).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(FULL_PAYMENT.getName() + index).getValueAsString(), "X");
        assertEquals(pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + index).getValueAsString(), "12");
    }

    @Test
    public void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }

}
