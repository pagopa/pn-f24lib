package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.f24.dto.component.PaymentMotiveRecord;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.creator.PDFCreator;

import java.awt.geom.AffineTransform;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SimplifiedPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";

    private F24Simplified form;

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        this.form = form;
    }

    private void setHeader(PDAcroForm form) throws IOException {
        getField("attorney", form).setValue(this.form.getHeader().getAttorneyTo());
        getField("agency", form).setValue(this.form.getHeader().getAgency());
        getField("province", form).setValue(this.form.getHeader().getProvince());
    }

    private void setPersonalData(PDAcroForm form) throws IOException {
        getField("corporateName", form).setValue(this.form.getContributor().getPersonalData().getCorporateName());
        getField("name", form).setValue(this.form.getContributor().getPersonalData().getName());
        getField("dateOfBirth", form).setValue(new SimpleDateFormat("ddMMyyyy").format(this.form.getContributor().getPersonalData().getDateOfBirth()));
        getField("sex", form).setValue(this.form.getContributor().getPersonalData().getSex() == 1 ? "M" : "F");
        getField("municipalityOfBirth", form).setValue(this.form.getContributor().getPersonalData().getMunicipalityOfBirth());
        getField("province", form).setValue(this.form.getContributor().getPersonalData().getProvince());
    }

    private void setContributor(PDAcroForm form) throws IOException {
        getField("taxCode", form).setValue(this.form.getContributor().getTaxCode());
        getField("officeCode", form).setValue(this.form.getContributor().getOfficeCode());
        getField("deedCode", form).setValue(this.form.getContributor().getDeedCode());
        getField("receiverTaxCode", form).setValue(this.form.getContributor().getReceiverTaxCode());
        getField("idCode", form).setValue(this.form.getContributor().getIdCode());
        setPersonalData(form);
    }

    private String[] splitMoney(double input) {
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format("%.2f", decimalPart).split(",")[1] };
    }

    private void setPaymentMotiveSection(PDAcroForm form) throws IOException {
        getField("operationId", form).setValue(this.form.getPaymentMotiveSection().getOperationId());
        List<PaymentMotiveRecord> paymentMotiveRecordList = this.form.getPaymentMotiveSection().getMotiveRecordList();
        for(int index = 1; index <= paymentMotiveRecordList.size(); index++) {
            PaymentMotiveRecord record = paymentMotiveRecordList.get(index-1);
            getField("section"+index, form).setValue(record.getSection());
            getField("tributeCode"+index, form).setValue(record.getTributeCode());
            getField("institutionCode"+index, form).setValue(record.getInstitutionCode());
            if(record.getRavv() != null) getField("ravv"+index, form).setValue("X");
            if(record.getBuilding() != null) getField("building"+index, form).setValue(record.getBuilding());
            if(record.getAcc() != null) getField("acc"+index, form).setValue("X");
            if(record.getBalance() != null) getField("balance"+index, form).setValue("X");
            if(record.getNumberOfBuildings() != null) getField("numberOfBuildings"+index, form).setValue(record.getNumberOfBuildings());
            getField("month"+index, form).setValue(record.getMonth());
            getField("reportingYear"+index, form).setValue(record.getReportingYear());
            String[] splittedDeduction = splitMoney(Double.parseDouble(record.getDeduction()));
            getField("deductionInt"+index, form).setValue(splittedDeduction[0]);
            getField("deductionDec"+index, form).setValue(splittedDeduction[1]);
            String[] splittedDebitAmount = splitMoney(Double.parseDouble(record.getDebitAmount()));
            getField("debitAmountInt"+index, form).setValue(splittedDebitAmount[0]);
            getField("debitAmountDec"+index, form).setValue(splittedDebitAmount[1]);
            String[] splittedCreditAmount = splitMoney(Double.parseDouble(record.getCreditAmount()));
            getField("creditAmountInt"+index, form).setValue(splittedCreditAmount[0]);
            getField("creditAmountDec"+index, form).setValue(splittedCreditAmount[1]);
        }
        // TODO check if rows are more than 10
        // TODO totalAmount calculation
    }

    private void setSignature(PDDocument doc, PDAcroForm form) throws IOException {
        PDField field = getField("signature", form);
        PDAnnotationWidget widget = field.getWidgets().get(0);
        PDImageXObject image = PDImageXObject.createFromFile("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\signature.png", doc);
        // TODO PDImageXObject image = PDImageXObject.createFromByteArray()

        float x = widget.getRectangle().getLowerLeftX();
        float y = widget.getRectangle().getLowerLeftY();
        float width = widget.getRectangle().getWidth();
        float height = widget.getRectangle().getHeight();

        PDAppearanceStream appearanceStream = new PDAppearanceStream(doc);
        appearanceStream.setResources(new PDResources());
        appearanceStream.setBBox(new PDRectangle(width, height));
        appearanceStream.setMatrix(new AffineTransform());

        PDPageContentStream contentStream = new PDPageContentStream(doc, appearanceStream);
        contentStream.drawImage(image, 0, 0, width, height);
        contentStream.close();

        PDAppearanceDictionary appearanceDictionary = new PDAppearanceDictionary();
        appearanceDictionary.setNormalAppearance(appearanceStream);

        widget.setAppearance(appearanceDictionary);
    }

    /**
     * Method which creates PDF Document for F24 Simplified Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            PDDocument doc = PDDocument.load(getClass().getClassLoader().getResourceAsStream(MODEL_NAME));
            PDAcroForm form = getForm(doc);

            setHeader(form);
            setContributor(form);
            setPaymentMotiveSection(form);
            setSignature(doc, form);

            // TODO remove
            doc.save("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            doc.save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
