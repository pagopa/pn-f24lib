package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

public class PaymentMotiveSection {

    private String operationId;

    private List<PaymentMotiveRecord> motiveRecordList;

    public PaymentMotiveSection() {}

    /**
     * Constructs Motive for Payment Section (Motivo del Pagamento)
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

    public Integer getTotalAmount() throws ResourceException {
        Integer totalAmount =  getMotiveRecordList()
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0") - Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if(totalAmount < 0) {
            throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

}
