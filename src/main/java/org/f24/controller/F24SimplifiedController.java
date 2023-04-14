package org.f24.controller;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.f24.dto.form.F24Simplified;
import org.f24.service.PDFService;
import org.f24.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/f24/simplified")
public class F24SimplifiedController {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ValidatorService validatorService;

    @Operation(description = "Validate Simplified (Semplificato) DTO.")
    @ApiResponse(responseCode = "200", description = "Simplified (Semplificato) form fields validation successfully passed.")
    @ApiResponse(responseCode = "400", description = "Form fields validation failed.")
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@Schema(implementation = F24Simplified.class) @RequestBody F24Simplified f24Simplified) throws IOException, ProcessingException {
        validatorService.validatePDF(f24Simplified);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Get generated PDF for F24 Simplified (Semplificato) form.")
    @ApiResponse(responseCode = "200", description = "PDF for Simplified (Semplificato) form successfully generated.")
    @ApiResponse(responseCode = "400", description = "Errors occurred during PDF generation for Simplified (Semplificato) form.", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePDF(@Schema(implementation = F24Simplified.class) @RequestBody F24Simplified f24Simplified) {
        return new ResponseEntity<>(pdfService.generatePDF(f24Simplified), HttpStatus.OK);
    }

}
