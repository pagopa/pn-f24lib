package org.f24.dto.component;

import java.util.List;

public class InpsSection {

    private List<InpsRecord> inpsRecordList;

    /**
     * Constructs INPS Section (Sezione INPS)
     *
     * @param inpsRecordList list of InpsRecord components
     */
    public InpsSection(List<InpsRecord> inpsRecordList) {
        this.inpsRecordList = inpsRecordList;
    }

}
