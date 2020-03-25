/**************************************************************************
 * $Date: 2018-11-23$
 * $Author: Khin Yadanar Thein $
 * $Rev:   $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.judgementStatusList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class JudgementStatusListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3293986261651297993L;

    private List<SelectItem> statusList = new ArrayList<SelectItem>();

    private int status;

    private String fromDate;

    private String toDate;

    private String record;

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

}
