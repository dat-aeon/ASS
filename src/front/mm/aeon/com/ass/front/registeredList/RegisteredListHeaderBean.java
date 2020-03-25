/**************************************************************************
 * $Date: 2018-08-17$
 * $Author: Thar Pyae $
 * $Rev:  0.1 $
 * Copyright (c) 2018 DIR-ACE Technology Ltd. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.registeredList;

import java.io.Serializable;

public class RegisteredListHeaderBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3293986261651297993L;
         
    private String agencyName;
    
    private String userName;
    
    private String record;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
