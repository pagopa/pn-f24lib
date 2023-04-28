package org.f24.dto.component;

public class ReportingPeriod {

    private String startDate;

    private String endDate;

    public ReportingPeriod() {}

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
