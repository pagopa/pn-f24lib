package org.f24.pdf.creator.impl;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.f24.dto.component.PaymentMotiveRecord;
import org.f24.dto.form.F24Simplified;
import org.f24.pdf.creator.PDFCreator;
import org.f24.pdf.creator.PDFFormManager;

import java.awt.geom.AffineTransform;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class SimplifiedPDFCreator extends PDFFormManager implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24Semplificato.pdf";
    private static final int MOTIVE_RECORDS_NUMBER = 10;

    private F24Simplified form;

    /**
     * Constructs Simplified PDF Creator.
     *
     * @param form F24Simplifiedcomponent (DTO for Simplified Form).
     */
    public SimplifiedPDFCreator(F24Simplified form) {
        this.form = form;
    }

    private void setHeader() throws Exception {
        setField("attorney", this.form.getHeader().getAttorneyTo());
        setField("agency", this.form.getHeader().getAgency());
        setField("province", this.form.getHeader().getProvince());
    }

    private void setPersonalData() throws Exception {
        setField("corporateName", this.form.getContributor().getPersonalData().getCorporateName());
        setField("name", this.form.getContributor().getPersonalData().getName());
        setField("dateOfBirth", new SimpleDateFormat("ddMMyyyy").format(this.form.getContributor().getPersonalData().getDateOfBirth()));
        setField("sex", this.form.getContributor().getPersonalData().getSex() == 1 ? "M" : "F");
        setField("municipalityOfBirth", this.form.getContributor().getPersonalData().getMunicipalityOfBirth());
        setField("province", this.form.getContributor().getPersonalData().getProvince());
    }

    private void setContributor() throws Exception {
        setField("taxCode", this.form.getContributor().getTaxCode());
        setField("officeCode", this.form.getContributor().getOfficeCode());
        setField("deedCode", this.form.getContributor().getDeedCode());
        setField("receiverTaxCode", this.form.getContributor().getReceiverTaxCode());
        setField("idCode", this.form.getContributor().getIdCode());
        setPersonalData();
    }

    private String[] splitMoney(double input) {
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format("%.2f", decimalPart).split(",")[1] };
    }

    private void setPaymentMotiveSection(int copyIndex) throws Exception {
        try {
            setField("operationId", this.form.getPaymentMotiveSection().getOperationId());
            List<PaymentMotiveRecord> paymentMotiveRecordList = this.form.getPaymentMotiveSection().getMotiveRecordList();
            int limit = copyIndex * MOTIVE_RECORDS_NUMBER + MOTIVE_RECORDS_NUMBER;
            limit = Math.min(limit, paymentMotiveRecordList.size());
            paymentMotiveRecordList = paymentMotiveRecordList.subList(copyIndex * MOTIVE_RECORDS_NUMBER, limit);
            for (int index = 1; index <= paymentMotiveRecordList.size(); index++) {
                PaymentMotiveRecord record = paymentMotiveRecordList.get(index - 1);
                setField("section" + index, record.getSection());
                setField("tributeCode" + index, record.getTributeCode());
                setField("institutionCode" + index, record.getInstitutionCode());
                if (record.getRavv() != null) setField("ravv" + index, "X");
                if (record.getBuilding() != null) setField("building" + index, record.getBuilding());
                if (record.getAcc() != null) setField("acc" + index, "X");
                if (record.getBalance() != null) setField("balance" + index, "X");
                if (record.getNumberOfBuildings() != null) setField("numberOfBuildings" + index, record.getNumberOfBuildings());
                setField("month" + index, record.getMonth());
                setField("reportingYear" + index, record.getReportingYear());
                String[] splittedDeduction = splitMoney(Double.parseDouble(record.getDeduction()));
                setField("deductionInt" + index, splittedDeduction[0]);
                setField("deductionDec" + index, splittedDeduction[1]);
                String[] splittedDebitAmount = splitMoney(Double.parseDouble(record.getDebitAmount()));
                setField("debitAmountInt" + index, splittedDebitAmount[0]);
                setField("debitAmountDec" + index, splittedDebitAmount[1]);
                String[] splittedCreditAmount = splitMoney(Double.parseDouble(record.getCreditAmount()));
                setField("creditAmountInt" + index, splittedCreditAmount[0]);
                setField("creditAmountDec" + index, splittedCreditAmount[1]);
            }
            // TODO totalAmount calculation
        } catch (Exception e) {
            // TODO
        }
    }

    private void setSignature() throws Exception {
        PDField field = getField("signature");
        PDAnnotationWidget widget = field.getWidgets().get(0);
        PDImageXObject image = PDImageXObject.createFromFile("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\signature.png", getDoc());
        // TODO PDImageXObject image = PDImageXObject.createFromByteArray()

        float width = widget.getRectangle().getWidth();
        float height = widget.getRectangle().getHeight();

        PDAppearanceStream appearanceStream = new PDAppearanceStream(getDoc());
        appearanceStream.setResources(new PDResources());
        appearanceStream.setBBox(new PDRectangle(width, height));
        appearanceStream.setMatrix(new AffineTransform());

        PDPageContentStream contentStream = new PDPageContentStream(getDoc(), appearanceStream);
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
            loadDoc(MODEL_NAME);

            int motiveRecordsCount = this.form.getPaymentMotiveSection().getMotiveRecordList().size();
            if(this.form.getPaymentMotiveSection().getMotiveRecordList().size() > MOTIVE_RECORDS_NUMBER) {
                copy(((motiveRecordsCount + MOTIVE_RECORDS_NUMBER - 1) / MOTIVE_RECORDS_NUMBER) - 1);
            }

            for(int copyIndex = 0; copyIndex < this.getCopies().size(); copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setContributor();
                setSignature();
                setPaymentMotiveSection(copyIndex);
            }

            merge();

            // TODO remove
            getDoc().save("C:\\Users\\GianlucaScatena\\Desktop\\PROGETTI\\F24\\output.pdf");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
