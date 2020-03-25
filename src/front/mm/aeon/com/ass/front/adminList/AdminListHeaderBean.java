/**************************************************************************
 * $Date: 2018-08-02$
 * $Author: Arjun$
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.adminList;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

public class AdminListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 42293481942974157L;

    private String adminName;

    private String adminLoginId;

    private ArrayList<SelectItem> statusList;

    private String validStatus;

    public ArrayList<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(String adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

}
