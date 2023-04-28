package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;

public class SocialSecuritySection extends Section {

    private List<InailRecord> inailRecords;

    private List<SocialSecurityRecord> socialSecurityRecordList;

    public SocialSecuritySection() {}

    /**
     * Constructs Other social security and insurance institutions section (sezione altri enri previdenziali e assicuretivi)
     *
     * @param inailRecords             list of InailRecord components
     * @param socialSecurityRecordList list of SocialSecurityRecord components
     */
    public SocialSecuritySection(List<InailRecord> inailRecords, List<SocialSecurityRecord> socialSecurityRecordList) {
        this.inailRecords = inailRecords;
        this.socialSecurityRecordList = socialSecurityRecordList;
    }

    public List<InailRecord> getInailRecords() {
        return inailRecords;
    }

    public void setInailRecords(List<InailRecord> inailRecords) {
        this.inailRecords = inailRecords;
    }

    public List<SocialSecurityRecord> getSocialSecurityRecordList() {
        return socialSecurityRecordList;
    }

    public void setSocialSecurityRecordList(List<SocialSecurityRecord> socialSecurityRecordList) {
        this.socialSecurityRecordList = socialSecurityRecordList;
    }

    @Override
    public Double getTotalAmount(List<? extends Record> record) throws ResourceException {
        return super.getTotalAmount(record);
    }
}
