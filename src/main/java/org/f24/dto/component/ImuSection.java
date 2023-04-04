package org.f24.dto.component;

import java.util.List;

public class ImuSection {

    private List<ImuRecord> imuRecordList;
    private String deduction;

    /**
     * Constructs IMU Section (IMU e altri tributi locali)
     *
     * @param imuRecordList list of ImuRecord components
     * @param deduction     deduction (detrazione)
     */
    public ImuSection(List<ImuRecord> imuRecordList, String deduction) {
        this.imuRecordList = imuRecordList;
        this.deduction = deduction;
    }

}
