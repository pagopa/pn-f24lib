package org.f24.dto.component;

import java.util.List;

import org.f24.exception.ErrorEnum;
import org.f24.exception.ResourceException;

public class PaymentReasonSection extends Section {

    private String operationId;

    private List<PaymentReasonRecord> reasonRecordList;

    public PaymentReasonSection() {
    }

    /**
     * Constructs Motive for Payment Section (Motivo del Pagamento)
     *
     * @param operationId      operation ID (identificativo operazione)
     * @param reasonRecordList list of PaymentMotiveRecord components
     */
    public PaymentReasonSection(String operationId, List<PaymentReasonRecord> reasonRecordList) {
        this.operationId = operationId;
        this.reasonRecordList = reasonRecordList;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<PaymentReasonRecord> getReasonRecordList() {
        return reasonRecordList;
    }

    public void setReasonRecordList(List<PaymentReasonRecord> reasonRecordList) {
        this.reasonRecordList = reasonRecordList;
    }

    public Integer getTotalAmount() throws ResourceException {
        Integer totalAmount = getReasonRecordList()
                .stream()
                .mapToInt(mr -> Integer.parseInt(mr.getDebitAmount() != null ? mr.getDebitAmount() : "0") - Integer.parseInt(mr.getCreditAmount() != null ? mr.getCreditAmount() : "0"))
                .sum();
        if (totalAmount < 0) {
            throw new ResourceException("TotalAmount: " + ErrorEnum.NEGATIVE_NUM.getMessage());
        }
        return totalAmount;
    }

}
