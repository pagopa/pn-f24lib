package org.f24.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.f24.dto.form.F24Standard;
import org.f24.service.PDFService;
import org.f24.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/f24/standard")
public class F24StandardController {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ValidatorService validatorService;

    @Operation(description = "Validate Standard DTO.")
    @ApiResponse(responseCode = "200", description = "Standard form fields validation successfully passed.")
    @ApiResponse(responseCode = "400", description = "Form fields validation failed.")
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@Schema(implementation = F24Standard.class) @RequestBody F24Standard f24Standard) {
        validatorService.validatePDF(f24Standard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Get generated PDF for F24 Standard form.")
    @ApiResponse(responseCode = "200", description = "PDF for Standard form successfully generated.")
    @ApiResponse(responseCode = "400", description = "Errors occurred during PDF generation for Standard form.", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePDF(@Schema(implementation = F24Standard.class) @RequestBody F24Standard f24Standard) {
        return new ResponseEntity<>(pdfService.generatePDF(f24Standard), HttpStatus.OK);
    }

}
