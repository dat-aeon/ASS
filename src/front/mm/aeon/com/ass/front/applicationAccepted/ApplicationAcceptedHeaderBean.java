/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationAccepted;

import java.io.Serializable;
import java.util.Date;

public class ApplicationAcceptedHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6379828929834955095L;

    private Date fromDate;

    private Date toDate;
    
    private int status;
    
    private boolean isUpdate = false;
    
    public boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
