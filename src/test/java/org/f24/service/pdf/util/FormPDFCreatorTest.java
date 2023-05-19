package org.f24.service.pdf.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.f24.dto.component.Header;
import org.f24.dto.component.PersonData;
import org.f24.dto.component.PersonalData;
import org.f24.dto.component.TaxAddress;
import org.f24.dto.form.F24Form;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.impl.FormPDFCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.f24.service.pdf.util.FieldEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FormPDFCreatorTest {

    private FormPDFCreator pdfCreator;
    private F24Form form;

    @BeforeEach
    void setup() throws IOException {
        String jsonFile = "src/test/resources/input/f24standard.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Standard.class);

        pdfCreator = new FormPDFCreator(form);
        pdfCreator.loadDoc("templates" + "/ModF24IMU2013.pdf");
    }

    @Test
    void givenHeaderData_whenFillHeader_thenFilledHeader() throws ResourceException {
        pdfCreator.setIndex(0);
        Header header = form.getHeader();

        pdfCreator.setField(DELEGATION.getName(), header.getDelegationTo());
        pdfCreator.setField(AGENCY.getName(), header.getAgency());
        pdfCreator.setField(AGENCY_PROVINCE.getName(), header.getProvince());

        assertEquals("delegation", pdfCreator.getField(DELEGATION.getName()).getValueAsString());
        assertEquals("agency", pdfCreator.getField(AGENCY.getName()).getValueAsString());
        assertEquals("MI", pdfCreator.getField(AGENCY_PROVINCE.getName()).getValueAsString());
    }

    @Test
    void givenPersonData_whenFillPersonalData_thenFilledPersonalData() throws ResourceException {
        pdfCreator.setIndex(0);
        PersonData personData = form.getTaxPayer().getPersonData();
        PersonalData personalData = personData.getPersonalData();

        pdfCreator.setField(CORPORATE_NAME.getName(), personalData.getSurname());
        pdfCreator.setField(NAME.getName(), personalData.getName());
        pdfCreator.setField(BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
        pdfCreator.setField(SEX.getName(), personalData.getSex());
        pdfCreator.setField(BIRTH_PLACE.getName(), personalData.getBirthPlace());
        pdfCreator.setField(BIRTH_PROVINCE.getName(), personalData.getBirthProvince());

        assertEquals("ROSSI", pdfCreator.getField(CORPORATE_NAME.getName()).getValueAsString());
        assertEquals("MARIO", pdfCreator.getField(NAME.getName()).getValueAsString());
        assertEquals("11111999", pdfCreator.getField(BIRTH_DATE.getName()).getValueAsString());
        assertEquals("M", pdfCreator.getField(SEX.getName()).getValueAsString());
        assertEquals("MILANO", pdfCreator.getField(BIRTH_PLACE.getName()).getValueAsString());
        assertEquals("MI", pdfCreator.getField(BIRTH_PROVINCE.getName()).getValueAsString());
    }

    @Test
    void givenCompanyData_whenFillCompanyData_thenFilledCompanyData() throws ResourceException {
        pdfCreator.setIndex(0);
        pdfCreator.setField(CORPORATE_NAME.getName(), "name");

        assertEquals("name", pdfCreator.getField(CORPORATE_NAME.getName()).getValueAsString());
    }

    @Test
    void givenTaxResidence_whenFillTaxResidence_thenFilledTaxResidence() throws ResourceException {
        pdfCreator.setIndex(0);
        TaxAddress taxAddress = form.getTaxPayer().getPersonData().getTaxAddress();

        pdfCreator.setField(ADDRESS.getName(), taxAddress.getAddress());
        pdfCreator.setField(MUNICIPALITY.getName(), taxAddress.getMunicipality());
        pdfCreator.setField(TAX_PROVINCE.getName(), taxAddress.getProvince());

        assertEquals("VIA LARGA 21", pdfCreator.getField(ADDRESS.getName()).getValueAsString());
        assertEquals("MILANO", pdfCreator.getField(MUNICIPALITY.getName()).getValueAsString());
        assertEquals("MI", pdfCreator.getField(TAX_PROVINCE.getName()).getValueAsString());
    }

    @Test
    void givenPaymentDetails_whenFillPaymentDetails_thenFilledPaymentDetails() throws ResourceException {
        pdfCreator.setIndex(0);

        pdfCreator.setField(DATE_OF_PAYMENT.getName(), "20042068");
        pdfCreator.setField(COMPANY.getName(), "company");
        pdfCreator.setField(CAB_CODE.getName(), "code");
        pdfCreator.setField(CHECK_NUMBER.getName(), "number");
        pdfCreator.setField(ABI_CODE.getName(), "code");
        pdfCreator.setField(BANK.getName(), "X");
        pdfCreator.setField(CIRCULAR.getName(), "");

        assertEquals("20042068", pdfCreator.getField(DATE_OF_PAYMENT.getName()).getValueAsString());
        assertEquals("company", pdfCreator.getField(COMPANY.getName()).getValueAsString());
        assertEquals("code", pdfCreator.getField(CAB_CODE.getName()).getValueAsString());
        assertEquals("number", pdfCreator.getField(CHECK_NUMBER.getName()).getValueAsString());
        assertEquals("code", pdfCreator.getField(ABI_CODE.getName()).getValueAsString());
        assertEquals("X", pdfCreator.getField(BANK.getName()).getValueAsString());
        assertEquals("", pdfCreator.getField(CIRCULAR.getName()).getValueAsString());
    }

}
