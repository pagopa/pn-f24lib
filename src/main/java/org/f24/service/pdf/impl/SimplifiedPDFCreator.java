package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Simplified;
import org.f24.service.pdf.PDFCreator;
import org.f24.service.pdf.PDFFormManager;

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
        Header header = this.form.getHeader();
        if(header != null) {
            setField("attorney", header.getDelegationTo());
            setField("agency", header.getAgency());
            setField("province", header.getProvince());
        }
    }

    private void setPersonData() throws Exception {
        PersonData personData = this.form.getContributor().getPersonData();
        if(personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField("corporateName", personalData.getSurname());
            setField("name", personalData.getName());
            setField("dateOfBirth", personalData.getDateOfBirth());
            setField("sex", personalData.getSex());
            setField("municipalityOfBirth", personalData.getMunicipalityOfBirth());
            setField("province", personalData.getProvince());
        }
    }

    private void setCompanyData() throws Exception {
        CompanyData companyData = this.form.getContributor().getCompanyData();
        if(companyData != null) {
            setField("corporateName", companyData.getName());
        }
    }

    private void setRegistryData() throws Exception {
        setPersonData();
        setCompanyData();
    }

    private void setContributor() throws Exception {
        Contributor contributor = this.form.getContributor();
        if(contributor != null) {
            setField("taxCode", contributor.getTaxCode());
            setField("officeCode", contributor.getOfficeCode());
            setField("deedCode", contributor.getActCode());
            setField("receiverTaxCode", contributor.getReceiverTaxCode());
            setField("idCode", contributor.getIdCode());
            setRegistryData();
        }
    }

    private String[] splitMoney(double input) {
        input = Math.round(input*100.0)/100.0;
        int integerPart = (int) input;
        double decimalPart = input - integerPart;
        return new String[] { Integer.toString(integerPart), String.format("%.2f", decimalPart).split(",")[1] };
    }

    private void setPaymentMotiveSection(int copyIndex) {
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
                if (record.getActiveRepentance() != null) setField("ravv" + index, "X");
                if (record.getVariedBuildings() != null) setField("building" + index, "X");
                if (record.getAdvancePayment() != null) setField("acc" + index, "X");
                if (record.getBalance() != null) setField("balance" + index, "X");
                if (record.getNumberOfBuildings() != null) setField("numberOfBuildings" + index, record.getNumberOfBuildings());
                setField("month" + index, record.getMonth());
                setField("reportingYear" + index, record.getReportingYear());
                if(record.getDeduction() != null) {
                    String[] splittedDeduction = splitMoney(Double.parseDouble(record.getDeduction()));
                    setField("deductionInt" + index, splittedDeduction[0]);
                    setField("deductionDec" + index, splittedDeduction[1]);
                }
                if(record.getDebitAmount() != null) {
                    String[] splittedDebitAmount = splitMoney(Double.parseDouble(record.getDebitAmount()));
                    setField("debitAmountInt" + index, splittedDebitAmount[0]);
                    setField("debitAmountDec" + index, splittedDebitAmount[1]);
                }
                if(record.getCreditAmount() != null) {
                    String[] splittedCreditAmount = splitMoney(Double.parseDouble(record.getCreditAmount()));
                    setField("creditAmountInt" + index, splittedCreditAmount[0]);
                    setField("creditAmountDec" + index, splittedCreditAmount[1]);
                }
            }
            String[] splittedTotalAmount = splitMoney(Double.parseDouble(this.form.getPaymentMotiveSection().getTotalAmount(paymentMotiveRecordList).toString()));
            setField("totalAmountInt", splittedTotalAmount[0]);
            setField("totalAmountDec", splittedTotalAmount[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPaymentDetails() throws Exception {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if(paymentDetails != null) {
            setField("dateOfPayment", paymentDetails.getDateOfPayment().replaceAll("-", ""));
            setField("company", paymentDetails.getCompany());
            setField("cabCode", paymentDetails.getCabCode());
            setField("checkNumber", paymentDetails.getCheckNumber());
            setField("abiCode", paymentDetails.getAbiCode());
            setField("isBank", paymentDetails.isBank() ? "X" : "");
            setField("isCircular", !paymentDetails.isBank() ? "X" : "");
            setField("ibanCode", paymentDetails.getIbanCode());
        }
    }

    /*private void setSignature() throws Exception {
        PDField field = getField("signature");
        PDAnnotationWidget widget = field.getWidgets().get(0);
        PDImageXObject image = PDImageXObject.createFromFile("", getDoc());
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
    }*/

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

            for(int copyIndex = 0; copyIndex < getCopies().size(); copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setContributor();
                setPaymentDetails();
                //setSignature();
                setPaymentMotiveSection(copyIndex);
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
