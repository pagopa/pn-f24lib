package org.f24.service.validator.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.f24.dto.component.*;
import org.f24.dto.form.F24Excise;
import org.f24.service.validator.Validator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExciseValidator implements Validator {

    private F24Excise form;

    public ExciseValidator(F24Excise form) {
        this.form = form;
    }

    @Override
    public void validate() {
    }

    //ToDo delete
    private void test() throws IOException, ProcessingException {

        //Common
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();
        ObjectMapper objectMapper = new ObjectMapper();

        //Personal Data

        PersonalData personalData = new PersonalData("ROssI", "MARIO","11-11-2000","M","MILANO","MI");
        JsonNode jsonPersonalData = objectMapper.valueToTree(personalData);

        //validateTest(jsonPersonalData,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/personal-data.json");

        //Tax Residence

        TaxResidence taxResidence = new TaxResidence("MILANO","MI6","VIA S. MARCO 23");
        JsonNode jsonTaxResidence = objectMapper.valueToTree(taxResidence);

        //validateTest(jsonTaxResidence,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/tax-residence.json");

        //Contributor
        Contributor contributor = new Contributor("LPANAA00S51F205X",true,personalData,taxResidence,"LPANAA00S51F205X","23");
        JsonNode jsonContributor = objectMapper.valueToTree(contributor);

        validateTest(jsonContributor,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/contributor-excise.json");

        //Tax
        Tax tax1 = new Tax("1234","NAM","2203","12.09","350000000000000.41");
        JsonNode jsonTax1 = objectMapper.valueToTree(tax1);
        validateTest(jsonTax1,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/tax.json");

        Tax tax2 = new Tax("3427","PROV","2002","0.03","89.09");
        JsonNode jsonTax2 = objectMapper.valueToTree(tax2);
        validateTest(jsonTax2,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/tax.json");

        Tax tax3 = new Tax("3426","INST","2003","123.09","0.97");
        JsonNode jsonTax3 = objectMapper.valueToTree(tax3);
        validateTest(jsonTax3,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/tax.json");

        Tax tax4 = new Tax("9400","CITY","2004","121223.98","2346.94");
        JsonNode jsonTax4 = objectMapper.valueToTree(tax4);
        validateTest(jsonTax4,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/tax.json");

        List<Tax> taxes = List.of(tax1,tax2,tax3,tax4);

        //Treasury section
        TreasurySection treasurySection = new TreasurySection(taxes,"T5E","12145678910");
        JsonNode jsonTreasurySection = objectMapper.valueToTree(treasurySection);
        //validateTest(jsonTreasurySection,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/treasury-section.json");

        PaymentDetails paymentDetails = new PaymentDetails("10-04-2023",null,null,null,true,null,null);

        //Inps Record
        ReportingPeriod reportingPeriod = new ReportingPeriod("011968","121996");
        JsonNode jsonReportPeriod = objectMapper.valueToTree(reportingPeriod);
        validateTest(jsonReportPeriod,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/reporting-period.json");

        InpsRecord inpsRecord1 = new InpsRecord("2133","MAS-","sewe",reportingPeriod,"23.91","0.00");
        JsonNode jsonInpsR1 = objectMapper.valueToTree(inpsRecord1);
        //validateTest(jsonInpsR1,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/inps-record.json");

        InpsRecord inpsRecord2 = new InpsRecord("123","A-WP","wI29",reportingPeriod,"2.19","1.21");
        JsonNode jsonInpsR2 = objectMapper.valueToTree(inpsRecord2);
        //validateTest(jsonInpsR2,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/inps-record.json");


        // INPS section (Sezione INPS)
        List<InpsRecord> inpsRecordList = new ArrayList<>();
        inpsRecordList.add(inpsRecord1);
        inpsRecordList.add(inpsRecord2);

        InpsSection inpsSection = new InpsSection(inpsRecordList);
        JsonNode jsonInpsSection = objectMapper.valueToTree(inpsSection);
        validateTest(jsonInpsSection,"/Users/ana._.stasi/Desktop/KeyPartner/F24-PDF/src/main/resources/schemas/component/inps-section.json");

    }

    private void validateTest(JsonNode object,String filename) throws IOException, ProcessingException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder().freeze();

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File(filename);
        JsonNode jsonNode = objectMapper.readTree(file);
        JsonSchema jsonSchema = jsonSchemaFactory.getJsonSchema(jsonNode);

        ProcessingReport processingReport = jsonSchema.validate(object);

        if (!processingReport.isSuccess()) {
            for (ProcessingMessage message : processingReport) {
                System.out.println(message.getMessage());
                
            }
        }
    }

    public static void main(String[] args) throws IOException, ProcessingException {
        ExciseValidator validator = new ExciseValidator(new F24Excise(null,null,null,null,null,null,null,null,null,null));
        validator.test();
    }
}
