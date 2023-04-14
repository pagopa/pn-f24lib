package org.f24.controller;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.f24.dto.form.F24Elid;
import org.f24.service.PDFService;
import org.f24.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/f24/elid")
public class F24ElidController {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ValidatorService validatorService;

    @Operation(description = "Validate ELID  DTO.")
    @ApiResponse(responseCode = "200", description = "ELID form fields validation successfully passed.")
    @ApiResponse(responseCode = "400", description = "Form fields validation failed.")
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@Schema(implementation = F24Elid.class) @RequestBody F24Elid f24Elid) throws IOException, ProcessingException {
        validatorService.validatePDF(f24Elid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Get generated PDF for F24 ELID form.")
    @ApiResponse(responseCode = "200", description = "PDF for ELID form successfully generated.")
    @ApiResponse(responseCode = "400", description = "Errors occurred during PDF generation  for ELID form.", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePDF(@Schema(implementation = F24Elid.class) @RequestBody F24Elid f24Elid) {
        return new ResponseEntity<>(pdfService.generatePDF(f24Elid), HttpStatus.OK);
    }

}
