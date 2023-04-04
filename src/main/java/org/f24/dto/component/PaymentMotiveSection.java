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

}
