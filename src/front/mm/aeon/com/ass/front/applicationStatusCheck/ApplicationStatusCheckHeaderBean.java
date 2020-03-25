/**************************************************************************
 * $Date: 2018-08-27 $
 * $Author: Thar Pyae $
 * $Rev:  0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationStatusCheck;

import java.io.Serializable;
import java.util.Date;

public class ApplicationStatusCheckHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3293986261651297993L;
     
    private int count;
    
    private int sumCount;
    
    private String agencyName;
    
    private Date fromDate;
    
    private Date toDate;
    
    private String applyRecords;
    
    private String summaryRecords;
    
    private boolean disableFlg;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getApplyRecords() {
        return applyRecords;
    }

    public void setApplyRecords(String applyRecords) {
        this.applyRecords = applyRecords;
    }

    public String getSummaryRecords() {
        return summaryRecords;
    }

    public void setSummaryRecords(String summaryRecords) {
        this.summaryRecords = summaryRecords;
    }

    public boolean isDisableFlg() {
        return disableFlg;
    }

    public void setDisableFlg(boolean disableFlg) {
        this.disableFlg = disableFlg;
    }
}
