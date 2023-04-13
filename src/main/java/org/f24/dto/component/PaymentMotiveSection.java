package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "Payment Motive Section (Motivo del Pagamento) object")
public class PaymentMotiveSection {

    @Pattern(regexp = "^[A-Z0-9]{18}$", message = "Invalid operation Id code. (identificativo operazione)")
    private String operationId;

    @Valid
    @Size(max = 10, message = "Maximum amount of records in payment motive section (Motivo del Pagamento) is 10.")
    private List<PaymentMotiveRecord> motiveRecordList;

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

}
