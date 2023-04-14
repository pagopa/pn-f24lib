package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "INPS Section (Sezione INPS) object")
public class InpsSection {

    @Valid
    @Size(max = 4, message = "Maximum amount of records in INPS section (sezione INPS) is 4.")
    private List<InpsRecord> inpsRecordList;

    public InpsSection() {
    }

    /**
     * Constructs INPS Section (Sezione INPS)
     *
     * @param inpsRecordList list of InpsRecord components
     */
    public InpsSection(List<InpsRecord> inpsRecordList) {
        this.inpsRecordList = inpsRecordList;
    }

    public List<InpsRecord> getInpsRecordList() {
        return inpsRecordList;
    }

    public void setInpsRecordList(List<InpsRecord> inpsRecordList) {
        this.inpsRecordList = inpsRecordList;
    }
}
