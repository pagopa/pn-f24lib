package org.f24.dto.component;

import java.util.List;

public class RegionSection {

    private List<RegionRecord> regionRecordList;

    /**
     * Constructs Region Section (Sezione Regioni)
     *
     * @param regionRecordList list of RegionRecord components
     */
    public RegionSection(List<RegionRecord> regionRecordList) {
        this.regionRecordList = regionRecordList;
    }

}
