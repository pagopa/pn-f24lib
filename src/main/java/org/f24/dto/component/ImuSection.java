package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(description = "IMU Section (Sezione IMU e Altri Tributi Locali) object")
public class ImuSection {

    @Pattern(regexp = "^[A-Z0-9]{18}$", message = "Invalid operation Id code. (identificativo operazione)")
    private String operationId;

    @Valid
    @Size(max = 4, message = "Maximum amount of records in IMU section (Sezione IMU e Altri Tributi Locali) is 4.")
    private List<ImuRecord> imuRecordList;

    @Pattern(regexp = "^(0|[1-9][0-9]{0,14})(\\.\\d{2})$", message = "Invalid deduction. (detrazione abitazione principale)")
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

}
