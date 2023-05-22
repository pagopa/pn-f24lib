package org.f24.service.pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.component.ExciseSection;
import org.f24.dto.component.ExciseTax;
import org.f24.dto.form.F24Excise;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.ExcisePDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.f24.service.pdf.util.FieldEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class ExcisePDFCreatorTest {

    private ExcisePDFCreator pdfCreator;
    private F24Excise form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24excise.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Excise.class);

        pdfCreator = new ExcisePDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24Accise2013.pdf");
        pdfCreator.setIndex(0);
    }

    @Test
    void givenExciseSectionData_whenFillExciseSection_thenFilledExciseSection() throws ResourceException {
        ExciseSection exciseSection = form.getExciseSection();
        List<ExciseTax> exciseTaxList = pdfCreator.paginateList(0, 7, exciseSection.getExciseTaxList());
        int sectionId = 5;

        pdfCreator.setField(OFFICE_CODE.getName() + sectionId, exciseSection.getOfficeCode());
        pdfCreator.setField(DOCUMENT_CODE.getName() + sectionId, exciseSection.getDocumentCode());
        for (int index = 1; index <= exciseTaxList.size(); index++) {
            ExciseTax exciseTax = exciseTaxList.get(index - 1);
            pdfCreator.setField(MUNICIPALITY.getName() + sectionId + index, exciseTax.getMunicipality());
            pdfCreator.setField(EXCISE_PROVINCE.getName() + sectionId + index, exciseTax.getProvince());
            pdfCreator.setField(TAX_TYPE_CODE.getName() + sectionId + index, exciseTax.getTaxTypeCode());
            pdfCreator.setField(ID_CODE.getName() + sectionId + index, exciseTax.getIdCode());
            pdfCreator.setField(INSTALLMENT.getName() + sectionId + index, exciseTax.getInstallment());
            pdfCreator.setField(MONTH.getName() + sectionId + index, exciseTax.getMonth());
            pdfCreator.setField(YEAR.getName() + sectionId + index, exciseTax.getYear());

            assertEquals(exciseTax.getMunicipality(), pdfCreator.getField(MUNICIPALITY.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getProvince(), pdfCreator.getField(EXCISE_PROVINCE.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getTaxTypeCode(), pdfCreator.getField(TAX_TYPE_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getIdCode(), pdfCreator.getField(ID_CODE.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getInstallment(), pdfCreator.getField(INSTALLMENT.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getMonth(), pdfCreator.getField(MONTH.getName() + sectionId + index).getValueAsString());
            assertEquals(exciseTax.getYear(), pdfCreator.getField(YEAR.getName() + sectionId + index).getValueAsString());
        }
        assertEquals(exciseSection.getOfficeCode(), pdfCreator.getField(OFFICE_CODE.getName() + sectionId).getValueAsString());
        assertEquals(exciseSection.getDocumentCode(), pdfCreator.getField(DOCUMENT_CODE.getName() + sectionId).getValueAsString());
    }

    @Test
    void givenExciseObject_whenGeneratePDF_thenReturnByteArray() {
        byte[] generatedPDF = pdfCreator.createPDF();

        assertNotNull(generatedPDF);
        assertTrue(generatedPDF.length > 0);
    }

}
