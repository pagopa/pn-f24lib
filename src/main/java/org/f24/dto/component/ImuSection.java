package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ResourceException;

public class ImuSection extends Section {

    private String operationId;

    private List<ImuRecord> imuRecordList;

    private String deduction;

    /**
     * Constructs IMU Section (IMU e altri tributi locali)
     *
     * @param operationId   operation Id (identificativo operazione)
     * @param imuRecordList list of ImuRecord components
     * @param deduction     deduction (detrazione)
     */
    public ImuSection(String operationId, List<ImuRecord> imuRecordList, String deduction) {
        this.operationId = operationId;
        this.imuRecordList = imuRecordList;
        this.deduction = deduction;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<ImuRecord> getImuRecordList() {
        return imuRecordList;
    }

    public void setImuRecordList(List<ImuRecord> imuRecordList) {
        this.imuRecordList = imuRecordList;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    @Override
    public Double getTotalAmount(List<? extends Record> record) throws ResourceException {
        return super.getTotalAmount(record);
    }
}
