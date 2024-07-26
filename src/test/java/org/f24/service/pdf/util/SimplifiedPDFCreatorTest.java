package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.f24.dto.component.PaymentReasonRecord;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.form.F24Simplified;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.SimplifiedPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.f24.service.pdf.util.FieldEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class SimplifiedPDFCreatorTest {

    private SimplifiedPDFCreator pdfCreator;
    private F24Simplified form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24simplified.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Simplified.class);

        pdfCreator = new SimplifiedPDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24Semplificato.pdf");
    }

    @Test
    void shouldFillTaxPayer() throws ResourceException {
        pdfCreator.setIndex(0);
        TaxPayer taxPayer = form.getTaxPayer();

        assertNotNull(taxPayer);

        if (taxPayer.getTaxCode() == null) {
            taxPayer.setTaxCode("AXDDXA12A23Z234F");
        }
        pdfCreator.setField(TAX_CODE.getName(), taxPayer.getTaxCode());
        assertEquals(taxPayer.getTaxCode(), pdfCreator.getField(TAX_CODE.getName()));

        if (taxPayer.getOfficeCode() == null) {
            taxPayer.setOfficeCode("AZ3");
        }
        pdfCreator.setField(OFFICE_CODE.getName(), taxPayer.getOfficeCode());
        assertEquals(taxPayer.getOfficeCode(), pdfCreator.getField(OFFICE_CODE.getName()));

        if (taxPayer.getDocumentCode() == null) {
            taxPayer.setDocumentCode("stringchars");
        }
        pdfCreator.setField(DOCUMENT_CODE.getName(), taxPayer.getDocumentCode());
        assertEquals(taxPayer.getDocumentCode(), pdfCreator.getField(DOCUMENT_CODE.getName()));

        if (taxPayer.getRelativePersonTaxCode() == null) {
            taxPayer.setRelativePersonTaxCode("AXDDXA12A23Z234F");
        }
        pdfCreator.setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
        assertEquals(taxPayer.getRelativePersonTaxCode(),
                pdfCreator.getField(RELATIVE_PERSON_TAX_CODE.getName()));

        if (taxPayer.getIdCode() == null) {
            taxPayer.setIdCode("AZ9");
        }
        pdfCreator.setField(ID_CODE.getName(), taxPayer.getIdCode());
        assertEquals(taxPayer.getIdCode(), pdfCreator.getField(ID_CODE.getName()));
    }

    @Test
    void shouldFillPaymentReasonSection() throws ResourceException {
        pdfCreator.setIndex(0);

        if(this.form.getPaymentReasonSection() != null) {
            List<PaymentReasonRecord> paymentReasonRecordList = this.form.getPaymentReasonSection().getReasonRecordList();
    
            assertNotNull(paymentReasonRecordList);
    
            if (form.getPaymentReasonSection().getOperationId() == null) {
                form.getPaymentReasonSection().setOperationId("AAAAAAAAAA01234567");
            }
            pdfCreator.setField(OPERATION_ID.getName(), form.getPaymentReasonSection().getOperationId());
            assertEquals(form.getPaymentReasonSection().getOperationId(),
                    pdfCreator.getField(OPERATION_ID.getName()));
    
            pdfCreator.paginateList(0, 10, paymentReasonRecordList);
    
            int index = 1;
            for (PaymentReasonRecord paymentReasonRecord : paymentReasonRecordList) {
                if (paymentReasonRecord.getSection() == null) {
                    paymentReasonRecord.setSection("paymentReason");
                }
                pdfCreator.setField(SECTION.getName() + index, paymentReasonRecord.getSection());
                assertEquals(paymentReasonRecord.getSection(),
                        pdfCreator.getField(SECTION.getName() + index));
    
                if (paymentReasonRecord.getTaxTypeCode() == null) {
                    paymentReasonRecord.setTaxTypeCode("12AZ");
                }
                pdfCreator.setField(TAX_TYPE_CODE.getName() + index, paymentReasonRecord.getTaxTypeCode());
                assertEquals(paymentReasonRecord.getTaxTypeCode(),
                        pdfCreator.getField(TAX_TYPE_CODE.getName() + index));
    
                if (paymentReasonRecord.getMunicipalityCode() == null) {
                    paymentReasonRecord.setMunicipalityCode("1234");
                }
                pdfCreator.setField(MUNICIPALITY_CODE.getName() + index, paymentReasonRecord.getMunicipalityCode());
                assertEquals(paymentReasonRecord.getMunicipalityCode(),
                        pdfCreator.getField(MUNICIPALITY_CODE.getName() + index));
    
                shouldFillPaymentReasonCheckboxes(paymentReasonRecord, index);
    
                index++;
            }
        }
    }

    private void shouldFillPaymentReasonCheckboxes(PaymentReasonRecord paymentReasonRecord, int index)
            throws ResourceException {
        pdfCreator.setField(RECONSIDERATION.getName() + index, "X");
        pdfCreator.setField(PROPERTIES_CHANGED.getName() + index, "X");
        pdfCreator.setField(ADVANCE_PAYMENT.getName() + index, "X");
        pdfCreator.setField(FULL_PAYMENT.getName() + index, "X");
        pdfCreator.setField(NUMBER_OF_PROPERTIES.getName() + index, "12");

        assertEquals("X", pdfCreator.getField(RECONSIDERATION.getName() + index));
        assertEquals("X", pdfCreator.getField(PROPERTIES_CHANGED.getName() + index));
        assertEquals("X", pdfCreator.getField(ADVANCE_PAYMENT.getName() + index));
        assertEquals("X", pdfCreator.getField(FULL_PAYMENT.getName() + index));
        assertEquals("12", pdfCreator.getField(NUMBER_OF_PROPERTIES.getName() + index));
    }

    @Test
    void givenSimplifiedObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }

}
