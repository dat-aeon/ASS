/**************************************************************************
 * $Date: 2018-08-20$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationAccepted;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ApplicationAcceptedTotalResDto implements IResServiceDto{

    private static final long serialVersionUID = -4014626560373692299L;

    private String location;
    
    private int received;
    
    private int registering;
    
    private int registered;
    
    private int notFound;

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
}

