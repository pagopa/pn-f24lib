package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.f24.dto.component.TaxPayer;
import org.f24.dto.component.PersonData;
import org.f24.dto.component.PersonalData;
import org.f24.dto.form.F24Form;
import org.f24.exception.ErrorEnum;
import org.f24.service.validator.TaxCodeCalculator;
import org.f24.service.validator.Validator;
import org.f24.exception.ResourceException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class FormValidator implements Validator {

    private String schemaPath;

    private F24Form form;

    public FormValidator(String schemaPath, F24Form form) {
        this.schemaPath = schemaPath;
        this.form = form;
    }

    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonForm = objectMapper.valueToTree(this.form);
        JsonNode jsonScemaNode = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream(this.schemaPath));
        JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(jsonScemaNode);

        ProcessingReport processingReport = jsonSchema.validate(jsonForm);
        if (!processingReport.isSuccess()) {
            for (ProcessingMessage message : processingReport) {
                manageError(message);
            }
        }

        validateTaxCode();
        validateIdCode();
    }

    private void validateTaxCode() throws ResourceException {
        PersonData personData = this.form.getTaxPayer().getPersonData();
        if(personData != null) {
            PersonalData personalData = personData.getPersonalData();
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("dd-MM-yyyy").parse(personalData.getBirthdate());
            } catch (ParseException e) {
                birthdate = new Date();
            }
            String municipality = this.form.getTaxPayer().getTaxCode().substring(11, 15);
            String calculatedTaxCode = TaxCodeCalculator.calculateTaxCode(personalData.getSurname(), personalData.getName(), personalData.getSex(), birthdate, municipality);
            if(!this.form.getTaxPayer().getTaxCode().substring(0, 11).equals(calculatedTaxCode.substring(0, 11))) {
                throw new ResourceException(ErrorEnum.TAX_CODE.getMessage());
            }
        }
    }

    private void validateIdCode() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();

        if (taxPayer != null) {
            String taxCode = taxPayer.getOtherTaxCode();
            String idCode = taxPayer.getIdCode();

            if (taxCode != null && idCode == null)
                throw new ResourceException(ErrorEnum.ID_CODE.getMessage());
        }
    }

    private void manageError(ProcessingMessage message) throws ResourceException {
        JsonNode errorDetails = message.asJson();
        JsonNode instance = errorDetails.findValue("instance");

        if (instance != null) {
            String field = instance.get("pointer").asText().replace("/", ":").substring(1);
            String errorMessage = getErrorMessage(errorDetails, field);
            throw new ResourceException(errorMessage);
        }
    }

    private String getErrorMessage(JsonNode errorDetails, String field) {
        String keyword = errorDetails.findValue("keyword").asText();
        Optional<ErrorEnum> error = Arrays.stream(ErrorEnum.values()).filter(e -> e.getCode().equals(keyword)).findFirst();
        JsonNode details = errorDetails.findValue("message");
        return error.map(e -> e.getMessage(field, details != null ? details.toString() : "")).orElse(ErrorEnum.DEFAULT.getMessage());
    }

}


