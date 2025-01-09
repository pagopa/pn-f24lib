package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.f24.dto.component.Record;
import org.f24.dto.component.PersonData;
import org.f24.dto.component.PersonalData;
import org.f24.dto.form.F24Form;
import org.f24.exception.ErrorEnum;
import org.f24.service.validator.util.TaxCodeCalculator;
import org.f24.service.validator.Validator;
import org.f24.exception.ResourceException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FormValidator implements Validator {

    private String schemaPath;

    private F24Form form;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FormValidator(String schemaPath, F24Form form) {
        this.schemaPath = schemaPath;
        this.form = form;
    }

    @Override
    public void validate() throws ProcessingException, IOException, ResourceException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();

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
        validateTaxPayer();
    }

    @Override
    public void validateWithoutTaxCode() throws ProcessingException, IOException, ResourceException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();

        JsonNode jsonForm = objectMapper.valueToTree(this.form);
        JsonNode jsonSchemaNode = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream(this.schemaPath));
        JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(jsonSchemaNode);

        ProcessingReport processingReport = jsonSchema.validate(jsonForm);

        if (!processingReport.isSuccess()) {
            for (ProcessingMessage message : processingReport) {
                manageError(message);
            }
        }

        validateTaxPayer();
    }

    private void validateTaxCode() throws ResourceException {
        PersonData personData = this.form.getTaxPayer().getPersonData();
        if(personData != null) {
            PersonalData personalData = personData.getPersonalData();
            if(personalData != null) {
                Date birthdate = null;
                try {
                    birthdate = new SimpleDateFormat("dd-MM-yyyy").parse(personalData.getBirthDate());
                } catch (ParseException e) {
                    birthdate = new Date();
                }

                String taxCode = this.form.getTaxPayer().getTaxCode();
                if(taxCode != null && taxCode.length() > 11) {
                    String municipality = taxCode.substring(11, 15);
                    String calculatedTaxCode = TaxCodeCalculator.calculateTaxCode(personalData.getSurname(), personalData.getName(), personalData.getSex(), birthdate, municipality);
                    if(!taxCode.substring(0, 11).equals(calculatedTaxCode.substring(0, 11))) {
                        throw new ResourceException(ErrorEnum.TAX_CODE.getMessage());
                    }
                } else {
                    throw new ResourceException(ErrorEnum.TAX_CODE_PF.getMessage());
                }
            }
        }
    }

    private void validateTaxPayer() throws ResourceException {
        if (this.form.getTaxPayer().getPersonData() != null && this.form.getTaxPayer().getCompanyData() != null) {
            throw new ResourceException(ErrorEnum.MULTI_TAX_PAYER.getMessage());
        }
    }

    void validateDebitandCredit(List<? extends Record> targetRecordList) throws ResourceException {
        if (targetRecordList != null) {
            for (Record recordItem : targetRecordList) {
                if ((recordItem.getDebitAmount() != null && recordItem.getCreditAmount() != null)
                        && (!recordItem.getDebitAmount().equals("0") && !recordItem.getCreditAmount().equals("0"))) {
                    throw new ResourceException(ErrorEnum.MOTIVE_RECORD.getMessage());
                }
            }
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


