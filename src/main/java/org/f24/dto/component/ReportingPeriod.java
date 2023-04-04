package org.f24.dto.component;

import java.util.Date;

public class ReportingPeriod {

    private Date startDate;
    private Date endDate;

    /**
     * Construct reporting period for INPS, Social Security records components
     *
     * @param startDate start date
     * @param endDate   end date
     */
    public ReportingPeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
