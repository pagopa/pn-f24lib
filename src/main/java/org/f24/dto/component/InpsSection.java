package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;

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

    @Override
    public Double getTotalAmount(List<? extends Record> record) throws ResourceException {
        return super.getTotalAmount(record);
    }
}
