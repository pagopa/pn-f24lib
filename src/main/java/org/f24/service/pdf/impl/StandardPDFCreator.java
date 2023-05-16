package org.f24.service.pdf.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Standard;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.PDFCreator;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import static org.f24.service.pdf.util.FieldEnum.*;

public class StandardPDFCreator extends FormPDFCreator implements PDFCreator {

    private static final String MODEL_NAME = MODEL_FOLDER_NAME + "/ModF24IMU2013.pdf";
    private static final int TAX_RECORDS_NUMBER = 6;
    private static final int UNIV_RECORDS_NUMBER = 4;
    private static final int INAIL_RECORDS_NUMBER = 3;
    private static final int SOC_RECORDS_NUMBER = 2;

    private Logger logger = Logger.getLogger(StandardPDFCreator.class.getName());
    private F24Standard form;
    private int totalBalance = 0;

    /**
     * Constructs Standard PDF Creator.
     *
     * @param form F24Standard component (DTO for Standard Form).
     */
    public StandardPDFCreator(F24Standard form) {
        super(form);
        this.form = form;
    }

    private void setTaxPayer() throws ResourceException {
        TaxPayer taxPayer = this.form.getTaxPayer();

        if (taxPayer != null) {
            setField(TAX_CODE.getName(), taxPayer.getTaxCode());
            setField(RELATIVE_PERSON_TAX_CODE.getName(), taxPayer.getRelativePersonTaxCode());
            setField(ID_CODE.getName(), taxPayer.getIdCode());

            if (taxPayer.getIsNotTaxYear())
                setField(IS_NOT_TAX_YEAR.getName(), "X");

            setRegistryData();
            setTaxResidenceData();
        }
    }

    private void setTreasurySection(String sectionId, int copyIndex) throws ResourceException {
        TreasurySection treasurySection = this.form.getTreasurySection();

        if (!treasurySection.getTaxList().isEmpty()) {
            List<Tax> taxList = paginateList(copyIndex, TAX_RECORDS_NUMBER, treasurySection.getTaxList());

            if (taxList.isEmpty()) {
                for (int index = 1; index <= taxList.size(); index++) {
                    Tax taxRecord = taxList.get(index - 1);
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setSectionRecordAmount(sectionId, index, taxRecord);
                }
                setField(OFFICE_CODE.getName(), treasurySection.getOfficeCode());
                setField(DOCUMENT_CODE.getName(), treasurySection.getDocumentCode());

                totalBalance += setSectionTotal(sectionId, taxList, totalBalance);
            }
        }
    }

