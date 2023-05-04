package org.f24.dto.component;

import java.util.List;

public class LocalTaxSection extends Section {

    private String operationId;

    private List<LocalTaxRecord> localTaxRecordList;

    private String deduction;

    /**
     * Constructs LocalTaxPayer Section (Old IMU) (IMU e altri tributi locali)
     *
     * @param operationId   operation Id (identificativo operazione)
     * @param imuRecordList list of ImuRecord components
     * @param deduction     deduction (detrazione)
     */
    public LocalTaxSection(String operationId, List<LocalTaxRecord> localTaxRecordList, String deduction) {
        this.operationId = operationId;
        this.localTaxRecordList = localTaxRecordList;
        this.deduction = deduction;
    }

    public LocalTaxSection() {}

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<LocalTaxRecord> getLocalTaxRecordList() {
        return localTaxRecordList;
    }

    public void setLocalTaxRecordList(List<LocalTaxRecord> localTaxRecordList) {
        this.localTaxRecordList = localTaxRecordList;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }
}
