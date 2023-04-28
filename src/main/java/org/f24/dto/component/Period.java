package org.f24.dto.component;

public class Period {

    private String startDate;

    private String endDate;

    public Period() {}

    /**
     * Construct reporting period for INPS, Social Security records components
     *
     * @param startDate start date
     * @param endDate   end date
     */
    public Period(String startDate, String endDate) {
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