    private void setInpsSection(String sectionId, int copyIndex) throws ResourceException {
        InpsSection inpsSection = this.form.getInpsSection();

        if (!inpsSection.getInpsRecordList().isEmpty()) {
            List<InpsRecord> inpsRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER,
                    inpsSection.getInpsRecordList());

            if (inpsRecordList.isEmpty()) {
                for (int index = 1; index <= inpsRecordList.size(); index++) {
                    InpsRecord inpsRecord = inpsRecordList.get(index - 1);
                    setField(OFFICE_CODE.getName() + sectionId + index, inpsRecord.getOfficeCode());
                    setField(CONTRIBUTION_REASON.getName() + sectionId + index, inpsRecord.getContributionReason());
                    setField(INPS_CODE.getName() + sectionId + index, inpsRecord.getInpsCode());
                    setMultiDate(START_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getStartDate());
                    setMultiDate(END_DATE.getName(), sectionId, index, inpsRecord.getPeriod().getEndDate());

                    setSectionRecordAmount(sectionId, index, inpsRecord);

                }
                totalBalance += setSectionTotal(sectionId, inpsRecordList, totalBalance);
            }
        }
    }

    private void setRegionSection(String sectionId, int copyIndex) throws ResourceException {
        RegionSection regionSection = this.form.getRegionSection();

        if (!regionSection.getRegionRecordList().isEmpty()) {
            List<RegionRecord> regionRecordsList = paginateList(copyIndex, UNIV_RECORDS_NUMBER,
                    regionSection.getRegionRecordList());

            if (regionRecordsList.isEmpty()) {
                for (int index = 1; index <= regionRecordsList.size(); index++) {
                    RegionRecord regionRecord = regionRecordsList.get(index - 1);
                    setField(YEAR.getName() + sectionId + index, regionRecord.getYear());
                    setField(INSTALLMENT.getName() + sectionId + index, regionRecord.getInstallment());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, regionRecord.getTaxTypeCode());
                    setField(REGION_CODE.getName() + sectionId + index, regionRecord.getRegionCode());

                    setSectionRecordAmount(sectionId, index, regionRecord);
                }
                totalBalance += setSectionTotal(sectionId, regionRecordsList, totalBalance);
            }
        }
    }

    private void setLocalTaxSection(String sectionId, int copyIndex) throws ResourceException {
        LocalTaxSection localTaxSection = this.form.getLocalTaxSection();

        if (!localTaxSection.getLocalTaxRecordList().isEmpty()) {
            List<LocalTaxRecord> localTaxRecordList = paginateList(copyIndex, UNIV_RECORDS_NUMBER,
                    localTaxSection.getLocalTaxRecordList());

            if (!localTaxRecordList.isEmpty()) {
                for (int index = 1; index <= localTaxRecordList.size(); index++) {
                    LocalTaxRecord taxRecord = localTaxRecordList.get(index - 1);
                    setField(YEAR.getName() + sectionId + index, taxRecord.getYear());
                    setField(INSTALLMENT.getName() + sectionId + index, taxRecord.getInstallment());
                    setField(TAX_TYPE_CODE.getName() + sectionId + index, taxRecord.getTaxTypeCode());
                    setField(MUNICIPALITY_CODE.getName() + sectionId + index, taxRecord.getMunicipalityCode());

                    setLocalTaxSectionChecks(taxRecord, index);
                    setSectionRecordAmount(sectionId, index, taxRecord);
                }
                if (!localTaxSection.getOperationId().isEmpty()) {
                    setField(OPERATION_ID.getName(), localTaxSection.getOperationId());
                }
                totalBalance += setSectionTotal(sectionId, localTaxRecordList, totalBalance);

                Double parsedDeduction = Double.parseDouble(localTaxSection.getDeduction());
                setMultiField(DEDUCTION.getName(), parsedDeduction);
            }
        }
    }

    private void setLocalTaxSectionChecks(LocalTaxRecord taxRecord, int index) throws ResourceException {
        if (taxRecord.getReconsideration() != null && taxRecord.getReconsideration()) {
            setField(RECONSIDERATION.getName() + index, "X");
        }
        if (taxRecord.getPropertiesChanges() != null && taxRecord.getPropertiesChanges()) {
            setField(PROPERTIES_CHANGED.getName() + index, "X");
        }
        if (taxRecord.getAdvancePayment() != null && taxRecord.getAdvancePayment()) {
            setField(ADVANCE_PAYMENT.getName() + index, "X");
        }
        if (taxRecord.getFullPayment() != null && taxRecord.getFullPayment()) {
            setField(FULL_PAYMENT.getName() + index, "X");
        }
        if (taxRecord.getNumberOfProperties() != null) {
            setField(NUMBER_OF_PROPERTIES.getName() + index, taxRecord.getNumberOfProperties());
        }
    }

    private void setInail(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();

        if (!socSecurity.getInailRecords().isEmpty()) {
            List<InailRecord> inailRecordList = paginateList(copyIndex, INAIL_RECORDS_NUMBER,
                    socSecurity.getInailRecords());

            if (inailRecordList.isEmpty()) {
                for (int index = 1; index <= inailRecordList.size(); index++) {
                    InailRecord inailRecord = inailRecordList.get(index - 1);
                    setField(OFFICE_CODE.getName() + sectionId + index, inailRecord.getOfficeCode());
                    setField(COMPANY_CODE.getName() + sectionId + index, inailRecord.getCompanyCode());
                    setField(CONTROL_CODE.getName() + sectionId + index, inailRecord.getControlCode());
                    setField(REFERENCE_NUMBER.getName() + sectionId + index, inailRecord.getReferenceNumber());
                    setField(REASON.getName() + sectionId + index, inailRecord.getReason());

                    setSectionRecordAmount(sectionId, index, inailRecord);

                }
                totalBalance += setSectionTotal(sectionId, inailRecordList, totalBalance);
            }
        }
    }

    private void setSocialSecurity(String sectionId, int copyIndex) throws ResourceException {
        SocialSecuritySection socSecurity = this.form.getSocialSecuritySection();

        if (!socSecurity.getSocialSecurityRecordList().isEmpty()) {
            List<SocialSecurityRecord> socSecurityList = paginateList(copyIndex, SOC_RECORDS_NUMBER,
                    socSecurity.getSocialSecurityRecordList());

            if (socSecurityList.isEmpty()) {
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
                totalBalance += setSectionTotal(sectionId, socSecurityList, totalBalance);
            }
        }
    }

    /**
     * Method which creates PDF Document for F24 Standard Form.
     *
     * @return PDFDocument object with filled fields.
     */
    @Override
    public byte[] createPDF() {
        try {
            loadDoc(MODEL_NAME);
            int totalPages = 0;

            int inpsRecordsCount = this.form.getInpsSection().getInpsRecordList().size();
            int localTaxRecordCount = this.form.getLocalTaxSection().getLocalTaxRecordList().size();
            int regionRecordsCount = this.form.getRegionSection().getRegionRecordList().size();
            int treasutyRecordsCount = this.form.getTreasurySection().getTaxList().size();
            int inailRecordsCount = this.form.getSocialSecuritySection().getInailRecords().size();
            int socSecurityRecordsCount = this.form.getSocialSecuritySection().getSocialSecurityRecordList().size();

            totalPages = getTotalPages(treasutyRecordsCount, TAX_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(inpsRecordsCount, UNIV_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(regionRecordsCount, UNIV_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(localTaxRecordCount, UNIV_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(inailRecordsCount, INAIL_RECORDS_NUMBER, totalPages);
            totalPages = getTotalPages(socSecurityRecordsCount, SOC_RECORDS_NUMBER, totalPages);

            copy(totalPages);

            int copiesCount = getCopies().size();

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
            }

            mergeCopies();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getDoc().save(byteArrayOutputStream);
            
            logger.info("standard pdf is created");
            return byteArrayOutputStream.toByteArray();
        } catch (ResourceException | IOException e) {
            logger.info(e.getMessage());
            return ByteArrayBuilder.NO_BYTES;
        }
    }

}
