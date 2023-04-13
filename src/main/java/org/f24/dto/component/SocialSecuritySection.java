package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Social Security Section (Sezione Altri Enti Precidenziali) object")
public class SocialSecuritySection {

    @Valid
    @Size(max = 3, message = "Maximum amount of INAIL records in Social Security section (Sezione Altri Enti Precidenziali) is 3.")
    private List<InailRecord> inailRecords;

    @Valid
    @Size(max = 2, message = "Maximum amount of Social Security records in Social Security section (Sezione Altri Enti Precidenziali) is 2.")
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
}
