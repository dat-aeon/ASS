/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.leadTimeReport;

import java.io.Serializable;

public class LeadTimeAverageReportLineBean implements Serializable {

    private static final long serialVersionUID = 1893887998128762573L;

    private String operation;

    private String averageAll;

    private String average1st;

    private String averageFollowUp;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAverageAll() {
        return averageAll;
    }

    public void setAverageAll(String averageAll) {
        this.averageAll = averageAll;
    }

    public String getAverage1st() {
        return average1st;
    }

    public void setAverage1st(String average1st) {
        this.average1st = average1st;
    }

    public String getAverageFollowUp() {
        return averageFollowUp;
    }

    public void setAverageFollowUp(String averageFollowUp) {
        this.averageFollowUp = averageFollowUp;
    }

}
