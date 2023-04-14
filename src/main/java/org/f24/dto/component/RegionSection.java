package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Region Section (Sezione Regioni) object")
public class RegionSection {

    @Valid
    @Size(max = 4, message = "Maximum amount of records in Region section (sezione Regioni) is 4.")
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
