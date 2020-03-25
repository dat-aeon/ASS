/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.front.applicationAccepted;

import java.io.Serializable;

public class ApplicationAcceptedTotalLineBean implements Serializable {

    
    private static final long serialVersionUID = 1893887998128762573L;

    private String location;

    private int received;
    
    private int registering;
    
    private int registered;
    
    private int notFound;

    private int total;
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getRegistering() {
        return registering;
    }

    public void setRegistering(int registering) {
        this.registering = registering;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public int getNotFound() {
        return notFound;
    }

    public void setNotFound(int notFound) {
        this.notFound = notFound;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
