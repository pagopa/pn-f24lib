package org.f24.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.f24.dto.form.F24Excise;
import org.f24.service.PDFService;
import org.f24.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/f24/excise")
public class F24ExciseController {

    @Autowired
    private PDFService pdfService;

    @Autowired
    private ValidatorService validatorService;

    @Operation(description = "Validate Excise (Accise) DTO.")
    @ApiResponse(responseCode = "200", description = "Excise (Accise) form fields validation successfully passed.")
    @ApiResponse(responseCode = "400", description = "Form fields validation failed.")
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@Schema(implementation = F24Excise.class) @RequestBody F24Excise f24Excise) {
        validatorService.validatePDF(f24Excise);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Get generated PDF for F24 Excise (Accise) form.")
    @ApiResponse(responseCode = "200", description = "PDF for Excise (Accise) form successfully generated.")
    @ApiResponse(responseCode = "400", description = "Errors occurred during PDF generation  for Excise (Accise) form.", content = @Content(schema = @Schema(hidden = true)))
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePDF(@Schema(implementation = F24Excise.class) @RequestBody() F24Excise f24Excise) {
        byte[] fileContent = pdfService.generatePDF(f24Excise);

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Assice.pdf");

        return ResponseEntity.ok()
                .headers(header)
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(fileContent.length)
                .body(fileContent);
    }

}
