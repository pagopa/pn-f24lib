package org.f24.dto.component;

import java.util.List;

public class SocialSecuritySection {

    private List<InailRecord> inailRecords;
    private List<SocialSecurityRecord> socialSecurityRecordList;

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

}
