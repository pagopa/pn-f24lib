package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.f24.dto.form.F24Form;
import org.f24.service.validator.Validator;
import org.f24.exception.ResourceException;

import java.io.IOException;

public class FormValidator implements Validator {

    private String schemaPath;

    private F24Form form;

    public FormValidator(String schemaPath, F24Form form) {
        this.schemaPath = schemaPath;
        this.form = form;
    }

    //ToDo delete try-catch
    @Override
    public void validate() throws ProcessingException, IOException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonForm = objectMapper.valueToTree(this.form);
        JsonNode jsonScemaNode = objectMapper.readTree(getClass().getClassLoader().getResourceAsStream(this.schemaPath));
        JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(jsonScemaNode);

        ProcessingReport processingReport = jsonSchema.validate(jsonForm);
        if (!processingReport.isSuccess()) {
            for (ProcessingMessage message : processingReport) {
                try {
                    manageError(message);
                } catch (ResourceException e) {
                    System.out.println(e.getMessage());
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
        return switch (keyword) {
            case "pattern" -> "Invalid " + field + " : " + errorDetails.findValue("string") + ".";
            case "maxItems" ->
                    "Too much records for " + field + ". Max amount of items: " + errorDetails.findValue("maxItems.");
            case "minItems" -> "Minimum amount of records required: " + errorDetails.findValue("minItems") + ".";
            case "type" -> "Field " + field + " is required.";
            default -> "Error occurred during validation.";
        };
    }

}


