package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.validator.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class FormValidatorTest {
    private FormValidator validator;
    private F24Form form;

    @BeforeEach
    void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24form.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Form.class);

        validator = (FormValidator) ValidatorFactory.createValidator(form);
    }

    @Test
    void givenInvalidTaxCode_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().setTaxCode("Zsdfg86R78U765I");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenTaxCodeWithoutIdCode_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().setIdCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidName_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setName("MARIO ,èèèè");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setName("marIo èèè");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setName(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidSurname_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setSurname("ROssi-èè");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSurname("ROSSI5èè");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSurname(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidBirthDate_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setBirthDate("19.12-1952");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthDate("19 12-1952");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthDate("19-12-19509");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthDate(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidSex_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setSex("O");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSex("ff");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSex(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidBirthPlace_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setBirthPlace("ROMA 5");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthPlace(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    void givenInvalidProvince_whenValidateForm_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setBirthProvince("MI6");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthProvince("M.");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthProvince(null);
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getHeader().setProvince("mi");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

}
