package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;

public class RegionSection extends Section {

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

    @Override
    public Double getTotalAmount(List<? extends Record> record) throws ResourceException {
        return super.getTotalAmount(record);
    }

}
