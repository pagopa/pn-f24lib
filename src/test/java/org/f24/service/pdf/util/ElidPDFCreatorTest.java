package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Elid;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.ElidPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;
import static org.f24.service.pdf.util.FieldEnum.DOCUMENT_CODE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ElidPDFCreatorTest {

    private ElidPDFCreator pdfCreator;
    private F24Elid form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24elide.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Elid.class);

        pdfCreator = new ElidPDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24ELID.pdf");
        pdfCreator.setIndex(0);
    }


    @Test
    void givenTreasurySectionData_whenTreasurySection_thenFilledTreasurySection() throws ResourceException {
        TreasuryAndOtherSection treasurySection = form.getTreasuryAndOtherSection();

        if(treasurySection != null) {
            List<TreasuryRecord> treasuryTaxList = pdfCreator.paginateList(0, 28, treasurySection.getTreasuryRecords());
    
            pdfCreator.setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
            pdfCreator.setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());
            for (int index = 1; index <= treasuryTaxList.size(); index++) {
                TreasuryRecord treasuryRecord = treasuryTaxList.get(index - 1);
                pdfCreator.setField(TYPE.getName() + index, treasuryRecord.getType());
                pdfCreator.setField(ID_ELEMENT.getName() + index, treasuryRecord.getIdElements());
                pdfCreator.setField(TAX_TYPE_CODE.getName() + index, treasuryRecord.getTaxTypeCode());
                pdfCreator.setField(YEAR.getName() + index, treasuryRecord.getYear());
    
                assertEquals(treasuryRecord.getType(), pdfCreator.getField(TYPE.getName() + index));
                assertEquals(treasuryRecord.getIdElements(), pdfCreator.getField(ID_ELEMENT.getName() + index));
                assertEquals(treasuryRecord.getTaxTypeCode(), pdfCreator.getField(TAX_TYPE_CODE.getName() + index));
                assertEquals(treasuryRecord.getYear(), pdfCreator.getField(YEAR.getName() + index));
            }
            assertEquals(treasurySection.getOfficeCode(), pdfCreator.getField(OFFICE_CODE.getName()));
            assertEquals(treasurySection.getDocumentCode(), pdfCreator.getField(DOCUMENT_CODE.getName()));
        }
    }

    @Test
    void givenElideObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }
}
