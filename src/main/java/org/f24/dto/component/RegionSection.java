package org.f24.dto.component;

import java.util.List;

public class RegionSection {

    private List<RegionRecord> regionRecordList;

    public RegionSection() {
    }

    /**
     * Constructs Region Section (Sezione Regioni)
     *
     * @param regionRecordList list of RegionRecord components
     */
    public RegionSection(List<RegionRecord> regionRecordList) {
        this.regionRecordList = regionRecordList;
    }

    public List<RegionRecord> getRegionRecordList() {
        return regionRecordList;
    }

    public void setRegionRecordList(List<RegionRecord> regionRecordList) {
        this.regionRecordList = regionRecordList;
    }

}
