package org.f24.dto.component;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(description = "Reporting Period (Sezione INPS) object")
public class ReportingPeriod {

    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid start of reporting period. (periodo di riferimento da)")
    private String startDate;

    @Pattern(regexp = "^[0-9]{6}$", message = "Invalid end of reporting period. (periodo di riferimento a)")
    private String endDate;

    /**
     * Construct reporting period for INPS, Social Security records components
     *
     * @param startDate start date
     * @param endDate   end date
     */
    public ReportingPeriod(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
