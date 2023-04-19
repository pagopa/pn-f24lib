package org.f24.dto.component;

import java.util.List;

public class PaymentMotiveSection {

    private String operationId;
    private List<PaymentMotiveRecord> motiveRecordList;

    /**
     * Constructs Motive for Payment Section (Motivo del Pergamento)
     *
     * @param operationId      operation ID (identificativo operazione)
     * @param motiveRecordList list of PaymentMotiveRecord components
     */
    public PaymentMotiveSection(String operationId, List<PaymentMotiveRecord> motiveRecordList) {
        this.operationId = operationId;
        this.motiveRecordList = motiveRecordList;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<PaymentMotiveRecord> getMotiveRecordList() {
        return motiveRecordList;
    }

    public void setMotiveRecordList(List<PaymentMotiveRecord> motiveRecordList) {
        this.motiveRecordList = motiveRecordList;
    }

    public Double getTotalAmount() {
        return getMotiveRecordList()
                .stream()
                .mapToDouble(mr -> Double.parseDouble(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0") - Double.parseDouble(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
    }

}
