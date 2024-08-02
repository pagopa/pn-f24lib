package org.f24.service.pdf.impl;

import java.io.IOException;
import java.util.List;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import static org.f24.service.pdf.util.FieldEnum.*;

public class StandardPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24IMU.pdf";

    private final Logger logger = LoggerFactory.getLogger(StandardPDFCreator.class.getName());
    private final F24Standard form;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        super(form);
        this.form = form;
    }

    private void setInail(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();

        if (socSecurity != null && !socSecurity.getInailRecords().isEmpty()) {
            List<InailRecord> inailRecordList = paginateList(copyIndex, INAIL_RECORDS_NUMBER.getRecordsNum(),
                    socSecurity.getInailRecords());

            if (!inailRecordList.isEmpty()) {
                for (int index = 1; index <= inailRecordList.size(); index++) {
                    InailRecord inailRecord = inailRecordList.get(index - 1);
                    setField(OFFICE_CODE.getName() + sectionId + index, inailRecord.getOfficeCode());
                    setField(COMPANY_CODE.getName() + sectionId + index, inailRecord.getCompanyCode());
                    setField(CONTROL_CODE.getName() + sectionId + index, inailRecord.getControlCode());
                    setField(REFERENCE_NUMBER.getName() + sectionId + index, inailRecord.getReferenceNumber());
                    setField(REASON.getName() + sectionId + index, inailRecord.getReason());

                    setSectionRecordAmount(sectionId, index, inailRecord);

                }
                totalBalance += setSectionTotal(sectionId, inailRecordList);
            }
        }
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();

        if (socSecurity != null && !socSecurity.getSocialSecurityRecordList().isEmpty()) {
            List<SocialSecurityRecord> socSecurityList = paginateList(copyIndex, SOC_RECORDS_NUMBER.getRecordsNum(),
                    socSecurity.getSocialSecurityRecordList());

            if (!socSecurityList.isEmpty()) {
                for (int index = 1; index <= socSecurityList.size(); index++) {
                    SocialSecurityRecord socSecRecord = socSecurityList.get(index - 1);
                    setField(MUNICIPALITY_CODE.getName() + sectionId, socSecRecord.getMunicipalityCode());
                    setField(OFFICE_CODE.getName() + sectionId + index, socSecRecord.getOfficeCode());
                    setField(CONTRIBUTION_REASON.getName() + sectionId + index, socSecRecord.getContributionReason());
                    setField(POSITION_CODE.getName() + sectionId + index, socSecRecord.getPositionCode());

                    setMultiDate(START_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getStartDate());
                    setMultiDate(END_DATE.getName(), sectionId, index, socSecRecord.getPeriod().getEndDate());

                    setSectionRecordAmount(sectionId, index, socSecRecord);
                }
                totalBalance += setSectionTotal(sectionId, socSecurityList);
            }
        }
    }

    @Override
    public int getPagesAmount() throws IOException {
        loadDoc(MODEL_NAME);
        int totalPages = 0;

        if (this.form.getInpsSection() != null) {
            int inpsRecordsCount = this.form.getInpsSection().getInpsRecordList().size();
            totalPages = getTotalPages(inpsRecordsCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        if (this.form.getLocalTaxSection() != null) {
            int localTaxRecordCount = this.form.getLocalTaxSection().getLocalTaxRecordList().size();
            totalPages = getTotalPages(localTaxRecordCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        if (this.form.getRegionSection() != null) {
            int regionRecordsCount = this.form.getRegionSection().getRegionRecordList().size();
            totalPages = getTotalPages(regionRecordsCount, UNIV_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        if (this.form.getTreasurySection() != null) {
            int treasutyRecordsCount = this.form.getTreasurySection().getTaxList().size();
            totalPages = getTotalPages(treasutyRecordsCount, TAX_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        if (this.form.getSocialSecuritySection() != null) {
            int inailRecordsCount = this.form.getSocialSecuritySection().getInailRecords().size();
            totalPages = getTotalPages(inailRecordsCount, INAIL_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        if (this.form.getSocialSecuritySection() != null) {
            int socSecurityRecordsCount = this.form.getSocialSecuritySection().getSocialSecurityRecordList().size();
            totalPages = getTotalPages(socSecurityRecordsCount, SOC_RECORDS_NUMBER.getRecordsNum(), totalPages);
        }

        copy(totalPages);
        return getCopies().size();
    }

    /**
     * Method which creates PDF Document for F24 Standard Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            int copiesCount = getPagesAmount();

            for (int copyIndex = 0; copyIndex < copiesCount; copyIndex++) {
                setIndex(copyIndex);
                setHeader();
                setTaxPayer();
                setTreasurySection("1", copyIndex);
                setInpsSection("2", copyIndex);
                setRegionSection("3", copyIndex);
                setLocalTaxSection("4", copyIndex);
                setInail("5", copyIndex);
                setSocialSecurity("6", copyIndex);
                setField(TOTAL_AMOUNT.getName(), getMoney(totalBalance));
                totalBalance = 0;
                flat();
                updateCopy();
            }

            byte[] mergeCopies = mergeCopies();

            finalizeDoc();

            logger.info("Standard PDF is created");

            return mergeCopies;
        } catch (ResourceException | IOException  e) {
            logger.error(e.getMessage(), e);
            return ByteArrayBuilder.NO_BYTES;
        }
    }
}
