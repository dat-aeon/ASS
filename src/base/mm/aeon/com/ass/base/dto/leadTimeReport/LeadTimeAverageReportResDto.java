/**************************************************************************
 * $Date: 2018-08-03$
 * $Author: Shoon Latt Winne $
 * $Rev:  $
 * 2018 AEON Microfinance (Myanmar) Company Limited. All Rights Reserved.
 *************************************************************************/
package mm.aeon.com.ass.base.dto.leadTimeReport;

import mm.com.dat.presto.main.common.dao.bean.IResServiceDto;

public class LeadTimeAverageReportResDto implements IResServiceDto{

    private static final long serialVersionUID = -4014626560373692299L;

    private String type;
    
    private String startToFinished;

    private String finishedToSent;
    
    private String SentToReceived;

    private String ReceivedtoRegistering;
    
    private String RegisteringToRegistered;

    private String ReceivedToRegistered;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartToFinished() {
        return startToFinished;
    }

    public void setStartToFinished(String startToFinished) {
        this.startToFinished = startToFinished;
    }

    public String getFinishedToSent() {
        return finishedToSent;
    }

    public void setFinishedToSent(String finishedToSent) {
        this.finishedToSent = finishedToSent;
    }

    public String getSentToReceived() {
        return SentToReceived;
    }

    public void setSentToReceived(String sentToReceived) {
        SentToReceived = sentToReceived;
    }

    public String getReceivedtoRegistering() {
        return ReceivedtoRegistering;
    }

    public void setReceivedtoRegistering(String receivedtoRegistering) {
        ReceivedtoRegistering = receivedtoRegistering;
    }

    public String getRegisteringToRegistered() {
        return RegisteringToRegistered;
    }

    public void setRegisteringToRegistered(String registeringToRegistered) {
        RegisteringToRegistered = registeringToRegistered;
    }

    public String getReceivedToRegistered() {
        return ReceivedToRegistered;
    }

    public void setReceivedToRegistered(String receivedToRegistered) {
        ReceivedToRegistered = receivedToRegistered;
    }
    
}

