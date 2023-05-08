package org.f24.service.pdf.impl;

import org.f24.dto.component.*;
import org.f24.dto.form.F24Form;
import org.f24.exception.ResourceException;
import org.f24.service.pdf.util.PDFFormManager;

import static org.f24.service.pdf.util.FieldEnum.*;

public class FormPDFCreator extends PDFFormManager {

    private F24Form form;

    public FormPDFCreator(F24Form form) {
        this.form = form;
    }

    protected void setHeader() throws ResourceException {
        Header header = this.form.getHeader();

        if (header != null) {
            setField(DELEGATION.getName(), header.getDelegationTo());
            setField(AGENCY.getName(), header.getAgency());
            setField(AGENCY_PROVINCE.getName(), header.getProvince());
        }
    }

    protected void setRegistryData() throws ResourceException {
        setPersonData();
        setCompanyData();
    }

    private void setPersonData() throws ResourceException {
        PersonData personData = this.form.getTaxPayer().getPersonData();

        if (personData != null && personData.getPersonalData() != null) {
            PersonalData personalData = personData.getPersonalData();
            setField(CORPORATE_NAME.getName(), personalData.getSurname());
            setField(NAME.getName(), personalData.getName());
            setField(BIRTH_DATE.getName(), personalData.getBirthDate().replace("-", ""));
            setField(SEX.getName(), personalData.getSex());
            setField(BIRTH_PLACE.getName(), personalData.getBirthPlace());
            setField(BIRTH_PROVINCE.getName(), personalData.getBirthProvince());
        }
    }

    private void setCompanyData() throws ResourceException {
        CompanyData companyData = this.form.getTaxPayer().getCompanyData();
        if (companyData != null) {
            setField(CORPORATE_NAME.getName(), companyData.getName());
        }
    }

    protected void setTaxResidenceData() throws ResourceException {
        TaxAddress taxResidenceData = this.form.getTaxPayer().getPersonData().getTaxAddress();

        if (taxResidenceData != null) {
            setField(ADDRESS.getName(), taxResidenceData.getAddress());
            setField(MUNICIPALITY.getName(), taxResidenceData.getMunicipality());
            setField(TAX_PROVINCE.getName(), taxResidenceData.getProvince());
        }
    }

    protected void setPaymentDetails() throws ResourceException {
        PaymentDetails paymentDetails = this.form.getPaymentDetails();
        if (paymentDetails != null) {
            setField(DATE_OF_PAYMENT.getName(), paymentDetails.getPaymentDate().replace("-", ""));
            setField(COMPANY.getName(), paymentDetails.getCompany());
            setField(CAB_CODE.getName(), paymentDetails.getCabCode());
            setField(CHECK_NUMBER.getName(), paymentDetails.getCheckNumber());
            setField(ABI_CODE.getName(), paymentDetails.getAbiCode());
            setField(BANK.getName(), paymentDetails.isBank() ? "X" : "");
            setField(CIRCULAR.getName(), !paymentDetails.isBank() ? "X" : "");
        }
    }

}
