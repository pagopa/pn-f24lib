package org.f24.service.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.validator.impl.FormValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FormValidatorTest {
    private FormValidator validator;
    private F24Form form;

    @BeforeEach
    public void setup() throws IOException, ResourceException {
        String jsonFile = "src/test/resources/input/f24form.json";
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
        form = new ObjectMapper().readValue(jsonString, F24Form.class);

        validator = (FormValidator) ValidatorFactory.createValidator(form);
    }

    @Test
    public void givenInvalidTaxCode_whenValidatePersonalData_thenThrowException()  {
        form.getTaxPayer().setTaxCode("Zsdfg86R78U765I");
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenTaxCodeWithoutIdCode_whenValidatePersonalData_thenThrowException(){
        form.getTaxPayer().setIdCode(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidName_whenValidatePersonalData_thenThrowException()  {
        form.getTaxPayer().getPersonData().getPersonalData().setName("MARIO ,");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setName("marIo");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setName(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidSurname_whenValidatePersonalData_thenThrowException()  {
        form.getTaxPayer().getPersonData().getPersonalData().setSurname("ROssi-");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSurname("ROSSI5");
        assertThrows(IllegalArgumentException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSurname(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidBirthDate_whenValidatePersonalData_thenThrowException()  {
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
    public void givenInvalidSex_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setSex("O");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSex("ff");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setSex(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidBirthPlace_whenValidatePersonalData_thenThrowException() {
        form.getTaxPayer().getPersonData().getPersonalData().setBirthPlace("ROMA 5");
        assertThrows(ResourceException.class, () -> validator.validate());

        form.getTaxPayer().getPersonData().getPersonalData().setBirthPlace(null);
        assertThrows(ResourceException.class, () -> validator.validate());
    }

    @Test
    public void givenInvalidProvince_whenValidateForm_thenThrowException() {
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
