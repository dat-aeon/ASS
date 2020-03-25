/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationAccepted;

import java.io.Serializable;

public class ApplicationAcceptedLineBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1893887998128762573L;

    private int no;
    
    private String agencyName;

    private int received;
    
    private int registered;
    
   
    
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
    
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    
}
