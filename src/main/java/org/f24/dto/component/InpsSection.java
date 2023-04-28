package org.f24.dto.component;

import java.util.List;

public class InpsSection extends Section {

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
