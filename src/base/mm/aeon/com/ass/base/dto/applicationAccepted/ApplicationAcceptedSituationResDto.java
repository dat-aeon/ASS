/**************************************************************************
 * $Date: 2018-08-20$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.applicationAccepted;


import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class ApplicationAcceptedSituationResDto implements IResServiceDto{

    private static final long serialVersionUID = -4014626560373692299L;

    private String agencyName;
    
    private int received;

    private int registered;
    
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

